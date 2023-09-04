package de.sbg.unity.iconomy.Listeners;

import de.sbg.unity.iconomy.Banksystem.PlayerAccount;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.io.IOException;
import java.sql.SQLException;
import net.risingworld.api.Server;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.general.InputEvent;

public class icInputListener implements Listener {

    private final iConomy plugin;
    private final icConsole Console;

    public icInputListener(iConomy plugin, icConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
    }

    @EventMethod
    public void onInputEvent(InputEvent event) {
        String in = event.getInput();
        String[] cmd = in.split(" ");

        if (cmd[0].toLowerCase().equals("ic") || cmd[0].toLowerCase().equals("$") || cmd[0].toLowerCase().equals("money") || cmd[0].toLowerCase().equals("iconomy")) {
            if (cmd.length == 1) {
                Console.sendInfo("Command", "Pluginname: " + plugin.getDescription("name"));
                Console.sendInfo("Command", "Version: " + plugin.getDescription("version"));
            }
            if (cmd.length == 2) {
                if (cmd[1].toLowerCase().equals("config")) {
                    Console.sendInfo("Command", "=========== iConomy-Config ===========");
                    Console.sendInfo("Command", "                    Debug=" + plugin.Config.Debug);
                    Console.sendInfo("Command", "                 Currency=" + plugin.Config.Currency);
                    Console.sendInfo("Command", "              MoneyFormat=" + plugin.Config.MoneyFormat);
                    Console.sendInfo("Command", "   Command_Bank_OnlyAdmin=" + plugin.Config.Command_Bank_OnlyAdmin);
                    Console.sendInfo("Command", "  CreateAccountViaCommand=" + plugin.Config.CreateAccountViaCommand);
                    Console.sendInfo("Command", "  FactoryBankStartAmounth=" + plugin.Config.FactoryBankStartAmounth);
                    Console.sendInfo("Command", "  FactoryCashStartAmounth=" + plugin.Config.FactoryCashStartAmounth);
                    Console.sendInfo("Command", "           KillerGetMoney=" + plugin.Config.KillerGetMoney);
                    Console.sendInfo("Command", "         LostMoneyByDeath=" + plugin.Config.LostMoneyByDeath);
                    Console.sendInfo("Command", "            MoneyInfoTime=" + plugin.Config.MoneyInfoTime);
                    Console.sendInfo("Command", "    PlayerBankAccountCost=" + plugin.Config.PlayerBankAccountCost);
                    Console.sendInfo("Command", "   PlayerBankStartAmounth=" + plugin.Config.PlayerBankStartAmounth);
                    Console.sendInfo("Command", "   PlayerCashStartAmounth=" + plugin.Config.PlayerCashStartAmounth);
                    Console.sendInfo("Command", "SaveAllByPlayerDisconnect=" + plugin.Config.SaveAllByPlayerDisconnect);
                    Console.sendInfo("Command", "                SaveTimer=" + plugin.Config.SaveTimer);
                    Console.sendInfo("Command", "             SuitcaseTime=" + plugin.Config.SuitcaseTime);
                    Console.sendInfo("Command", "        ShowBalaceAtStart=" + plugin.Config.ShowBalaceAtStart);
                    Console.sendInfo("Command", "======================================");
                }
                if (cmd[1].toLowerCase().equals("save")) {
                    try {
                        Console.sendInfo("Command", "Save all....");
                        plugin.Databases.saveAll();
                        Console.sendInfo("Command", "Done!");
                    } catch (IOException ex) {
                        Console.sendErr("DB-IO", "========= iConomy-Exception =========");
                        Console.sendErr("DB-IO", "Can not save all to Database!");
                        Console.sendErr("DB-IO", "Ex-Msg: " + ex.getMessage());
                        for (StackTraceElement st : ex.getStackTrace()) {
                            Console.sendErr("DB-IO", st.toString());
                        }
                        Console.sendErr("DB-IO", "=====================================");
                        plugin.StopPluginByDB = true;
                        Console.sendErr("SERVER", "STOP SERVER!");
                        Server.shutdown();
                    } catch (SQLException ex) {
                        Console.sendErr("DB-SQL", "========= iConomy-Exception =========");
                        Console.sendErr("DB-SQL", "Can not save all to Database!");
                        Console.sendErr("DB-SQL", "Ex-Msg: " + ex.getMessage());
                        Console.sendErr("DB-SQL", "Ex-SQLState: " + ex.getSQLState());
                        for (StackTraceElement st : ex.getStackTrace()) {
                            Console.sendErr("DB-SQL", st.toString());
                        }
                        Console.sendErr("DB-SQL", "=====================================");
                        plugin.StopPluginByDB = true;
                        Console.sendErr("SERVER", "STOP SERVER!");
                        Server.shutdown();
                    }
                }
            }

            if (cmd.length == 3) {
                if (cmd[1].toLowerCase().equals("config")) {
                    if (cmd[2].toLowerCase().equals("reload")) {
                        Console.sendInfo("Command", "Reload Config...");
                        try {
                            plugin.Config.iniConfig();
                            Console.sendInfo("Command", "Done!");
                        } catch (IOException ex) {
                            Console.sendErr("Config", "Can not reload config!");
                        }
                    }
                }
                if (cmd[1].toLowerCase().equals("bank")) {
                    if (cmd[2].toLowerCase().equals("accounts")) {
                        Console.sendInfo("Command", "=========== Bank-Accounts ===========");
                        for (PlayerAccount pa : plugin.Bankystem.PlayerSystem.getPlayerAccounts().values()) {
                            Console.sendInfo("Command", "- " + pa.getOwnerUID() + " (Last Name: " + pa.getLastKnownOwnerName() + ")");
                        }
                        Console.sendInfo("Command", "=====================================");
                    }
                }
            }
        }

        //TODO Input
    }

}
