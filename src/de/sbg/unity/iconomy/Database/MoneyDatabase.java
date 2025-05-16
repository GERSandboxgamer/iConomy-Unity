package de.sbg.unity.iconomy.Database;

import de.sbg.unity.iconomy.Banksystem.BankMember;
import de.sbg.unity.iconomy.Banksystem.PlayerAccount;
import de.sbg.unity.iconomy.Objects.AtmObject;
import de.sbg.unity.iconomy.Utils.AtmUtils;
import de.sbg.unity.iconomy.Utils.AtmUtils.AtmType;
import de.sbg.unity.iconomy.Utils.BankStatement;
import de.sbg.unity.iconomy.Utils.DatabaseFormat;
import de.sbg.unity.iconomy.Utils.PlayerAccountPermission;
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

        Database.execute("PRAGMA foreign_keys = ON");

        Database.execute("CREATE TABLE IF NOT EXISTS Cash ("
                + "ID INTEGER PRIMARY KEY NOT NULL, " //AUTOINCREMENT
                + "UID TXT, "
                + "Money BIGINT, "
                + "More TXT "
                + "); ");
        Database.execute("""       
            CREATE TABLE IF NOT EXISTS Bank (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                UID TEXT, 
                Money BIGINT,
                Min BIGINT,
                Statements BLOB,
                More TEXT);
        """);
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
        Database.execute("""
            CREATE TABLE IF NOT EXISTS playeraccount_members (
                account_id INTEGER NOT NULL,
                member_uid TEXT NOT NULL,
                PRIMARY KEY (account_id, member_uid),
                FOREIGN KEY (account_id) REFERENCES Bank(ID) 
                    ON DELETE CASCADE 
                    ON UPDATE NO ACTION
            );
        """);

        Database.execute("""
            CREATE TABLE IF NOT EXISTS playeraccount_member_permissions (
                account_id INTEGER NOT NULL,
                member_uid TEXT NOT NULL,
                permission TEXT NOT NULL,
                PRIMARY KEY (account_id, member_uid, permission),
                FOREIGN KEY (account_id, member_uid) 
                    REFERENCES playeraccount_members(account_id, member_uid)
                    ON DELETE CASCADE
                    ON UPDATE NO ACTION
            );
        """);

    }

    public class TableNpc {

        private final Database database;
        private final Connection conn;
        private PreparedStatement pstmt;

        public TableNpc(Database db, icConsole Console) {
            this.database = db;
            this.conn = db.getConnection();
        }

        public int add(Npc npc, int mode) throws SQLException {
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

        public void saveAll(List<Npc> list) throws SQLException {
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

        public void loadAll(List<Npc> list) throws SQLException {
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

        private final Database db;
        private final Connection conn;
        private PreparedStatement pstmt;

        public TableBank(iConomy plugin, icConsole Console, Database db) {
            this.db = db;
            this.conn = db.getConnection();
        }

        /**
         * Fügt einen neuen PlayerAccount in die Bank-Tabelle ein. (Nur die
         * Grunddaten, kein Members-BLOB mehr!)
         * @param pa
         * @throws java.sql.SQLException
         * @throws java.io.IOException
         */
        public void add(PlayerAccount pa) throws SQLException, IOException {
            String sql = "INSERT INTO Bank (UID, Money, Min, Statements) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pa.getOwnerUID());
            pstmt.setLong(2, pa.getMoney());
            pstmt.setLong(3, pa.getMin());
            pstmt.setBytes(4, format.toBlob(pa.getStatements())); // Statements = BLOB
            pstmt.executeUpdate();
            pstmt.close();

            Console.sendInfo("DB-Bank", "Neuer PlayerAccount für " + pa.getOwnerUID() + " eingefügt.");
        }

        /**
         * Lädt alle PlayerAccounts aus der Bank-Tabelle in das übergebene
         * HashMap. Key = UID, Value = PlayerAccount.
         * @param PlayerAccounts
         * @throws java.sql.SQLException
         * @throws java.io.IOException
         * @throws java.lang.ClassNotFoundException
         */
        @SuppressWarnings("unchecked")
        public void loadAllFromDatabase(HashMap<String, PlayerAccount> PlayerAccounts) throws SQLException, IOException, ClassNotFoundException {
            int count = 0;
            String sql = "SELECT * FROM 'Bank'";
            try (ResultSet rs = db.executeQuery(sql)) {
                while (rs.next()) {
                    count++;
                    int id = rs.getInt("ID");
                    String uid = rs.getString("UID");
                    long money = rs.getLong("Money");
                    long min = rs.getLong("Min");
                    byte[] stmts = rs.getBytes("Statements");

                    // PlayerAccount anlegen
                    PlayerAccount pa = new PlayerAccount(plugin, Console, uid);
                    pa.setMoney(money);
                    pa.setMin(min);

                    // Statements via BLOB
                    if (stmts != null && stmts.length > 0) {
                        List<BankStatement> list = (List<BankStatement>) format.toObject(stmts);
                        pa.addAllStatements(list);
                    }

                    // Members + Permissions aus den neuen Tabellen laden
                    loadMembersAndPermissions(id, pa);

                    // In die Map packen
                    PlayerAccounts.put(uid, pa);
                }
            }
            Console.sendInfo("DB-Bank", "Load " + count + " PlayerAccounts aus der DB.");
        }

        /**
         * Lädt alle Member (und deren Permissions) aus playeraccount_members
         * und playeraccount_member_permissions, fügt sie dem PlayerAccount
         * hinzu.
         */
        private void loadMembersAndPermissions(int accountId, PlayerAccount pa) throws SQLException {

            // 1) Alle Member
            String sqlMembers = "SELECT member_uid FROM playeraccount_members WHERE account_id=?";
            try (PreparedStatement ps = conn.prepareStatement(sqlMembers)) {
                ps.setInt(1, accountId);
                try (ResultSet rsMem = ps.executeQuery()) {
                    while (rsMem.next()) {
                        String memberUID = rsMem.getString("member_uid");
                        BankMember bm = new BankMember(memberUID);

                        // 2) Für jeden Member die einzelnen Permissions
                        String sqlPerm = """
                        SELECT permission 
                        FROM playeraccount_member_permissions
                        WHERE account_id=? AND member_uid=?
                    """;
                        try (PreparedStatement psPerm = conn.prepareStatement(sqlPerm)) {
                            psPerm.setInt(1, accountId);
                            psPerm.setString(2, memberUID);
                            try (ResultSet rsPerm = psPerm.executeQuery()) {
                                while (rsPerm.next()) {
                                    String permString = rsPerm.getString("permission");
                                    // Enum konvertieren
                                    PlayerAccountPermission enumPerm = PlayerAccountPermission.valueOf(permString);
                                    bm.addPermission(enumPerm);
                                }
                            }
                        }

                        // Member dem PlayerAccount hinzufügen
                        pa.addMember(bm);
                    }
                }
            }
        }

        /**
         * Speichert alle PlayerAccounts in der DB (Bank-Tabelle +
         * Member/Permissions).
         * @param PlayerAccounts
         * @throws java.sql.SQLException
         * @throws java.io.IOException
         */
        public void saveAllToDatabase(HashMap<String, PlayerAccount> PlayerAccounts) throws SQLException, IOException {
            int count = 0;
            for (PlayerAccount pa : PlayerAccounts.values()) {
                count++;
                // 1) Grunddaten in der Bank-Tabelle updaten
                String sql = "UPDATE Bank SET Money=?, Min=?, Statements=? WHERE UID=?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setLong(1, pa.getMoney());
                pstmt.setLong(2, pa.getMin());
                pstmt.setBytes(3, format.toBlob(pa.getStatements()));
                pstmt.setString(4, pa.getOwnerUID());
                pstmt.executeUpdate();
                pstmt.close();

                // 2) ID holen (Bank.ID) -> account_id
                int bankId = getBankIdByUID(pa.getOwnerUID());
                if (bankId < 0) {
                    Console.sendErr("DB-Bank", "Konnte Bank ID nicht finden für " + pa.getOwnerUID());
                    continue;
                }

                // 3) Alte Member-Einträge löschen
                String delMem = "DELETE FROM playeraccount_members WHERE account_id=?";
                try (PreparedStatement psDel = conn.prepareStatement(delMem)) {
                    psDel.setInt(1, bankId);
                    psDel.executeUpdate();
                }

                // 3.1) Alte Permissions-Einträge löschen
                String delPerm = "DELETE FROM playeraccount_member_permissions WHERE account_id=?";
                try (PreparedStatement psDel = conn.prepareStatement(delPerm)) {
                    psDel.setInt(1, bankId);
                    psDel.executeUpdate();
                }

                // 4) Neue Member + Permissions einfügen
                for (BankMember bm : pa.getMembers()) {
                    // a) Eintrag in playeraccount_members
                    String insMem = "INSERT INTO playeraccount_members (account_id, member_uid) VALUES (?, ?)";
                    try (PreparedStatement psMem = conn.prepareStatement(insMem)) {
                        psMem.setInt(1, bankId);
                        psMem.setString(2, bm.getUID());
                        psMem.executeUpdate();
                    }

                    // b) Permissions
                    for (PlayerAccountPermission perm : bm.getPermissions()) {
                        String insPerm = """
                        INSERT INTO playeraccount_member_permissions (account_id, member_uid, permission)
                        VALUES (?, ?, ?)
                    """;
                        try (PreparedStatement psPerm = conn.prepareStatement(insPerm)) {
                            psPerm.setInt(1, bankId);
                            psPerm.setString(2, bm.getUID());
                            psPerm.setString(3, perm.name());
                            psPerm.executeUpdate();
                        }
                    }
                }
            }
            Console.sendInfo("DB-Bank", "Save " + count + " PlayerAccounts to the database.");
        }

        /**
         * Hilfshelfer: Hole den Bank.ID-Eintrag anhand der OwnerUID (Spalte
         * UID).
         */
        private int getBankIdByUID(String ownerUID) throws SQLException {
            String sql = "SELECT ID FROM Bank WHERE UID=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, ownerUID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("ID");
                    }
                }
            }
            return -1;
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
                pstmt = conn.prepareStatement("UPDATE Atm SET Type=?, PosX=?, PosY=?, PosZ=?, RotW=?, RotX=?, RotY=?, RotZ=? WHERE ID='" + atm.getDbID() + "'");
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
