package de.sbg.unity.iconomy.Database;

import de.sbg.unity.iconomy.Banksystem.BankMember;
import de.sbg.unity.iconomy.Banksystem.PlayerAccount;
import de.sbg.unity.iconomy.Utils.DatabaseFormat;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.io.IOException;
import java.util.HashMap;
import net.risingworld.api.database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The database of the money
 *
 * @author pbronke
 */
public class MoneyDatabase {

    private final Database Database;
    private final icConsole Console;
    private final iConomy plugin;
    private final DatabaseFormat format;

    /**
     * Access to the 'Bank' table
     */
    public TableBank Bank;

    /**
     * Access to the 'Cash' table
     */
    public TableCash Cash;

    public MoneyDatabase(iConomy plugin, icConsole Console) {
        this.Console = Console;
        this.plugin = plugin;
        this.Database = plugin.getSQLiteConnection(plugin.getPath() + "/Databases/MoneyDatabase.db");
        this.Cash = new TableCash(plugin, Console, Database);
        this.Bank = new TableBank(plugin, Console, Database);
        this.format = new DatabaseFormat();

    }

    public Database getDatabase() {
        return Database;
    }

    /**
     * Close the database connection!
     *
     * @hidden
     * @throws SQLException
     */
    public void close() throws SQLException {
        Bank.conn.close();
        Cash.conn.close();
        Database.close();
    }

    /**
     * @hidden
     */
    public void createDatabse() {
        Database.execute("CREATE TABLE IF NOT EXISTS Cash ("
                + "ID INTEGER PRIMARY KEY NOT NULL, " //AUTOINCREMENT
                + "UID TXT, "
                + "Money BIGINT, "
                + "More TXT "
                + "); ");
        Database.execute("CREATE TABLE IF NOT EXISTS Bank ("
                + "ID INTEGER PRIMARY KEY NOT NULL, " //AUTOINCREMENT
                + "UID TXT,"
                + "Money BIGINT, "
                + "Members BLOB, "
                + "Statements BLOB, "
                + "Min BIGINT, "
                + "More TXT "
                + "); ");
        
    }

    public class TableCash {

        private final Database Database;
        private final Connection conn;
        private PreparedStatement pstmt;

        public TableCash(iConomy plugin, icConsole Console, Database db) {
            this.Database = db;
            conn = db.getConnection();
        }

        public void add(String uid, long amounth) throws SQLException {
            pstmt = conn.prepareStatement("INSERT INTO Cash (UID, Money) VALUES (?, ?)");
            pstmt.setString(1, uid);
            pstmt.setLong(2, amounth);
            pstmt.executeUpdate();
            pstmt.close();
            Console.sendInfo("DB-Cash", "Add player '" + uid + "' to database!");
        }

        public void loadAllFromDatabase(HashMap<String, Long> CashList) throws SQLException {
            int zähler = 0;
            try (ResultSet result = Database.executeQuery("SELECT * FROM 'Cash'")) {
                while (result.next()) {
                    zähler += 1;
                    String uid = result.getString("UID");
                    long money = result.getLong("Money");
                    if (plugin.Config.Debug > 0) {
                        Console.sendDebug("DB", "Load: " + uid + " - " + money);
                    }
                    CashList.put(uid, money);
                }
            }
            Console.sendInfo("DB-Cash", "Load " + zähler + " players from database!");
        }

        public void saveAllToDatabase(HashMap<String, Long> CashList) throws SQLException {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("DB-saveAllToDatabase");
            }

            int zähler = 0;
            for (String s : CashList.keySet()) {
                zähler += 1;
                long money = CashList.get(s);
                if (plugin.Config.Debug > 0) {
                    Console.sendDebug("DB-saveAllToDatabase","money = " + s);
                }
                pstmt = conn.prepareStatement("UPDATE Cash SET Money=? WHERE UID='" + s + "'");
                pstmt.setLong(1, money);
                pstmt.executeUpdate();
                pstmt.close();
            }
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("DB-Cash", "Save " + zähler + " players to the database");
            }
        }

    }

    public class TableBank {

        private final Database Database;
        private final Connection conn;
        private PreparedStatement pstmt;

        public TableBank(iConomy plugin, icConsole Console, Database db) {
            this.Database = db;
            conn = db.getConnection();
        }

        public void add(PlayerAccount pa) throws SQLException {
            pstmt = conn.prepareStatement("INSERT INTO Bank (UID, Money, Min) VALUES (?, ?, ?)");
            pstmt.setString(1, pa.getOwnerUID());
            pstmt.setLong(2, pa.getMoney());
            pstmt.setLong(3, pa.getMin());
            pstmt.executeUpdate();
            pstmt.close();
        }

        public void loadAllFromDatabase(HashMap<String, PlayerAccount> PlayerAccounts) throws SQLException, IOException, ClassNotFoundException {
            String uid;
            long money, min;
            List<String> Statements;
            List<BankMember> Members;

            int zähler = 0;
            try (ResultSet result = Database.executeQuery("SELECT * FROM 'Bank'")) {
                while (result.next()) {
                    zähler = +1;
                    uid = result.getString("UID");
                    min = result.getLong("Min");
                    money = result.getLong("Money");
                    Members = (List<BankMember>)format.toObject(result.getBytes("Members"));
                    Statements = (List<String>)format.toObject(result.getBytes("Statements"));
                    PlayerAccount pa = new PlayerAccount(plugin, uid);
                    pa.setMoney(money);
                    pa.setMin(min);
                    pa.addAllStatements(Statements);
                    pa.addAllMembers(Members);
                    PlayerAccounts.put(uid, pa);
                }
            }
            Console.sendInfo("DB-Bank", "Load " + zähler + " accounts from database!");

        }
        
        public void saveAllToDatabase(HashMap<String, PlayerAccount> PlayerAccounts) throws SQLException, IOException {
            PlayerAccount pa;
            int zähler = 0;
            for (String uid : PlayerAccounts.keySet()) {
                zähler += 1;
                pa = plugin.Bankystem.PlayerSystem.getPlayerAccount(uid);
                pstmt = conn.prepareStatement("UPDATE Bank SET Money=?, Members=?, Statements=?, Min=? WHERE UID='" + uid + "'");
                pstmt.setLong(1, pa.getMoney());
                pstmt.setBytes(2, format.toBlob(pa.getMembers()));
                pstmt.setBytes(3, format.toBlob(pa.getStatements()));
                pstmt.setLong(4, pa.getMin());
                pstmt.executeUpdate();
                pstmt.close();
            }
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("DB-Bank", "Save " + zähler + " account(s) to the database");
            }
        }

    }

}
