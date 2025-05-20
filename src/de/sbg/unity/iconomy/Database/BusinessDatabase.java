package de.sbg.unity.iconomy.Database;

import de.sbg.unity.iconomy.Banksystem.BusinessAccount;
import de.sbg.unity.iconomy.Banksystem.BusinessBankMember;
import de.sbg.unity.iconomy.Business.Business;
import de.sbg.unity.iconomy.Business.BusinessPlots.BusinessPlot;
import de.sbg.unity.iconomy.Utils.BankStatement;
import de.sbg.unity.iconomy.Utils.DatabaseFormat;
import de.sbg.unity.iconomy.Utils.BusinessAccountPermission;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import net.risingworld.api.database.Database;
import net.risingworld.api.utils.Quaternion;
import net.risingworld.api.utils.Vector3f;

public class BusinessDatabase {

    private final iConomy plugin;
    private final icConsole Console;
    private final Database Database;

    public final TabelBank TabBank;
    public final TabelBusiness TabBusiness;
    public final TabelPlots TabelPlots;

    public BusinessDatabase(iConomy plugin, icConsole console) {
        this.plugin = plugin;
        this.Console = console;

        this.Database = plugin.getSQLiteConnection(plugin.getPath() + "/Databases/BusinessDatabase.db");
        this.TabBank = new TabelBank(plugin, console, Database);
        this.TabBusiness = new TabelBusiness(plugin, console, Database);
        this.TabelPlots = new TabelPlots(plugin, console, Database);
    }

    public Database getDatabase() {
        return Database;
    }

    public void close() throws SQLException {
        TabBank.conn.close();
        TabBusiness.conn.close();
        Database.close();
    }

    public void createDatabse() {
        Database.execute("PRAGMA foreign_keys=ON");

        Database.execute("CREATE TABLE IF NOT EXISTS Bank ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + "BusinessID INTEGER, "
                + "Money BIGINT, "
                + "Min BIGINT, "
                + "Statements BLOB"
                + ");");

        Database.execute("CREATE TABLE IF NOT EXISTS businessaccount_members ("
                + "account_id INTEGER NOT NULL, "
                + "member_uid TEXT NOT NULL, "
                + "PRIMARY KEY (account_id, member_uid), "
                + "FOREIGN KEY (account_id) REFERENCES Bank(ID) ON DELETE CASCADE"
                + ");");

        Database.execute("CREATE TABLE IF NOT EXISTS businessaccount_member_permissions ("
                + "account_id INTEGER NOT NULL, "
                + "member_uid TEXT NOT NULL, "
                + "permission TEXT NOT NULL, "
                + "PRIMARY KEY (account_id, member_uid, permission), "
                + "FOREIGN KEY (account_id, member_uid) REFERENCES businessaccount_members(account_id, member_uid) ON DELETE CASCADE"
                + ");");

        Database.execute("CREATE TABLE IF NOT EXISTS Business ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + "BusinessName TEXT, "
                + "BusinessBankID TEXT, "
                + "Cash BIGINT DEFAULT 0, "
                + "Info BLOB, "
                + "TeleX FLOAT, TeleY FLOAT, TeleZ FLOAT, "
                + "TelRotX FLOAT, TelRotY FLOAT, TelRotZ FLOAT, TelRotW FLOAT, "
                + "ChestUID BIGINT, "
                + "BusinessType TEXT, "
                + "More TEXT"
                + ");");

        Database.execute("CREATE TABLE IF NOT EXISTS business_members ("
                + "business_id INTEGER NOT NULL, "
                + "member_uid TEXT NOT NULL, "
                + "Info BLOB, "
                + "PRIMARY KEY (business_id, member_uid), "
                + "FOREIGN KEY (business_id) REFERENCES business(ID) ON DELETE CASCADE"
                + ");");

        Database.execute("CREATE TABLE IF NOT EXISTS business_member_permissions ("
                + "business_id INTEGER NOT NULL, "
                + "member_uid TEXT NOT NULL, "
                + "permission TEXT NOT NULL, "
                + "PRIMARY KEY (business_id, member_uid, permission), "
                + "FOREIGN KEY (business_id, member_uid) "
                + "REFERENCES business_members(business_id, member_uid) ON DELETE CASCADE"
                + ");");

        Database.execute("CREATE TABLE IF NOT EXISTS business_member_action ("
                + "business_id INTEGER NOT NULL, "
                + "member_uid TEXT NOT NULL, "
                + "action TEXT NOT NULL, "
                + "typ TEXT NOT NULL, "
                + "action_uid TEXT NOT NULL, "
                + "price BIGINT DEFAULT 0, "
                + "PRIMARY KEY (business_id, member_uid, action, typ, action_uid), "
                + "FOREIGN KEY (business_id, member_uid) REFERENCES business_members(business_id, member_uid) ON DELETE CASCADE"
                + ");");

        Database.execute("CREATE TABLE IF NOT EXISTS Plots ("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + "Area BIGINT, "
                + "Plotname TEXT, "
                + "BusinessID INTEGER, "
                + "Price BIGINT"
                + ");");
        Database.execute("CREATE TABLE IF NOT EXISTS business_guest_permissions ("
                + "business_id INTEGER NOT NULL, "
                + "permission TEXT NOT NULL, "
                + "PRIMARY KEY (business_id, permission), "
                + "FOREIGN KEY (business_id) REFERENCES Business(ID) ON DELETE CASCADE"
                + ");");

    }

