package de.sbg.unity.iconomy.Database;

import de.sbg.unity.iconomy.Banksystem.BankMember;
import de.sbg.unity.iconomy.Banksystem.PlayerAccount;
import de.sbg.unity.iconomy.Objects.AtmObject;
import de.sbg.unity.iconomy.Utils.AtmUtils;
import de.sbg.unity.iconomy.Utils.AtmUtils.AtmType;
import de.sbg.unity.iconomy.Utils.BankStatement;
import de.sbg.unity.iconomy.Utils.DatabaseFormat;
import de.sbg.unity.iconomy.Utils.PrefabVorlage;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.io.IOException;
import java.util.HashMap;
import net.risingworld.api.database.Database;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.World;
import net.risingworld.api.objects.Npc;
import net.risingworld.api.utils.Quaternion;
import net.risingworld.api.utils.Vector3f;

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
    private int savedAtm;
    private int savedPlayers;
    private int savedAccounts;

    /**
     * Access to the 'Bank' table
     */
    public TableBank Bank;

    /**
     * Access to the 'Cash' table
     */
    public TableCash Cash;

    /**
     * Access to the 'ATM' table
     */
    public TableAtm ATM;
    
    public TableNpc NPC;
    

    public MoneyDatabase(iConomy plugin, icConsole Console) {
        this.Console = Console;
        this.plugin = plugin;
        this.Database = plugin.getSQLiteConnection(plugin.getPath() + "/Databases/MoneyDatabase.db");
        this.Cash = new TableCash(plugin, Console, Database);
        this.Bank = new TableBank(plugin, Console, Database);
        this.format = new DatabaseFormat();
        this.ATM = new TableAtm(plugin, Console, Database);
        this.NPC = new TableNpc(Database, Console);
    }

    public int getSavedAccounts() {
        return savedAccounts;
    }

    public int getSavedAtm() {
        return savedAtm;
    }

    public int getSavedPlayers() {
        return savedPlayers;
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
        Database.execute("CREATE TABLE IF NOT EXISTS Atm ("
                + "ID INTEGER PRIMARY KEY NOT NULL, " //AUTOINCREMENT
                + "Type INTEGER, "
                + "PosX FLOAT, "
                + "PosY FLOAT, "
                + "PosZ FLOAT, "
                + "RotW FLOAT, "
                + "RotX FLOAT, "
                + "RotY FLOAT, "
                + "RotZ FLOAT, "
                + "LivePoints INTEGER, "
                + "More TXT "
                + "); ");
        Database.execute("CREATE TABLE IF NOT EXISTS NPC ("
                + "ID INTEGER PRIMARY KEY NOT NULL, " //AUTOINCREMENT
                + "Global BIGINT, "
                + "Mode INTEGER, "
                + "More BLOB "
                + "); ");

    }

    public class TableNpc {

        private final Database database;
        private final Connection conn;
        private PreparedStatement pstmt;

        public TableNpc(Database db, icConsole Console) {
            this.database = db;
            this.conn = db.getConnection();
        }

        public int add(Npc npc, int mode) throws SQLException{
            int id = -1;
            pstmt = conn.prepareStatement("INSERT INTO Npc (Global, Mode) VALUES (?, ?)");
            pstmt.setLong(1, npc.getGlobalID());
            pstmt.setInt(2, mode);
            pstmt.executeUpdate();
            
            
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT last_insert_rowid()");
            
            if (rs.next()) {
                id = rs.getInt(1);
            }
            
            pstmt.close();
            return id;
        }
        
        public void remove(Npc npc) throws SQLException {
            pstmt = conn.prepareStatement("DELETE FROM Npc WHERE Global=" + npc.getGlobalID());
            pstmt.executeUpdate();
            pstmt.close();
        }
        
        public void saveAll(List<Npc> list) throws SQLException{
            int zähler = 0;
            for (Npc npc : list) {
                pstmt = conn.prepareStatement("UPDATE Npc SET Mode=? WHERE Global=" + npc.getGlobalID());
                pstmt.setInt(1, plugin.Attribute.npc.getNpcMode(npc));
                pstmt.executeUpdate();
                pstmt.close();
            }
            
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("DB-Npc", "Saved " + zähler + " Npcs to the database");
            }
        }
        
        public void loadAll(List<Npc> list) throws SQLException{
            int zähler = 0;
            try (ResultSet result = Database.executeQuery("SELECT * FROM 'Npc'")) {
                while (result.next()) {
                    zähler += 1;
                    long gID = result.getLong("Global");
                    int mode = result.getInt("Mode");
                    
                    Npc npc = World.getNpc(gID);
                    if (npc != null) {
                        plugin.Attribute.npc.setNpcMode(npc, mode);
                        list.add(npc);
                    }
                }
            }
            Console.sendInfo("DB-Npc", "Load " + zähler + " npcs from database!");
        }
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
            savedPlayers = zähler;
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
                    Console.sendDebug("DB-saveAllToDatabase", "money = " + s);
                }
                pstmt = conn.prepareStatement("UPDATE Cash SET Money=? WHERE UID='" + s + "'");
                pstmt.setLong(1, money);
                pstmt.executeUpdate();
                pstmt.close();
            }
            savedPlayers = zähler;
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
            pstmt = conn.prepareStatement("INSERT INTO Bank (UID, Money, Min) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, pa.getOwnerUID());
            pstmt.setLong(2, pa.getMoney());
            pstmt.setLong(3, pa.getMin());
            pstmt.executeUpdate();
            pstmt.close();
        }

        public void loadAllFromDatabase(HashMap<String, PlayerAccount> PlayerAccounts) throws SQLException, IOException, ClassNotFoundException {
            String uid;
            long money, min;
            List<BankStatement> Statements;
            List<BankMember> Members;
            int id;

            int zähler = 0;
            try (ResultSet result = Database.executeQuery("SELECT * FROM 'Bank'")) {
                while (result.next()) {
                    zähler = +1;
                    id = result.getInt("ID");
                    uid = result.getString("UID");
                    min = result.getLong("Min");
                    money = result.getLong("Money");
                    Members = (List<BankMember>) format.toObject(result.getBytes("Members"));
                    Statements = (List<BankStatement>) format.toObject(result.getBytes("Statements"));
                    PlayerAccount pa = new PlayerAccount(plugin, Console, uid);
                    pa.setMoney(money);
                    pa.setMin(min);
                    pa.addAllStatements(Statements);
                    pa.addAllMembers(Members);
                    PlayerAccounts.put(uid, pa);
                }
            }
            savedAccounts = zähler;
            Console.sendInfo("DB-Bank", "Load " + zähler + " accounts from database!");

        }

        public void saveAllToDatabase(HashMap<String, PlayerAccount> PlayerAccounts) throws SQLException, IOException {
            int zähler = 0;
            for (PlayerAccount pa : PlayerAccounts.values()) {
                zähler += 1;
                pstmt = conn.prepareStatement("UPDATE Bank SET Money=?, Members=?, Statements=?, Min=? WHERE UID='" + pa.getOwnerUID() + "'");
                pstmt.setLong(1, pa.getMoney());
                pstmt.setBytes(2, format.toBlob(pa.getMembers()));
                pstmt.setBytes(3, format.toBlob(pa.getStatements()));
                pstmt.setLong(4, pa.getMin());
                pstmt.executeUpdate();
                pstmt.close();
            }
            savedAccounts = zähler;
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("DB-Bank", "Save " + zähler + " account(s) to the database");
            }
        }

    }

    public class TableAtm {

        private final Database Database;
        private final Connection conn;
        private PreparedStatement pstmt;

        public TableAtm(iConomy plugin, icConsole Console, Database db) {
            this.Database = db;
            conn = db.getConnection();
        }

        public AtmObject add(PrefabVorlage auswahl, AtmType type, Vector3f pos, Quaternion rot) throws SQLException {
            pstmt = conn.prepareStatement("INSERT INTO Atm (Type, PosX, PosY, PosZ, RotW, RotX, RotY, RotZ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, type.getId());
            pstmt.setFloat(2, pos.x);
            pstmt.setFloat(3, pos.y);
            pstmt.setFloat(4, pos.z);
            pstmt.setFloat(5, rot.w);
            pstmt.setFloat(6, rot.x);
            pstmt.setFloat(7, rot.y);
            pstmt.setFloat(8, rot.z);
            pstmt.addBatch();

            conn.setAutoCommit(false);
            pstmt.executeBatch();
            conn.setAutoCommit(true);
            //pstmt.executeUpdate();
            int id;
            try (ResultSet result = pstmt.getGeneratedKeys()) {
                result.next();
                id = result.getInt(1);
            }
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("DB-ATM-add", "Add ID: " + id);
            }
            pstmt.close();

            AtmObject atm = new AtmObject(id, auswahl, type, pos, rot, plugin, Console);
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("DB-ATM-add", "ATM = " + atm);
            }
            return atm;
        }

        public List<Integer> getAllAtmIDs() throws SQLException {
            List<Integer> list = new ArrayList<>();
            try (ResultSet result = Database.executeQuery("SELECT * FROM 'Atm'")) {
                while (result.next()) {
                    list.add(result.getInt("ID"));
                }
            }
            return list;
        }

        public void remove(AtmObject atm) throws SQLException {
            pstmt = conn.prepareStatement("DELETE FROM Atm WHERE ID=" + atm.getDbID());
            pstmt.executeUpdate();
            pstmt.close();
        }

        public void loadAllFromDatabase(List<AtmObject> atms) throws SQLException, IOException, ClassNotFoundException {
            Vector3f pos;
            Quaternion rot;
            int ID;
            AtmUtils aU = new AtmUtils();
            AtmType type;
            int zähler = 0;

            try (ResultSet result = Database.executeQuery("SELECT * FROM 'Atm'")) {
                while (result.next()) {
                    zähler += 1;
                    ID = result.getInt("ID");
                    pos = new Vector3f(result.getFloat("PosX"), result.getFloat("PosY"), result.getFloat("PosZ"));
                    rot = new Quaternion(result.getFloat("RotX"), result.getFloat("RotY"), result.getFloat("RotZ"), result.getFloat("RotW"));
                    type = aU.getAtmType(result.getInt("Type"));

                    AtmObject atm = new AtmObject(ID, plugin.GameObject.getListBundle().get("ATM"), type, pos, rot, plugin, Console);
                    atms.add(atm);
                }
            }
            savedAtm = zähler;
            Console.sendInfo("DB-Bank", "Load " + zähler + " ATMs from the database");
        }

        public void saveAllToDatabase(List<AtmObject> atms) throws SQLException, IOException {
            int zähler = 0;
            for (AtmObject atm : atms) {
                zähler += 1;
                pstmt = conn.prepareStatement("UPDATE Atm SET Type=?, PosX=?, PosY=?, PosZ=?, RotW=?, RotX=?, RotY=?, RotZ=? WHERE AtmID='" + atm.getDbID() + "'");
                pstmt.setInt(1, atm.getType().getId());
                pstmt.setFloat(2, atm.getLocalPosition().x);
                pstmt.setFloat(3, atm.getLocalPosition().y);
                pstmt.setFloat(4, atm.getLocalPosition().z);
                pstmt.setFloat(5, atm.getLocalRotation().w);
                pstmt.setFloat(6, atm.getLocalRotation().x);
                pstmt.setFloat(7, atm.getLocalRotation().y);
                pstmt.setFloat(8, atm.getLocalRotation().z);
                pstmt.executeUpdate();
                pstmt.close();
            }
            savedAtm = zähler;
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("DB-Bank", "Save " + zähler + " ATMs to the database");
            }
        }
    }

}
