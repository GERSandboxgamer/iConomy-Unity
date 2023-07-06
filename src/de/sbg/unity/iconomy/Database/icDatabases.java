package de.sbg.unity.iconomy.Database;

import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.io.IOException;
import java.sql.SQLException;
import net.risingworld.api.Server;
import net.risingworld.api.Timer;

public class icDatabases {

    public final MoneyDatabase Money;
    public final FactoryDatabase Factory;
    private Timer saveTimer;
    private final iConomy plugin;
    private final icConsole Console;

    public icDatabases(iConomy plugin, icConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        Money = new MoneyDatabase(plugin, Console);
        Factory = new FactoryDatabase(plugin, Console);
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
                Console.sendErr("DB-SQL", "Can not save all to Database!");
                Console.sendErr("DB-SQL", "Ex-Msg: " + ex.getMessage());
                Console.sendErr("DB-SQL", "Ex-SQLState: " + ex.getSQLState());
                ex.printStackTrace();
                Console.sendErr("SERVER", "STOP SERVER!");
                Server.shutdown();
            } catch (IOException ex) {
                Console.sendErr("DB-IO", "Can not save all to Database!");
                Console.sendErr("DB-IO", "Ex-Msg: " + ex.getMessage());
                ex.printStackTrace();
                Console.sendErr("SERVER", "STOP SERVER!");
                Server.shutdown();
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
