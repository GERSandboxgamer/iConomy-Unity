package de.sbg.unity.iconomy.Listeners;

import de.sbg.unity.iconomy.Events.Money.AddBankMoneyEvent;
import de.sbg.unity.iconomy.Events.Money.AddCashEvent;
import de.sbg.unity.iconomy.Events.Money.RemoveBankMoneyEvent;
import de.sbg.unity.iconomy.Events.Money.RemoveCashEvent;
import de.sbg.unity.iconomy.Exeptions.CashFormatExeption;
import de.sbg.unity.iconomy.Utils.MoneyFormate;
import de.sbg.unity.iconomy.Utils.TextFormat;
import de.sbg.unity.iconomy.Utils.TransferResult;
import static de.sbg.unity.iconomy.Utils.TransferResult.EventCancel;
import static de.sbg.unity.iconomy.Utils.TransferResult.PlayerNotConnected;
import static de.sbg.unity.iconomy.Utils.TransferResult.PlayerNotExist;
import static de.sbg.unity.iconomy.Utils.TransferResult.Successful;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.io.IOException;
import java.sql.SQLException;
import net.risingworld.api.Server;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerCommandEvent;
import net.risingworld.api.events.player.PlayerDisconnectEvent;
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
        String lang = player.getLanguage();

        if (player.isAdmin()) {

            String[] cmd = event.getCommand().split(" ");

            if (cmd[0].toLowerCase().equals("/money") || cmd[0].toLowerCase().equals("/eco") || cmd[0].toLowerCase().equals("/$") || cmd[0].toLowerCase().equals("/ic") || cmd[0].toLowerCase().equals("/iconomy")) {
                if (cmd.length == 2) {
                    if (cmd[1].toLowerCase().equals("info")) {
                        player.sendTextMessage(format.Color("orange", "========== iConomy-Info =========="));
                        player.sendTextMessage(format.Color("orange", "Name: " + plugin.getDescription("name")));
                        player.sendTextMessage(format.Color("orange", "Version: " + plugin.getDescription("version")));
                        player.sendTextMessage(format.Color("orange", "Update: " + plugin.hasUpdate()));
                        player.sendTextMessage(format.Color("orange", "Debug: " + plugin.Config.Debug));
                        player.sendTextMessage(format.Color("orange", "Currency: " + plugin.Config.Currency));
                        player.sendTextMessage(format.Color("orange", "MoneyFormat: " + plugin.Config.MoneyFormat));
                        player.sendTextMessage(format.Color("orange", "MoneyInfoTime: " + plugin.Config.MoneyInfoTime));
                        player.sendTextMessage(format.Color("orange", "PlayerBankAccountCost: " + plugin.Config.PlayerBankAccountCost));
                        player.sendTextMessage(format.Color("orange", "PlayerBankStartAmounth: " + plugin.Config.PlayerBankStartAmounth));
                        player.sendTextMessage(format.Color("orange", "PlayerCashStartAmounth: " + plugin.Config.PlayerCashStartAmounth));
                        player.sendTextMessage(format.Color("orange", "SaveTimer: " + plugin.Config.SaveTimer));
                        player.sendTextMessage(format.Color("orange", "ShowBalaceAtStart: " + plugin.Config.ShowBalaceAtStart));
                        player.sendTextMessage(format.Color("orange", "=================================="));
                    }

                    if (cmd[1].toLowerCase().equals("save")) {
                        try {
                            player.sendTextMessage(format.Color("orange", "Save all...."));
                            plugin.Databases.saveAll();
                            player.sendTextMessage(format.Color("green", "Done!"));
                        } catch (IOException ex) {
                            player.sendTextMessage(format.Color("red", "IOException"));
                        } catch (SQLException sql) {
                            player.sendTextMessage(format.Color("red", "SQLException"));
                        }
                    }

                }
                if (cmd.length == 3) {
                    if (cmd[1].toLowerCase().equals("test")) {
                        if (cmd[2].toLowerCase().equals("suitcase")) {
                            long c = plugin.CashSystem.getCash(player);
                            if (c > 0) {
                                plugin.GameObject.suitcase.spawn(player, c, player.getPosition());
                                plugin.CashSystem.removeCash(player, c, RemoveCashEvent.Reason.Lost);
                                player.sendTextMessage(format.Color("orange", plugin.Language.getStatus().getLostMoney(lang)));
                            }
                        }
                    }
                }
                if (cmd.length == 4) {
                    if (cmd[1].toLowerCase().equals("givecash") || cmd[1].toLowerCase().equals("gc")) {
                        Player p2 = Server.getPlayerByName(cmd[2]);
                        try {
                            long l = mFormat.getMoneyAsLong(cmd[3]);
                            TransferResult tr = plugin.CashSystem.addCash(p2, l, AddCashEvent.Reason.Player);
                            player.sendTextMessage(format.Color("green", plugin.Language.getCommand().getAdminGivecash(lang)));
                        } catch (NumberFormatException ex) {
                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getMoneyMustBeNumber(lang)));
                        }
                    }
                    if (cmd[1].toLowerCase().equals("takecash") || cmd[1].toLowerCase().equals("tc")) {
                        Player p2 = Server.getPlayerByName(cmd[2]);
                        try {
                            long l = mFormat.getMoneyAsLong(cmd[3]);
                            if (plugin.CashSystem.getCash(p2) - l < 0) {
                                player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getOtherPlayerNotAnounthMoney(lang)));
                            } else {
                                switch (plugin.CashSystem.removeCash(p2, l, RemoveCashEvent.Reason.Player)) {
                                    case EventCancel -> {
                                        player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getTransferCancel(lang)));
                                    }
                                    case PlayerNotConnected -> {
                                        player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotConnected(lang)));
                                    }
                                    case PlayerNotExist -> {
                                        player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotExist(lang)));
                                    }
                                    case Successful -> {
                                        player.sendTextMessage(format.Color("green", plugin.Language.getCommand().getAdminTakecash(lang)));
                                    }
                                }
                            }
                        } catch (NumberFormatException ex) {
                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getMoneyMustBeNumber(lang)));
                        }
                    }
                    if (cmd[1].toLowerCase().equals("setcash") || cmd[1].toLowerCase().equals("sc")) {
                        Player p2 = Server.getPlayerByName(cmd[2]);
                        try {
                            long l = mFormat.getMoneyAsLong(cmd[3]);
                            TransferResult tr = plugin.CashSystem.setCash(p2, l);
                            switch (tr) {
                                case EventCancel -> {
                                    player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getTransferCancel(lang)));
                                }
                                case PlayerNotConnected -> {
                                    player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotConnected(lang)));
                                }
                                case PlayerNotExist -> {
                                    player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotExist(lang)));
                                }
                                case Successful -> {
                                    player.sendTextMessage(format.Color("green", plugin.Language.getCommand().getAdminSetcash(lang)));
                                }
                            }

                        } catch (NumberFormatException ex) {
                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getMoneyMustBeNumber(lang)));
                        } catch (CashFormatExeption ex) {
                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getAmounthBigger(lang))); 
                        }

                    }
                    if (cmd[1].toLowerCase().equals("givebank") || cmd[1].toLowerCase().equals("gb")) {
                        Player p2 = Server.getPlayerByName(cmd[2]);
                        try {
                            long l = mFormat.getMoneyAsLong(cmd[3]);
                            if (plugin.Bankystem.PlayerSystem.hasPlayerAccount(p2)) {
                                switch (plugin.Bankystem.PlayerSystem.getPlayerAccount(p2).addMoney(player, l, AddBankMoneyEvent.Reason.Command)) {
                                    case Successful ->
                                        player.sendTextMessage(format.Color("green", plugin.Language.getCommand().getAdminGivebank(lang)));
                                    case EventCancel ->
                                        player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getTransferCancel(lang)));
                                    case PlayerNotConnected ->
                                        player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotConnected(lang)));
                                    default ->
                                        player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotExist(lang)));
                                }
                            } else {
                                player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getOtherPlayerHasNoAccount(lang)));
                            }
                        } catch (NumberFormatException ex) {
                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getMoneyMustBeNumber(lang)));
                        }

                    }
                    if (cmd[1].toLowerCase().equals("takebank") || cmd[1].toLowerCase().equals("tb")) {
                        Player p2 = Server.getPlayerByName(cmd[2]);
                        try {
                            long l = mFormat.getMoneyAsLong(cmd[3]);

                            if (plugin.Bankystem.PlayerSystem.hasPlayerAccount(p2)) {
                                switch (plugin.Bankystem.PlayerSystem.getPlayerAccount(p2).removeMoney(player, l, RemoveBankMoneyEvent.Reason.Command)) {
                                    case Successful ->
                                        player.sendTextMessage(format.Color("green", plugin.Language.getCommand().getAdminTakebank(lang)));
                                    case EventCancel ->
                                        player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getTransferCancel(lang)));
                                    case NotEnoughMoney ->
                                        player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotAnounthMoney(lang)));
                                    case PlayerNotConnected ->
                                        player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotConnected(lang)));
                                    default ->
                                        player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotExist(lang)));
                                }
                            } else {
                                player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getOtherPlayerHasNoAccount(lang)));
                            }
                        } catch (NumberFormatException ex) {
                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getMoneyMustBeNumber(lang)));
                        }
                    }
                    if (cmd[1].toLowerCase().equals("setbank") || cmd[1].toLowerCase().equals("sb")) {
                        Player p2 = Server.getPlayerByName(cmd[2]);
                        try {
                            long l = mFormat.getMoneyAsLong(cmd[3]);
                            if (plugin.Bankystem.PlayerSystem.hasPlayerAccount(p2)) {
                                plugin.Bankystem.PlayerSystem.getPlayerAccount(p2).setMoney(0);
                                player.sendTextMessage(format.Color("green", plugin.Language.getCommand().getAdminSetbank(lang)));

                            } else {
                                player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getOtherPlayerHasNoAccount(lang)));
                            }
                        } catch (NumberFormatException ex) {
                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getMoneyMustBeNumber(lang)));
                        }
                    }
                }
            }
        }
    }

}
