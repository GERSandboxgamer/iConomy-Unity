package de.sbg.unity.iconomy.Database;

import de.sbg.unity.iconomy.Banksystem.FactoryAccount;
import de.sbg.unity.iconomy.Banksystem.FactoryBankMember;
import de.sbg.unity.iconomy.Factory.Factory;
import de.sbg.unity.iconomy.Factory.FactoryMember;
import de.sbg.unity.iconomy.Factory.FactoryPlots.FactoryPlot;
import de.sbg.unity.iconomy.Factory.FactoryWorker;
import de.sbg.unity.iconomy.Utils.BankStatement;
import de.sbg.unity.iconomy.Utils.DatabaseFormat;
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

/**
 *
 * @hidden  
 */
public class FactoryDatabase {

    private final iConomy plugin;
    private final icConsole Console;
    private final Database Database;

    public final TabelBank TabBank;
    public final TabelFactory TabFactory;
    public final TabelPlots TabelPlots;

    public FactoryDatabase(iConomy plugin, icConsole console) {
        this.plugin = plugin;
        this.Console = console;
        this.Database = plugin.getSQLiteConnection(plugin.getPath() + "/Databases/FactoryDatabase.db");
        this.TabBank = new TabelBank(plugin, console, Database);
        this.TabFactory = new TabelFactory(plugin, console, Database);
        this.TabelPlots = new TabelPlots(plugin, console, Database);
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
        Database.execute("CREATE TABLE IF NOT EXISTS Plots ("
                + "ID INTEGER PRIMARY KEY NOT NULL, " //AUTOINCREMENT
                + "Area BIGINT, "
                + "Plotname TXT, "
                + "Factory INTEGER, "
                + "Price BIGINT, "
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
            List<BankStatement> stList = new ArrayList<>();
            pstmt = conn.prepareStatement("INSERT INTO Bank (FactoryID, Money, Min, Statements) VALUES (?, ?, ?, ?)");
            pstmt.setInt(1, f.getID());
            pstmt.setLong(2, 0);
            pstmt.setLong(3, 0);
            pstmt.setBytes(4, DatabaseFormat.toBlob(stList));
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
            List<BankStatement> state;

            try (ResultSet result = Database.executeQuery("SELECT * FROM 'Bank'")) {
                while (result.next()) {
                    id = result.getInt("ID");
                    FactoryID = result.getInt("FactoryID");
                    money = result.getLong("Money");
                    Members = (List<FactoryBankMember>) DatabaseFormat.toObject(result.getBytes("Members"));
                    min = result.getLong("Min");
                    state = (List<BankStatement>) DatabaseFormat.toObject(result.getBytes("Statements"));

                    Factory f = plugin.Factory.getFactoryByID(FactoryID);
                    FactoryAccount a = new FactoryAccount(plugin, Console, f, id);
                    a.setMin(min);
                    a.setMoney(money);
                    a.addAllMembers(Members);
                    a.addAllStatements(state);
                    
                    FactoryAccounts.put(f, a);
                }
            }

        }

        public void saveAllToDatabase(Collection<FactoryAccount> FactoryAccounts) throws SQLException, IOException {
            for (FactoryAccount fa : FactoryAccounts) {
                pstmt = conn.prepareStatement("UPDATE Bank SET Money=?, Members=?, Min=?, Statements=?  WHERE ID=" +  fa.getAccountID());
                pstmt.setLong(1, fa.getMoney());
                pstmt.setBytes(2, DatabaseFormat.toBlob(fa.getMembers()));
                pstmt.setLong(3, fa.getMin());
                pstmt.setBytes(4, DatabaseFormat.toBlob(fa.getStatements()));
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
            int id = -1;

            pstmt = conn.prepareStatement("INSERT INTO Factory (FactoryName) VALUES (?)");
            pstmt.setString(1, FactoryName);
            pstmt.executeUpdate();
            
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT last_insert_rowid()");
            
            if (rs.next()) {
                id = rs.getInt(1);
            }
            pstmt.close();
            
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
            //List<Long> Plots;
            List<FactoryMember> Members;
            List<FactoryWorker> Workers;
           
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
                    //Plots = (List<Long>) DatabaseFormat.toObject(result.getBytes("Plots"));
                    Members = (List<FactoryMember>) DatabaseFormat.toObject(result.getBytes("Members"));
                    Workers = (List<FactoryWorker>) DatabaseFormat.toObject(result.getBytes("Workers"));
                    Info = (List<String>) DatabaseFormat.toObject(result.getBytes("Info"));
                    Tele = new Vector3f(result.getFloat("TeleX"), result.getFloat("TeleY"), result.getFloat("TeleZ"));
                    Rot = new Quaternion(result.getFloat("RotX"), result.getFloat("RotY"), result.getFloat("RotZ"), result.getFloat("RotW"));
                    ChestUID = result.getLong("ChestUID");
                    PriceList = (HashMap<Integer, Long>) DatabaseFormat.toObject(result.getBytes("PriceList"));

                    Factory f = new Factory(plugin, FactoryName, FactoryID, PriceList);
                    f.addAllOwners(Owners);
                    f.setCash(Cash);
                    f.addAllMembers(Members);
                    f.addAllWorkers(Workers);
                    f.addAllInfos(Info);
                    f.setTelepoint(Tele);
                    f.setTelepointRotation(Rot);
                    f.setChestUID(ChestUID);
                    //f.addAllPlots(Plots);
                }
            }
        }

