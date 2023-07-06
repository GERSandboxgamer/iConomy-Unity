package de.sbg.unity.iconomy.Database;

import de.sbg.unity.iconomy.Banksystem.FactoryAccount;
import de.sbg.unity.iconomy.Banksystem.FactoryBankMember;
import de.sbg.unity.iconomy.Factory.Factory;
import de.sbg.unity.iconomy.Factory.FactoryMember;
import de.sbg.unity.iconomy.Factory.FactoryWorker;
import de.sbg.unity.iconomy.Utils.DatabaseFormat;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import net.risingworld.api.database.Database;
import net.risingworld.api.objects.Area;
import net.risingworld.api.utils.Quaternion;
import net.risingworld.api.utils.Vector3f;

public class FactoryDatabase {

    private final iConomy plugin;
    private final icConsole Console;
    private final Database Database;

    public TabelBank TabBank;
    public TabelFactory TabFactory;

    public FactoryDatabase(iConomy plugin, icConsole console) {
        this.plugin = plugin;
        this.Console = console;
        this.Database = plugin.getSQLiteConnection(plugin.getPath() + "/Databases/FactoryDatabase.db");
        this.TabBank = new TabelBank(plugin, console, Database);
        this.TabFactory = new TabelFactory(plugin, console, Database);
    }

    public Database getDatabase() {
        return Database;
    }

    public void close() throws SQLException {
        TabBank.conn.close();
        TabFactory.conn.close();
        Database.close();
    }

    public void createDatabse(){
        Database.execute("CREATE TABLE IF NOT EXISTS Bank ("
                + "ID INTEGER PRIMARY KEY NOT NULL, " //AUTOINCREMENT
                + "FactoryID INTEGER, "
                + "Money BIGINT, "
                + "Members BLOB, "
                + "Min BIGINT, "
                + "Statements BLOB, "
                + "More BLOB "
                + "); ");
        Database.execute("CREATE TABLE IF NOT EXISTS Factory ("
                + "ID INTEGER PRIMARY KEY NOT NULL, " //AUTOINCREMENT
                + "FactoryName TXT,"
                + "Owners BLOB,"
                + "Cash BIGINT, "
                + "Members BLOB, "
                + "Workers BLOB, "
                + "Plots BLOB, "
                + "Info BLOB, "
                + "TeleX FLOAT, "
                + "TeleY FLOAT, "
                + "TeleZ FLOAT, "
                + "RotX FLOAT, "
                + "RotY FLOAT, "
                + "RotZ FLOAT, "
                + "RotW FLOAT, "
                + "ChestUID BIGINT, "
                + "PriceList BLOB, "
                + "More BLOB "
                + "); ");
    }

    public class TabelBank {

        private final Connection conn;
        private PreparedStatement pstmt;
        private final Database Database;
        private final DatabaseFormat DatabaseFormat;
        private final iConomy plugin;

        public TabelBank(iConomy plugin, icConsole console, Database db) {
            this.plugin = plugin;
            this.Database = db;
            conn = db.getConnection();
            DatabaseFormat = new DatabaseFormat();
        }

        public int add(Factory f) throws SQLException, IOException {
            int id;

            pstmt = conn.prepareStatement("INSERT INTO Bank (FactoryID, Money, Min) VALUES (?, ?, ?)");
            pstmt.setInt(1, f.getID());
            pstmt.setLong(2, 0);
            pstmt.setLong(3, 0);
            pstmt.executeUpdate();
            pstmt.close();

            try (ResultSet result = Database.executeQuery("SELECT * FROM 'Bank' WHERE FactoryID=" + f.getID())) {
                id = result.getInt("ID");
            }
            return id;
        }

        public void remove(FactoryAccount fa) throws SQLException {
            pstmt = conn.prepareStatement("DELETE FROM Factory WHERE ID=" + fa.getAccountID());
            pstmt.executeUpdate();
            pstmt.close();
        }

        public void loadAllFromDatabase(HashMap<Factory, FactoryAccount> FactoryAccounts) throws SQLException, IOException, ClassNotFoundException {
            int FactoryID;
            long money, min;
            int id;
            List<FactoryBankMember> Members;
            List<String> state;

            try (ResultSet result = Database.executeQuery("SELECT * FROM 'Bank'")) {
                while (result.next()) {
                    id = result.getInt("ID");
                    FactoryID = result.getInt("FactoryID");
                    money = result.getLong("Money");
                    Members = (List<FactoryBankMember>) DatabaseFormat.toObject(result.getBytes("Members"));
                    min = result.getLong("Min");
                    state = (List<String>) DatabaseFormat.toObject(result.getBytes("Statements"));

                    Factory f = plugin.Factory.getFactoryByID(FactoryID);
                    FactoryAccount a = new FactoryAccount(plugin, f, id);
                    a.setMin(min);
                    a.setMoney(money);
                    for (FactoryBankMember m : Members) {
                        a.getMembers().add(m);
                    }
                    for (String s : state) {
                        a.getStatements().add(s);
                    }
                    
                    FactoryAccounts.put(f, a);
                }
            }

        }

        public void saveAllToDatabase(Collection<FactoryAccount> FactoryAccounts) throws SQLException, IOException {
            for (FactoryAccount fa : FactoryAccounts) {
                pstmt = conn.prepareStatement("UPDATE Bank SET Money=?, Members=?, Min=?  WHERE ID=" +  fa.getAccountID());
                pstmt.setLong(1, fa.getMoney());
                pstmt.setBytes(2, DatabaseFormat.toBlob(fa.getMembers()));
                pstmt.setLong(3, fa.getMin());
                pstmt.executeUpdate();
                pstmt.close();
            }
        }

    }

