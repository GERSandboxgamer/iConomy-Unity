package de.sbg.unity.iconomy.Listeners;

import de.sbg.unity.iconomy.Banksystem.BankMember;
import de.sbg.unity.iconomy.Banksystem.PlayerAccount;
import de.sbg.unity.iconomy.Utils.TransferResult;
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
                    Console.sendInfo("Command", "  BusinessBankStartAmount=" + plugin.Config.BusinessBankStartAmount);
                    Console.sendInfo("Command", "  BusinessCashStartAmount=" + plugin.Config.BusinessCashStartAmount);
                    Console.sendInfo("Command", "           KillerGetMoney=" + plugin.Config.KillerGetMoney);
                    Console.sendInfo("Command", "         LostMoneyByDeath=" + plugin.Config.LostMoneyByDeath);
                    Console.sendInfo("Command", "            MoneyInfoTime=" + plugin.Config.MoneyInfoTime);
                    Console.sendInfo("Command", "    PlayerBankAccountCost=" + plugin.Config.PlayerBankAccountCost);
                    Console.sendInfo("Command", "   PlayerBankStartAmount=" + plugin.Config.PlayerBankStartAmount);
                    Console.sendInfo("Command", "   PlayerCashStartAmount=" + plugin.Config.PlayerCashStartAmount);
                    Console.sendInfo("Command", "SaveAllByPlayerDisconnect=" + plugin.Config.SaveAllByPlayerDisconnect);
                    Console.sendInfo("Command", "                SaveTimer=" + plugin.Config.SaveTimer);
                    Console.sendInfo("Command", "             SuitcaseTime=" + plugin.Config.SuitcaseTime);
                    Console.sendInfo("Command", "        ShowBalaceAtStart=" + plugin.Config.ShowBalanceAtStart);
                    Console.sendInfo("Command", "======================================");
                }
                if (cmd[1].toLowerCase().equals("help")) {

                    Console.sendInfo("Command", "=========== iConomy-Help ===========");
                    Console.sendInfo("Command", "INFO: () = Optional; <> = Must");
                    Console.sendInfo("Command", cmd[0] + " config (reload)");
                    Console.sendInfo("Command", cmd[0] + " help");
                    Console.sendInfo("Command", cmd[0] + " dbinfo");
                    Console.sendInfo("Command", cmd[0] + " save");
                    Console.sendInfo("Command", cmd[0] + " bundle");
                    Console.sendInfo("Command", cmd[0] + " bank (PLAYER NAME)");
                    Console.sendInfo("Command", cmd[0] + " cash (PLAYER NAME)");
                    Console.sendInfo("Command", cmd[0] + " <setbank|sb> <PLAYER NAME> <MONEY>");
                    Console.sendInfo("Command", cmd[0] + " <addbank|ab> <PLAYER NAME> <AMOUNTH>");
                    Console.sendInfo("Command", cmd[0] + " <removebank|rb> <PLAYER NAME> <AMOUNTH>");
                    Console.sendInfo("Command", "====================================");
                }
                if (cmd[1].toLowerCase().equals("dbinfo")) {
                    Console.sendInfo("Command", "=========== iConomy DB-Info ===========");
                    Console.sendInfo("Command", "    Saved ATMS: " + plugin.Databases.Money.getSavedAtm());
                    Console.sendInfo("Command", " Saved Players: " + plugin.Databases.Money.getSavedPlayers());
                    Console.sendInfo("Command", "Saved Accounts: " + plugin.Databases.Money.getSavedAccounts());
                    Console.sendInfo("Command", "=======================================");
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
                        Console.sendErr("DB-IO", "Localized Message: " + ex.getLocalizedMessage());
                        Console.sendErr("DB-IO", "toString(): " + ex.toString());
                        for (StackTraceElement st : ex.getStackTrace()) {
                            Console.sendErr("DB-IO", st.toString());
                        }
                        plugin.StopPluginByDB = true;
                        Console.sendErr("SERVER", "STOP SERVER!");
                        Console.sendErr("DB-IO", "=====================================");
                        //Server.shutdown();
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
                if (cmd[1].toLowerCase().equals("bundle")) {
                    Console.sendInfo("Command", "=========== Bundles ===========");
                    if (!plugin.GameObject.getListBundle().isEmpty()) {
                        for (String s : plugin.GameObject.getListBundle().keySet()) {
                            Console.sendInfo("Command", "- " + s + "(Path: " + plugin.GameObject.getListBundle().get(s).getBundlePath() + "/" + s + "/" + plugin.GameObject.getListBundle().get(s).getPrefabAsset().getPath() + ")");
                        }
                    } else {
                        Console.sendInfo("Command", "List is empty!");
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
                    if (plugin.Bankystem.PlayerSystem.getPlayerAccount(cmd[2]) == null) {
                        if (cmd[2].toLowerCase().equals("accounts")) {
                            Console.sendInfo("Command", "=========== Bank-Accounts ===========");
                            for (PlayerAccount pa : plugin.Bankystem.PlayerSystem.getPlayerAccounts().values()) {
                                Console.sendInfo("Command", "- " + pa.getOwnerUID() + " (Last Name: " + pa.getLastKnownOwnerName() + ")");
                            }
                            Console.sendInfo("Command", "=====================================");
                        }
                    } else {
                        PlayerAccount pa = plugin.Bankystem.PlayerSystem.getPlayerAccount(cmd[2]);
                        Console.sendInfo("Command", "============= Bank =============");
                        Console.sendInfo("Command", "Player: " + pa.getLastKnownOwnerName());
                        Console.sendInfo("Command", " Money: " + pa.getMoneyAsFormatedString());
                        Console.sendInfo("Command", "============= Bank =============");
                    }
                }
            }

            if (cmd[1].toLowerCase().equals("cash")) {
                if (cmd.length == 2) {
                    Console.sendInfo("Command", "============= Cash =============");
                    String money, playername;
                    for (String uid : plugin.CashSystem.getCashList().keySet()) {
                        money = plugin.CashSystem.getCashAsFormatedString(uid);
                        playername = Server.getLastKnownPlayerName(uid);
                        Console.sendInfo("Command", "Player: " + playername + " | Money: " + money);
                    }
                    Console.sendInfo("Command", "============= Cash =============");
                }
                if (cmd.length == 3) {
                    if (!plugin.CashSystem.getPlayerNames().contains(cmd[2])) {
                        Console.sendErr("Command", "Player not found!");
                    } else {
                        Console.sendInfo("Command", "============= Cash =============");
                        Console.sendInfo("Command", "Player: " + cmd[2]);
                        Console.sendInfo("Command", " Money: " + plugin.CashSystem.getCashAsFormatedString(cmd[2]));
                        Console.sendInfo("Command", "============= Cash =============");
                    }
                }

            }

            if (cmd.length == 4) {
                if (cmd[1].toLowerCase().equals("setbank") || cmd[1].toLowerCase().equals("sb")) {
                    String oldMoney, newMoney;
                    PlayerAccount pa = plugin.Bankystem.PlayerSystem.getPlayerAccount(cmd[2]);
                    if (pa != null) {
                        oldMoney = pa.getMoneyAsFormatedString();
                        pa.setMoney(cmd[3]);
                        newMoney = pa.getMoneyAsFormatedString();
                        if (plugin.Config.Debug > 0) {
                            Console.sendDebug("SetCash", "Account-Name: " + pa.getLastKnownOwnerName());
                            Console.sendDebug("SetCash", "   Money old: " + oldMoney);
                            Console.sendDebug("SetCash", "   Money now: " + newMoney);
                        }
                    } else {
                        Console.sendErr("Command", "Account not found!");
                    }
                }
                if (cmd[1].toLowerCase().equals("addbank") || cmd[1].toLowerCase().equals("ab")) {
                    TransferResult tr;
                    String oldMoney, newMoney;
                    PlayerAccount pa = plugin.Bankystem.PlayerSystem.getPlayerAccount(cmd[2]);
                    if (pa != null) {
                        oldMoney = pa.getMoneyAsFormatedString();
                        tr = pa.addMoney(plugin.moneyFormat.getMoneyAsLong(cmd[3]), "Server command", "SERVER-COMMAND");
                        newMoney = pa.getMoneyAsFormatedString();
                        if (plugin.Config.Debug > 0) {
                            Console.sendDebug("SetCash", "  Account-Name: " + pa.getLastKnownOwnerName());
                            Console.sendDebug("SetCash", "TransferResult: " + tr);
                            Console.sendDebug("SetCash", "     Money old: " + oldMoney);
                            Console.sendDebug("SetCash", "     Money now: " + newMoney);
                        }
                    } else {
                        Console.sendErr("Command", "Account not found!");
                    }
                }
                if (cmd[1].toLowerCase().equals("removebank") || cmd[1].toLowerCase().equals("rb")) {
                    TransferResult tr;
                    String oldMoney, newMoney;
                    PlayerAccount pa = plugin.Bankystem.PlayerSystem.getPlayerAccount(cmd[2]);
                    if (pa != null) {
                        oldMoney = pa.getMoneyAsFormatedString();
                        tr = pa.removeMoney(plugin.moneyFormat.getMoneyAsLong(cmd[3]), "Server command", "SERVER-COMMAND");
                        newMoney = pa.getMoneyAsFormatedString();
                        if (plugin.Config.Debug > 0) {
                            Console.sendDebug("SetCash", "  Account-Name: " + pa.getLastKnownOwnerName());
                            Console.sendDebug("SetCash", "TransferResult: " + tr);
                            Console.sendDebug("SetCash", "     Money old: " + oldMoney);
                            Console.sendDebug("SetCash", "     Money now: " + newMoney);
                        }
                    } else {
                        Console.sendErr("Command", "Account not found!");
                    }
                }

                if (cmd[1].toLowerCase().equals("bank")) {
                    if (cmd[2].toLowerCase().equals("member")) {
                        for (BankMember bm : plugin.Bankystem.PlayerSystem.getPlayerAccount(cmd[3]).getMembers()) {
                            Console.sendInfo("Command", bm.getLastKnownMemberName());
                        }
                    }
                }
            }

        }

    }

}