    public class TabelBank {

        private final Connection conn;
        private final Database db;
        private final DatabaseFormat DatabaseFormat;
        private final iConomy plugin;

        public TabelBank(iConomy plugin, icConsole console, Database db) {
            this.plugin = plugin;
            this.db = db;
            this.conn = db.getConnection();
            this.DatabaseFormat = new DatabaseFormat();
        }

        public int add(Business f) throws SQLException, IOException {
            int id;
            List<BankStatement> stList = new ArrayList<>();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Bank (BusinessID, Money, Min, Statements) VALUES (?, ?, ?, ?)");
            pstmt.setInt(1, f.getID());
            pstmt.setLong(2, 0);
            pstmt.setLong(3, 0);
            pstmt.setBytes(4, DatabaseFormat.toBlob(stList));
            pstmt.executeUpdate();
            pstmt.close();

            try (ResultSet result = db.executeQuery("SELECT * FROM 'Bank' WHERE BusinessID=" + f.getID())) {
                id = result.getInt("ID");
            }
            return id;
        }

        public void remove(BusinessAccount fa) throws SQLException {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Bank WHERE ID=" + fa.getAccountID());
            pstmt.executeUpdate();
            pstmt.close();
        }

        public void loadAllFromDatabase(HashMap<Business, BusinessAccount> BusinessAccounts)
                throws SQLException, IOException, ClassNotFoundException {
            try (ResultSet result = db.executeQuery("SELECT * FROM 'Bank'")) {
                while (result.next()) {
                    int id = result.getInt("ID");
                    int BusinessID = result.getInt("BusinessID");
                    long money = result.getLong("Money");
                    long min = result.getLong("Min");
                    byte[] stmts = result.getBytes("Statements");

                    Business f = plugin.Business.getBusinessByID(BusinessID);
                    if (f == null) {
                        continue; // Sicherheitsabfrage
                    }
                    BusinessAccount fa = new BusinessAccount(plugin, Console, f, id);
                    fa.setMoney(money);
                    fa.setMin(min);

                    if (stmts != null && stmts.length > 0) {
                        List<BankStatement> list = (List<BankStatement>) DatabaseFormat.toObject(stmts);
                        fa.addAllStatements(list);
                    }

                    loadMembersAndPermissions(id, fa);

                    BusinessAccounts.put(f, fa);
                }
            }
        }