    public class TabelFactory {

        private final Connection conn;
        private PreparedStatement pstmt;
        private final Database Database;
        private final DatabaseFormat DatabaseFormat;

        public TabelFactory(iConomy plugin, icConsole console, Database db) {
            this.Database = db;
            conn = db.getConnection();
            DatabaseFormat = new DatabaseFormat();
        }

        public int add(String FactoryName) throws SQLException {
            int id;

            pstmt = conn.prepareStatement("INSERT INTO Factory (FactoryName) VALUES (?)");
            pstmt.setString(1, FactoryName);
            pstmt.executeUpdate();
            pstmt.close();

            try (ResultSet result = Database.executeQuery("SELECT * FROM 'Factory' WHERE FactoryName='" + FactoryName + "'")) {
                id = result.getInt("ID");
            }
            return id;
        }

        public void remove(Factory f) throws SQLException {
            pstmt = conn.prepareStatement("DELETE FROM Factory WHERE ID=" + f.getID());
            pstmt.executeUpdate();
            pstmt.close();
        }

        public void loadAllFromDatabase(HashMap<Integer, Factory> Factorys) throws SQLException, IOException, ClassNotFoundException {
            int FactoryID;
            String FactoryName;
            List<String> Owners, Info;
            long Cash;
            List<FactoryMember> Members;
            List<FactoryWorker> Workers;
            List<Area> Plots;
            Vector3f Tele;
            Quaternion Rot;
            long ChestUID;
            HashMap<Integer, Long> PriceList;

            try (ResultSet result = Database.executeQuery("SELECT * FROM 'Factory'")) {
                while (result.next()) {
                    FactoryID = result.getInt("FactoryID");
                    FactoryName = result.getString("FactoryName");
                    Owners = (List<String>) DatabaseFormat.toObject(result.getBytes("Owners"));
                    Cash = result.getLong("Cash");
                    Members = (List<FactoryMember>) DatabaseFormat.toObject(result.getBytes("Members"));
                    Workers = (List<FactoryWorker>) DatabaseFormat.toObject(result.getBytes("Workers"));
                    Plots = (List<Area>) DatabaseFormat.toObject(result.getBytes("Plots"));
                    Info = (List<String>) DatabaseFormat.toObject(result.getBytes("Info"));
                    Tele = new Vector3f(result.getFloat("TeleX"), result.getFloat("TeleY"), result.getFloat("TeleZ"));
                    Rot = new Quaternion(result.getFloat("RotX"), result.getFloat("RotY"), result.getFloat("RotZ"), result.getFloat("RotW"));
                    ChestUID = result.getLong("ChestUID");
                    PriceList = (HashMap<Integer, Long>) DatabaseFormat.toObject(result.getBytes("PriceList"));

                    Factory f = new Factory(plugin, FactoryName, FactoryID, PriceList);
                    for (String uid : Owners) {
                        f.getOwners().add(uid);
                    }
                    f.setCash(Cash);
                    for (FactoryMember m : Members) {
                        f.getMembers().add(m);
                    }
                    for (FactoryWorker w : Workers) {
                        f.getWorkers().add(w);
                    }
                    for (Area a : Plots) {
                        f.getPlots().add(a);
                    }
                    for (String i : Info) {
                        f.getInfos().add(i);
                    }
                    f.setTelepoint(Tele);
                    f.setTelepointRotation(Rot);
                    f.setChestUID(ChestUID);
                }
            }
        }

        public void saveAllToDatabase(Collection<Factory> Factorys) throws SQLException, IOException {
            for (Factory f : Factorys) {
                pstmt = conn.prepareStatement("UPDATE Factory SET FactoryName=?, Owners=?, Cash=?, Members=?, Workers=?, Plots=?, Info=?, "
                        + "TeleX=?, TeleY=?, TeleZ=?, RotX=?, RotY=?, RotZ=?, RotW=?, ChestUID=?, PriceList=? WHERE ID=" + f.getID());
                pstmt.setString(1, f.getName());
                pstmt.setBytes(2, DatabaseFormat.toBlob(f.getOwners()));
                pstmt.setLong(3, f.getCash());
                pstmt.setBytes(4, DatabaseFormat.toBlob(f.getMembers()));
                pstmt.setBytes(5, DatabaseFormat.toBlob(f.getWorkers()));
                pstmt.setBytes(6, DatabaseFormat.toBlob(f.getPlots()));
                pstmt.setBytes(7, DatabaseFormat.toBlob(f.getInfos()));
                pstmt.setFloat(8, f.getTelepoint().x);
                pstmt.setFloat(9, f.getTelepoint().y);
                pstmt.setFloat(10, f.getTelepoint().z);
                pstmt.setFloat(11, f.getTelepointRotation().x);
                pstmt.setFloat(12, f.getTelepointRotation().y);
                pstmt.setFloat(13, f.getTelepointRotation().z);
                pstmt.setFloat(14, f.getTelepointRotation().w);
                pstmt.setLong(15, f.getChestUID());
                pstmt.setBytes(16, DatabaseFormat.toBlob(f.getPriceList()));
                pstmt.executeUpdate();
                pstmt.close();
            }
        }
    }
}
