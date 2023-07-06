package de.sbg.unity.iconomy.Database;

import de.sbg.unity.iconomy.Banksystem.BankMember;
import de.sbg.unity.iconomy.Banksystem.PlayerAccount;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
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
                + "MembersPerm BLOB, "
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
                    zähler = +1;
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
                zähler = +1;
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

        public int add(PlayerAccount pa) {
            //TODO add
            return -1;
        }

        public void loadAllFromDatabase(HashMap<String, PlayerAccount> PlayerAccounts) throws SQLException {
            String uid;
            long money;
            PlayerAccount pa;
            List<BankMember> Members;

            int zähler = 0;
            try (ResultSet result = Database.executeQuery("SELECT * FROM 'Bank'")) {
                while (result.next()) {
                    zähler = +1;
                    uid = result.getString("UID");
                    money = result.getLong("Money");

                    PlayerAccounts.put(uid, null);
                }
            }
            Console.sendInfo("DB-Bank", "Load " + zähler + " accounts from database!");

        }

//        Database.execute("CREATE TABLE IF NOT EXISTS Bank ("
//                + "ID INTEGER PRIMARY KEY NOT NULL, " //AUTOINCREMENT
//                + "UID TXT,"
//                + "Money BIGINT, "
//                + "Members BLOB, "
//                + "MembersPerm BLOB, "
//                + "Statements BLOB, "
//                + "Min BIGINT, "
//                + "More TXT "
//                + "); ");
        public void saveAllToDatabase(HashMap<String, PlayerAccount> PlayerAccounts) {
            //TODO saveAllToDatabase
        }

    }

}