        private void loadMembersAndPermissions(int accountId, BusinessAccount fa) throws SQLException {
            String sqlMembers = "SELECT member_uid FROM businessaccount_members WHERE account_id=?";
            try (PreparedStatement ps = conn.prepareStatement(sqlMembers)) {
                ps.setInt(1, accountId);
                try (ResultSet rsMem = ps.executeQuery()) {
                    while (rsMem.next()) {
                        String memberUID = rsMem.getString("member_uid");
                        BusinessBankMember fbm = new BusinessBankMember(memberUID);

                        String sqlPerm = "SELECT permission FROM businessaccount_member_permissions "
                                + "WHERE account_id=? AND member_uid=?";
                        try (PreparedStatement psPerm = conn.prepareStatement(sqlPerm)) {
                            psPerm.setInt(1, accountId);
                            psPerm.setString(2, memberUID);

                            try (ResultSet rsPerm = psPerm.executeQuery()) {
                                while (rsPerm.next()) {
                                    String perm = rsPerm.getString("permission");
                                    fbm.addPermission(BusinessAccountPermission.valueOf(perm));
                                }
                            }
                        }
                        fa.addMember(null, fbm);
                    }
                }
            }
        }

        public void saveAllToDatabase(Collection<BusinessAccount> BusinessAccounts) throws SQLException, IOException {
            for (BusinessAccount fa : BusinessAccounts) {
                PreparedStatement pstmt = conn.prepareStatement("UPDATE Bank SET Money=?, Min=?, Statements=? WHERE ID=?");
                pstmt.setLong(1, fa.getMoney());
                pstmt.setLong(2, fa.getMin());
                pstmt.setBytes(3, DatabaseFormat.toBlob(fa.getStatements()));
                pstmt.setInt(4, fa.getAccountID());
                pstmt.executeUpdate();
                pstmt.close();

                // Erst alle alten Members & Permissions löschen
                int accountId = fa.getAccountID();

                String delMem = "DELETE FROM businessaccount_members WHERE account_id=?";
                try (PreparedStatement psDel = conn.prepareStatement(delMem)) {
                    psDel.setInt(1, accountId);
                    psDel.executeUpdate();
                }

                String delPerm = "DELETE FROM businessaccount_member_permissions WHERE account_id=?";
                try (PreparedStatement psDel = conn.prepareStatement(delPerm)) {
                    psDel.setInt(1, accountId);
                    psDel.executeUpdate();
                }

                // Neue Members + deren Permissions speichern
                for (BusinessBankMember fbm : fa.getMembers()) {
                    String insMem = "INSERT INTO businessaccount_members (account_id, member_uid) VALUES (?, ?)";
                    try (PreparedStatement psMem = conn.prepareStatement(insMem)) {
                        psMem.setInt(1, accountId);
                        psMem.setString(2, fbm.getUID());
                        psMem.executeUpdate();
                    }

                    for (String perm : fbm.getPermissionsAsStringList()) {
                        String insPerm = "INSERT INTO businessaccount_member_permissions (account_id, member_uid, permission) "
                                + "VALUES (?, ?, ?)";
                        try (PreparedStatement psPerm = conn.prepareStatement(insPerm)) {
                            psPerm.setInt(1, accountId);
                            psPerm.setString(2, fbm.getUID());
                            psPerm.setString(3, perm);
                            psPerm.executeUpdate();
                        }
                    }
                }
            }
        }
    }

    // Restliche TabelBusiness und TabelPlots bleiben unverändert!
    public class TabelBusiness {

        private final Connection conn;
        private PreparedStatement pstmt;
        private final Database Database;
        private final DatabaseFormat DatabaseFormat;

        public TabelBusiness(iConomy plugin, icConsole console, Database db) {
            this.Database = db;
            conn = db.getConnection();
            DatabaseFormat = new DatabaseFormat();
        }

        public int add(String BusinessName) throws SQLException {
            int id = -1;

            pstmt = conn.prepareStatement("INSERT INTO Business (BusinessName) VALUES (?)");
            pstmt.setString(1, BusinessName);
            pstmt.executeUpdate();

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT last_insert_rowid()");

            if (rs.next()) {
                id = rs.getInt(1);
            }
            pstmt.close();

            return id;
        }

        public void remove(Business b) throws SQLException {
            pstmt = conn.prepareStatement("DELETE FROM Business WHERE ID=" + b.getID());
            pstmt.executeUpdate();
            pstmt.close();
        }

