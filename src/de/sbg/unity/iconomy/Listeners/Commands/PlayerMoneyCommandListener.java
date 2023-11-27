package de.sbg.unity.iconomy.Listeners.Commands;

import de.sbg.unity.iconomy.Events.Money.RemoveCashEvent;
import de.sbg.unity.iconomy.GUI.Banksystem.CashInOutGUI;
import de.sbg.unity.iconomy.Utils.TextFormat;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerCommandEvent;
import net.risingworld.api.objects.Player;

public class PlayerMoneyCommandListener implements Listener {

    private final iConomy plugin;
    private final icConsole Console;
    private final TextFormat format;

    public PlayerMoneyCommandListener(iConomy plugin, icConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        this.format = new TextFormat();
    }

    @EventMethod
    public void onPlayerCommandEvent(PlayerCommandEvent event) {
        String[] cmd = event.getCommand().split(" ");
        Player player = event.getPlayer();
        String lang = player.getLanguage();

        if (cmd[0].toLowerCase().equals("/balance")) {
            if (cmd.length == 1) {
                if (plugin.Bankystem.PlayerSystem.hasPlayerAccount(player)) {
                    plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + plugin.CashSystem.getCashAsFormatedString(player), "Bank: " + plugin.Bankystem.PlayerSystem.getPlayerAccount(player).getMoneyAsFormatedString());
                } else {
                    plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + plugin.CashSystem.getCashAsFormatedString(player), "Bank: " + plugin.Language.getGui().getNoAccount(lang));
                }
            }
            if (cmd.length == 2) {
                if (cmd[1].toLowerCase().equals("all")) {
                    if (plugin.Bankystem.PlayerSystem.hasPlayerAccount(player)) {
                        plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + plugin.CashSystem.getCashAsFormatedString(player), "Bank: " + plugin.Bankystem.PlayerSystem.getPlayerAccount(player).getMoneyAsFormatedString());
                    } else {
                        plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + plugin.CashSystem.getCashAsFormatedString(player), "Bank: " + plugin.Language.getGui().getNoAccount(lang));
                    }
                }
                if (cmd[1].toLowerCase().equals("cash")) {
                    plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + plugin.CashSystem.getCashAsFormatedString(player));
                }
                if (cmd[1].toLowerCase().equals("bank")) {
                    if (plugin.Bankystem.PlayerSystem.hasPlayerAccount(player)) {
                        plugin.GUI.MoneyInfoGui.showGUI(player, "Bank: " + plugin.Bankystem.PlayerSystem.getPlayerAccount(player).getMoneyAsFormatedString());
                    } else {
                        plugin.GUI.MoneyInfoGui.showGUI(player, "Bank: " + plugin.Language.getGui().getNoAccount(lang));
                    }
                }
            }
        }
        if (cmd[0].toLowerCase().equals("/money") || cmd[0].toLowerCase().equals("/eco") || cmd[0].toLowerCase().equals("/$") || cmd[0].toLowerCase().equals("/ic") || cmd[0].toLowerCase().equals("/iconomy")) {
            if (cmd.length == 1) {
                if (plugin.Config.Debug > 0) {
                    Console.sendDebug("Money-Info", "plugin: " + plugin);
                    Console.sendDebug("Money-Info", "plugin.CashSystem: " + plugin.CashSystem);
                    Console.sendDebug("Money-Info", "Cash: " + plugin.CashSystem.getCash(player));
                    Console.sendDebug("Money-Info", "Cash-Formatet: " + plugin.CashSystem.getCashAsFormatedString(player));
                }

                if (plugin.Bankystem.PlayerSystem.hasPlayerAccount(player)) {
                    plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + plugin.CashSystem.getCashAsFormatedString(player), "Bank: " + plugin.Bankystem.PlayerSystem.getPlayerAccount(player).getMoneyAsFormatedString());
                } else {
                    plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + plugin.CashSystem.getCashAsFormatedString(player), "Bank: " + plugin.Language.getGui().getNoAccount(lang));
                }
            }
            if (cmd.length >= 2) {
                if (cmd[1].toLowerCase().equals("createbank") || cmd[1].toLowerCase().equals("cb")) {
                    if (plugin.Config.CreateAccountViaCommand) {
                        if (cmd.length == 2) {
                            if (!plugin.Bankystem.PlayerSystem.hasPlayerAccount(player)) {
                                if (plugin.Config.PlayerBankAccountCost > 0) {
                                    switch (plugin.CashSystem.removeCash(player, plugin.Config.PlayerBankAccountCost, RemoveCashEvent.Reason.Buy)) {
                                        case Successful -> {
                                            plugin.Bankystem.PlayerSystem.addPlayerAccount(player);
                                            player.sendTextMessage(format.Color("green", plugin.Language.getCommand().getCreateBank(lang)));
                                        }
                                        case NotEnoughMoney -> {
                                            player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getPlayerNotAnounthMoney(lang)));
                                        }
                                    }
                                } else {
                                    plugin.Bankystem.PlayerSystem.addPlayerAccount(player);
                                    player.sendTextMessage(format.Color("green", plugin.Language.getCommand().getCreateBank(lang)));
                                }
                            } else {
                                player.sendTextMessage(format.Color("green", plugin.Language.getCommand().getCreateBank_HasAccount(lang)));
                            }
                        }
                    }
                }
                if (cmd.length == 2) {
                    if (cmd[1].toLowerCase().equals("send")) {
                        plugin.GUI.SendCashGui.showGUI(player);
                    }
                }

                if (cmd.length == 3) {
                    if (cmd[1].toLowerCase().equals("bank")) {
                        if (!plugin.Config.Command_Bank_OnlyAdmin || player.isAdmin()) {
                            switch (cmd[2].toLowerCase()) {
                                case "in" ->
                                    plugin.GUI.Bankystem.CashInOutGui.showGUI(player, CashInOutGUI.Modus.In);
                                case "out" ->
                                    plugin.GUI.Bankystem.CashInOutGui.showGUI(player, CashInOutGUI.Modus.Out);
                            }
                        } else {
                            player.sendTextMessage(format.Color("red", plugin.Language.getOther().getNoPermission(lang)));
                        }
                    }
                }
            }
        }
    }
}
