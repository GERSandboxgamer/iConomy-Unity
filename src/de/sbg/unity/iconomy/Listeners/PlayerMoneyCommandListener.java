package de.sbg.unity.iconomy.Listeners;

import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerCommandEvent;
import net.risingworld.api.objects.Player;

public class PlayerMoneyCommandListener implements Listener {

    private final iConomy plugin;
    private final icConsole Console;

    public PlayerMoneyCommandListener(iConomy plugin, icConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
    }

    @EventMethod
    public void onPlayerCommandEvent(PlayerCommandEvent event) {
        String[] cmd = event.getCommand().split(" ");
        Player player = event.getPlayer();

        if (cmd[0].toLowerCase().equals("/balance")) {
            if (cmd.length == 1) {
                if (plugin.Bankystem.PlayerSystem.hasPlayerAccount(player)) {
                    plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + plugin.CashSystem.getCashAsFormatedString(player), "Bank: " + plugin.Bankystem.PlayerSystem.getPlayerAccount(player).getMoneyAsFormatedString());
                } else {
                    plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + plugin.CashSystem.getCashAsFormatedString(player), "Bank: (No Account)");
                }
            }
            if (cmd.length == 2) {
                if (cmd[1].toLowerCase().equals("all")) {
                    if (plugin.Bankystem.PlayerSystem.hasPlayerAccount(player)) {
                        plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + plugin.CashSystem.getCashAsFormatedString(player), "Bank: " + plugin.Bankystem.PlayerSystem.getPlayerAccount(player).getMoneyAsFormatedString());
                    } else {
                        plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + plugin.CashSystem.getCashAsFormatedString(player), "Bank: (No Account)");
                    }
                }
                if (cmd[1].toLowerCase().equals("cash")) {
                    plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + plugin.CashSystem.getCashAsFormatedString(player));
                }
                if (cmd[1].toLowerCase().equals("bank")) {
                    if (plugin.Bankystem.PlayerSystem.hasPlayerAccount(player)) {
                        plugin.GUI.MoneyInfoGui.showGUI(player, "Bank: " + plugin.Bankystem.PlayerSystem.getPlayerAccount(player).getMoneyAsFormatedString());
                    } else {
                        plugin.GUI.MoneyInfoGui.showGUI(player, "Bank: (No Account)");
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
                    plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + plugin.CashSystem.getCashAsFormatedString(player), "Bank: (No Account)");
                }
            }
            if (cmd.length >= 2) {

                if (cmd.length == 2) {
                    if (cmd[1].toLowerCase().equals("createbank") || cmd[1].toLowerCase().equals("cb")) {

                    }

                    if (cmd[1].toLowerCase().equals("send")) {
                        plugin.GUI.SendCashGui.showGUI(player);
                    }
                }

            }
        }

    }

}