        public void loadAllFromDatabase(HashMap<Integer, Business> BusinessList) throws SQLException, IOException, ClassNotFoundException {
            int BusinessID;
            String BusinessName, BusinessBankID;
            List<String> Info;
            long Cash;
            //List<Long> Plots;

            Vector3f Tele;
            Quaternion Rot;
            long ChestUID;

            try (ResultSet result = Database.executeQuery("SELECT * FROM 'Business'")) {
                while (result.next()) {
                    BusinessID = result.getInt("ID");
                    BusinessName = result.getString("BusinessName");
                    BusinessBankID = result.getString("BusinessBankID");
                    Cash = result.getLong("Cash");
                    //Plots = (List<Long>) DatabaseFormat.toObject(result.getBytes("Plots"));
                    Info = (List<String>) DatabaseFormat.toObject(result.getBytes("Info"));
                    Tele = new Vector3f(result.getFloat("TeleX"), result.getFloat("TeleY"), result.getFloat("TeleZ"));
                    Rot = new Quaternion(result.getFloat("TelRotX"), result.getFloat("TelRotY"), result.getFloat("TelRotZ"), result.getFloat("TelRotW"));
                    ChestUID = result.getLong("ChestUID");

                    Business b = new Business(plugin, BusinessName, BusinessID, BusinessBankID);
                    //b.addAllOwners(Owners);
                    b.setCash(Cash);
                    b.addAllInfos(Info);
                    b.setTelepoint(Tele);
                    b.setTelepointRotation(Rot);
                    b.setChestUID(ChestUID);
                    //f.addAllPlots(Plots);
                    BusinessList.put(BusinessID, b);
                }
            }
        }

        public void saveAllToDatabase(Collection<Business> Businesss) throws SQLException, IOException {
            for (Business f : Businesss) {
                pstmt = conn.prepareStatement("UPDATE Business SET BusinessName=?, Cash=?, Info=?, "
                        + "TeleX=?, TeleY=?, TeleZ=?, TelRotX=?, TelRotY=?, TelRotZ=?, TelRotW=?, ChestUID=? WHERE ID=" + f.getID());
                pstmt.setString(1, f.getName());
                pstmt.setLong(3, f.getCash());
                pstmt.setBytes(6, DatabaseFormat.toBlob(f.getInfos()));
                pstmt.setFloat(7, f.getTelepoint().x);
                pstmt.setFloat(8, f.getTelepoint().y);
                pstmt.setFloat(9, f.getTelepoint().z);
                pstmt.setFloat(10, f.getTelepointRotation().x);
                pstmt.setFloat(11, f.getTelepointRotation().y);
                pstmt.setFloat(12, f.getTelepointRotation().z);
                pstmt.setFloat(13, f.getTelepointRotation().w);
                pstmt.setLong(14, f.getChestUID());
                pstmt.executeUpdate();
                pstmt.close();
            }
        }
    }

    public class TabelPlots {

        private final Connection conn;
        private PreparedStatement pstmt;
        private final Database Database;
        private final DatabaseFormat DatabaseFormat;

        public TabelPlots(iConomy plugin, icConsole console, Database db) {
            this.Database = db;
            conn = db.getConnection();
            DatabaseFormat = new DatabaseFormat();
        }

        public int add(BusinessPlot fa) throws SQLException {
            int id = -1;

            pstmt = conn.prepareStatement("INSERT INTO Plots (Area) VALUES (?)");
            pstmt.setLong(1, fa.getArea().getID());
            pstmt.setString(2, fa.getName());
            pstmt.setLong(3, fa.getPrice());
            if (fa.getBusiness() != null) {
                pstmt.setInt(4, fa.getBusiness().getID());
            } else {
                pstmt.setInt(4, -1);
            }
            pstmt.executeUpdate();

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT last_insert_rowid()");

            if (rs.next()) {
                id = rs.getInt(1);
            }
            pstmt.close();

            return id;
        }

        public void remove(long areaID) throws SQLException {
            pstmt = conn.prepareStatement("DELETE FROM Plots WHERE Area=" + areaID);
            pstmt.executeUpdate();
            pstmt.close();
        }

        public void loadAllFromDatabase() throws SQLException, IOException, ClassNotFoundException {

        }

        public void saveAllToDatabase() throws SQLException, IOException {

        }
    }
}
