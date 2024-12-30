package de.sbg.unity.iconomy.Database;

import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.io.IOException;
import java.sql.SQLException;
import net.risingworld.api.Timer;

public class icDatabases {

    public final MoneyDatabase Money;
    public final FactoryDatabase Factory;
    private Timer saveTimer;
    private final iConomy plugin;
    private final icConsole Console;
    private boolean saveAtm;

    public icDatabases(iConomy plugin, icConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        Money = new MoneyDatabase(plugin, Console);
        Factory = new FactoryDatabase(plugin, Console);
        saveAtm = false;
    }
    
    public void saveAtm(){
        saveAtm = true;
    }

    public void startSaveTimer() {
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("icDatabases", "startSaveTimer");
        }
        Timer sT = new Timer(plugin.Config.SaveTimer * 60, -1, -1, () -> {
            try {
                if (plugin.Config.Debug > 0) {
                    Console.sendDebug("icDatabases", "SaveTimer: SaveAll...");
                }
                saveAll();
                if (plugin.Config.Debug > 0) {
                    Console.sendDebug("icDatabases", "SaveTimer: SaveAll...Done!");
                }
                
            } catch (SQLException ex) {
                Console.sendErr("DB-SQL", "========= iConomy-Exception =========");
                Console.sendErr("DB-SQL", "Can not save all to Database!");
                Console.sendErr("DB-SQL", "Ex-Msg: " + ex.getMessage());
                Console.sendErr("DB-SQL", "Ex-SQLState: " + ex.getSQLState());
                for (StackTraceElement st : ex.getStackTrace()) {
                    Console.sendErr("DB-SQL", st.toString());
                }
                //plugin.StopPluginByDB = true;
                Console.sendErr("SERVER", "STOP SERVER!");
                Console.sendErr("DB-SQL", "=====================================");
                //Server.shutdown();
            } catch (IOException ex) {
                Console.sendErr("DB-IO", "========= iConomy-Exception =========");
                Console.sendErr("DB-IO", "Can not save all to Database!");
                Console.sendErr("DB-IO", "Ex-Msg: " + ex.getMessage());
                Console.sendErr("DB-IO", "Localized Message: " + ex.getLocalizedMessage());
                Console.sendErr("DB-IO", "toString(): " + ex.toString());
                for (StackTraceElement st : ex.getStackTrace()) {
                    Console.sendErr("DB-IO", st.toString());
                }
                //plugin.StopPluginByDB = true;
                Console.sendErr("SERVER", "STOP SERVER!");
                Console.sendErr("DB-IO", "=====================================");
                //Server.shutdown();
            }
        });
        saveTimer = sT;
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("icDatabases", "saveTimer = " + sT);
        }
        sT.start();
    }

    public void saveAll() throws SQLException, IOException {
        Money.Cash.saveAllToDatabase(plugin.CashSystem.getCashList());
        Money.Bank.saveAllToDatabase(plugin.Bankystem.PlayerSystem.getPlayerAccounts());
        
        if (saveAtm) {
            Console.sendInfo("SaveAtm", "Save now all ATMs...");
            Money.ATM.saveAllToDatabase(plugin.GameObject.atm.getAtmList());
            saveAtm = false;
        }
        
        Factory.TabBank.saveAllToDatabase(plugin.Bankystem.FactoryBankSystem.getFactoryAccounts());
        Factory.TabFactory.saveAllToDatabase(plugin.Factory.getFactorys()); 
    } 

    public void stopSaveTimer() {
        if (isSaveTimerRunning()) {
            saveTimer.kill();
        }
    }

    public boolean isSaveTimerRunning() {
        return saveTimer != null || saveTimer.isActive();
    }

}