        public void saveAllToDatabase(Collection<Factory> Factorys) throws SQLException, IOException {
            for (Factory f : Factorys) {
                pstmt = conn.prepareStatement("UPDATE Factory SET FactoryName=?, Owners=?, Cash=?, Members=?, Workers=?, Info=?, "
                        + "TeleX=?, TeleY=?, TeleZ=?, RotX=?, RotY=?, RotZ=?, RotW=?, ChestUID=?, PriceList=?, Plots=? WHERE ID=" + f.getID());
                pstmt.setString(1, f.getName());
                pstmt.setBytes(2, DatabaseFormat.toBlob(f.getOwners()));
                pstmt.setLong(3, f.getCash());
                pstmt.setBytes(4, DatabaseFormat.toBlob(f.getMembers()));
                pstmt.setBytes(5, DatabaseFormat.toBlob(f.getWorkers()));
                pstmt.setBytes(6, DatabaseFormat.toBlob(f.getInfos()));
                pstmt.setFloat(7, f.getTelepoint().x);
                pstmt.setFloat(8, f.getTelepoint().y);
                pstmt.setFloat(9, f.getTelepoint().z);
                pstmt.setFloat(10, f.getTelepointRotation().x);
                pstmt.setFloat(11, f.getTelepointRotation().y);
                pstmt.setFloat(12, f.getTelepointRotation().z);
                pstmt.setFloat(13, f.getTelepointRotation().w);
                pstmt.setLong(14, f.getChestUID());
                pstmt.setBytes(15, DatabaseFormat.toBlob(f.getPriceList()));
                pstmt.setBytes(16, DatabaseFormat.toBlob(f.getPlotsID()));
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
        
        public TabelPlots(iConomy plugin, icConsole console, Database db){
            this.Database = db;
            conn = db.getConnection();
            DatabaseFormat = new DatabaseFormat();
        }
        
        public int add(FactoryPlot fa) throws SQLException {
            int id = -1;

            pstmt = conn.prepareStatement("INSERT INTO Plots (Area) VALUES (?)");
            pstmt.setLong(1, fa.getArea().getID());
            pstmt.setString(2, fa.getName());
            pstmt.setLong(3, fa.getPrice());
            if (fa.getFactory() != null) {
                pstmt.setInt(4, fa.getFactory().getID());
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
