package de.sbg.unity.iconomy.Listeners;

import de.sbg.unity.iconomy.Events.Money.AddCashEvent;
import de.sbg.unity.iconomy.Events.Money.RemoveCashEvent;
import de.sbg.unity.iconomy.Exeptions.CashFormatExeption;
import de.sbg.unity.iconomy.Utils.MoneyFormate;
import de.sbg.unity.iconomy.Utils.TextFormat;
import de.sbg.unity.iconomy.Utils.TransferResult;
import static de.sbg.unity.iconomy.Utils.TransferResult.EventCancel;
import static de.sbg.unity.iconomy.Utils.TransferResult.NotAnouthMoney;
import static de.sbg.unity.iconomy.Utils.TransferResult.PlayerNotConnected;
import static de.sbg.unity.iconomy.Utils.TransferResult.Successful;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.Server;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerCommandEvent;
import net.risingworld.api.objects.Player;

public class AdminMoneyCommandListener implements Listener {

    private final iConomy plugin;
    private final icConsole Console;
    private final MoneyFormate mFormat;
    private final TextFormat format;

    public AdminMoneyCommandListener(iConomy plugin, icConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        this.mFormat = new MoneyFormate(plugin);
        this.format = new TextFormat();
    }

    @EventMethod
    public void onPlayerCommandEvent(PlayerCommandEvent event) {
        Player player = event.getPlayer();

        if (player.isAdmin()) {

            String[] cmd = event.getCommand().split(" ");

            if (cmd[0].toLowerCase().equals("/money") || cmd[0].toLowerCase().equals("/eco") || cmd[0].toLowerCase().equals("/$") || cmd[0].toLowerCase().equals("/ic") || cmd[0].toLowerCase().equals("/iconomy")) {

                if (cmd.length == 4) {
                    if (cmd[1].toLowerCase().equals("givecash") || cmd[1].toLowerCase().equals("gc")) {
                        Player p2 = Server.getPlayerByName(cmd[2]);
                        if (p2 != null && p2.isConnected()) {
                            try {
                                long l = mFormat.getMoneyAsLong(cmd[3]);
                                plugin.CashSystem.addCash(p2, l, AddCashEvent.Reason.Player);
                                player.sendTextMessage(format.Color("green", "Give money!"));
                            } catch (NumberFormatException ex) {
                                player.sendTextMessage(format.Color("red", "Money must be numers, ',' or '.'!"));
                            }
                        } else {
                            player.sendTextMessage(format.Color("red", "Player is not connected!"));
                        }
                    }
                    if (cmd[1].toLowerCase().equals("takecash") || cmd[1].toLowerCase().equals("tc")) {
                        Player p2 = Server.getPlayerByName(cmd[2]);
                        if (p2 != null && p2.isConnected()) {
                            try {
                                long l = mFormat.getMoneyAsLong(cmd[3]);
                                if (plugin.CashSystem.getCash(p2) - l < 0) {
                                    player.sendTextMessage(format.Color("red", "Player has not anouth money!"));
                                } else {
                                    plugin.CashSystem.removeCash(p2, l, RemoveCashEvent.Reason.Player);
                                    player.sendTextMessage(format.Color("green", "Take money!"));
                                }
                            } catch (NumberFormatException ex) {
                                player.sendTextMessage(format.Color("red", "Money must be numers, ',' or '.'!"));
                            }
                        } else {
                            player.sendTextMessage(format.Color("red", "Player is not connected!"));
                        }
                    }
                    if (cmd[1].toLowerCase().equals("setcash") || cmd[1].toLowerCase().equals("sc")) {
                        Player p2 = Server.getPlayerByName(cmd[2]);
                        if (p2 != null && p2.isConnected()) {
                            try {
                                long l = mFormat.getMoneyAsLong(cmd[3]);
                                if (plugin.CashSystem.getCash(p2) - l < 0) {
                                    player.sendTextMessage(format.Color("red", "Player has not anouth money!"));
                                } else {
                                    plugin.CashSystem.setCash(p2, l);
                                    player.sendTextMessage(format.Color("green", "Set money!"));
                                }
                            } catch (NumberFormatException ex) {
                                player.sendTextMessage(format.Color("red", "Money must be numers, ',' or '.'!"));
                            } catch (CashFormatExeption ex) {
                                player.sendTextMessage(format.Color("red", "The amounth must be bigger then 0!"));
                            }
                        } else {
                            player.sendTextMessage(format.Color("red", "Player is not connected!"));
                        }
                    }
                    if (cmd[1].toLowerCase().equals("givebank") || cmd[1].toLowerCase().equals("gb")) {
                        Player p2 = Server.getPlayerByName(cmd[2]);
                        if (p2 != null && p2.isConnected()) {
                            try {
                                long l = mFormat.getMoneyAsLong(cmd[3]);
                                if (plugin.Bankystem.PlayerSystem.hasPlayerAccount(p2)) {
                                    switch (plugin.Bankystem.PlayerSystem.getPlayerAccount(p2).addMoney(player, l)) {
                                        case Successful ->
                                            player.sendTextMessage(format.Color("green", "Give money to bank successfuly!"));
                                        case EventCancel ->
                                            player.sendTextMessage(format.Color("red", "Can not change the bank!"));
                                        case PlayerNotConnected ->
                                            player.sendTextMessage(format.Color("red", "Player not exist!"));
                                        default ->
                                            player.sendTextMessage(format.Color("red", "Player not exist!"));
                                    }
                                } else {
                                    player.sendTextMessage(format.Color("red", "Player has not a bank account!"));
                                }
                            } catch (NumberFormatException ex) {
                                player.sendTextMessage(format.Color("red", "Money must be numers, ',' or '.'!"));
                            }
                        }
                    }
                    if (cmd[1].toLowerCase().equals("takebank") || cmd[1].toLowerCase().equals("tb")) {
                        Player p2 = Server.getPlayerByName(cmd[2]);
                        if (p2 != null && p2.isConnected()) {
                            try {
                                long l = mFormat.getMoneyAsLong(cmd[3]);

                                if (plugin.Bankystem.PlayerSystem.hasPlayerAccount(p2)) {
                                    switch (plugin.Bankystem.PlayerSystem.getPlayerAccount(p2).removeMoney(player, 0)) {
                                        case Successful ->
                                            player.sendTextMessage(format.Color("green", "Take money from bank successfuly!"));
                                        case EventCancel ->
                                            player.sendTextMessage(format.Color("red", "Can not change the bank!"));
                                        case NotAnouthMoney ->
                                            player.sendTextMessage(format.Color("red", "Player has not anouth money!"));
                                        case PlayerNotConnected ->
                                            player.sendTextMessage(format.Color("red", "Player not exist!"));
                                        default ->
                                            player.sendTextMessage(format.Color("red", "Player not exist!"));
                                    }
                                } else {
                                    player.sendTextMessage(format.Color("red", "Player has not a bank account!"));
                                }
                            } catch (NumberFormatException ex) {
                                player.sendTextMessage(format.Color("red", "Money must be numers, ',' or '.'!"));
                            }
                        }

                    }
                    if (cmd[1].toLowerCase().equals("setbank") || cmd[1].toLowerCase().equals("sb")) {
                        Player p2 = Server.getPlayerByName(cmd[2]);
                        if (p2 != null && p2.isConnected()) {
                            try {
                                long l = mFormat.getMoneyAsLong(cmd[3]);
                                if (plugin.Bankystem.PlayerSystem.hasPlayerAccount(p2)) {
                                    plugin.Bankystem.PlayerSystem.getPlayerAccount(p2).setMoney(0);
                                    player.sendTextMessage(format.Color("green", "Change money of the bank successfuly!"));

                                } else {
                                    player.sendTextMessage(format.Color("red", "Player has not a bank account!"));
                                }
                            } catch (NumberFormatException ex) {
                                player.sendTextMessage(format.Color("red", "Money must be numers, ',' or '.'!"));
                            }
                        }
                    }
                }
            }
        }
    }

}
