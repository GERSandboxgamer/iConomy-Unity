package de.sbg.unity.iconomy.Objects;

import de.sbg.unity.iconomy.GUI.CashInOutGUI;
import de.sbg.unity.iconomy.Utils.TextFormat;
import de.sbg.unity.iconomy.iConomy;
import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerObjectInteractionEvent;
import net.risingworld.api.events.player.PlayerSetSignTextEvent;
import net.risingworld.api.objects.Player;
import net.risingworld.api.utils.Utils;

public class icSign implements Listener {

    private final List<String> SignList;
    private final iConomy plugin;
    private final TextFormat format;

    public icSign(iConomy plugin) {
        this.SignList = new ArrayList<>();
        this.plugin = plugin;
        this.format = new TextFormat();
        iniSign();
    }

    public List<String> getSignList() {
        return SignList;
    }

    private void iniSign() {
        SignList.add("[Bank]");
        SignList.add("[Balance]");
    }

    public boolean isIcSign(String SignText) {
        return SignList.stream().anyMatch(s -> (SignText.contains(s)));
    }

    @EventMethod
    public void onPlayerSetSignTextEvent(PlayerSetSignTextEvent event) {
        Player player = event.getPlayer();
        String lang = player.getLanguage();
        String[] line = Utils.StringUtils.getLines(event.getText());
        Result r = Result.Nothing;
        if (!event.getText().isBlank() && !event.getText().isEmpty() && line.length > 1) {
            if (isIcSign(event.getText())) {
                if (player.isAdmin()) {
                    Signs signs = new Signs(plugin);
                    signs.setInteract(false);
                    signs.setPlayer(player);
                    if (line.length == 2) {
                        signs.setLine2(line[1]);
                    } else {
                        signs.setLine2("");
                    }
                    switch (line[0]) {
                        case "[Balance]" ->
                            r = signs.Balance();
                        case "[Bank]" ->
                            r = signs.Bank();
                    }
                } else {
                    r = Result.Permission;
                }
            }
        }
        switch (r) {
            case Permission -> {
                event.setCancelled(true);
                player.sendTextMessage(format.Color("red", plugin.Language.getOther().getNoPermission(lang))); 
            }
            case OK ->
                player.sendTextMessage(format.Color("green", plugin.Language.getStatus().getSign_OK(lang))); 
            case Misspelled ->
                player.sendTextMessage(format.Color("red", plugin.Language.getStatus().getSign_Misspelled(lang))); 
        }
    }

    @EventMethod
    public void onPlayerObjectInteraktionEvent(PlayerObjectInteractionEvent event) {

    }

    private enum Result {

        OK("Everything OK!"),
        Permission("Player does not have enough permission!"),
        Money("Player does not have enough money!"),
        Misspelled("Sign is spelled wrong!"),
        Error("There has been an error!"),
        EventCancel("Event was cancelled!"),
        EditMode("Edit mode is active"),
        Nothing("No result found!"),
        Statement("Statement does not match"),
        GameObject("Game Object not found!"),
        Waiting("Waiting for something");

        private final String msg;

        Result(String msg) {
            this.msg = msg;
        }

        public String getStatusMessage() {
            return msg;
        }

    }

    private class Signs {

        private String Line2;
        private boolean Interact;
        private final iConomy plugin;
        private Player player;

        private Signs(iConomy plugin) {
            this.plugin = plugin;
        }

        private void setLine2(String Line2) {
            this.Line2 = Line2;
        }

        private void setInteract(boolean Interact) {
            this.Interact = Interact;
        }

        private void setPlayer(Player player) {
            this.player = player;
        }

        private Result Balance() {
            if (Line2.isBlank() || Line2.isEmpty()) {
                if (Interact) {
                    if (plugin.Bankystem.PlayerSystem.hasPlayerAccount(player)) {
                        plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + plugin.CashSystem.getCashAsFormatedString(player), "Bank: " + plugin.Bankystem.PlayerSystem.getPlayerAccount(player).getMoneyAsFormatedString());
                    } else {
                        plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + plugin.CashSystem.getCashAsFormatedString(player), "Bank: (No Account)");
                    }
                }
                return Result.OK;
            }
            switch (Line2.toLowerCase()) {
                case "all" -> {
                    if (Interact) {
                        if (plugin.Bankystem.PlayerSystem.hasPlayerAccount(player)) {
                            plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + plugin.CashSystem.getCashAsFormatedString(player), "Bank: " + plugin.Bankystem.PlayerSystem.getPlayerAccount(player).getMoneyAsFormatedString());
                        } else {
                            plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + plugin.CashSystem.getCashAsFormatedString(player), "Bank: (No Account)");
                        }
                    }
                    return Result.OK;
                }
                case "cash" -> {
                    if (Interact) {
                        plugin.GUI.MoneyInfoGui.showGUI(player, "Cash: " + plugin.CashSystem.getCashAsFormatedString(player));
                    }
                    return Result.OK;
                }
                case "bank" -> {
                    if (Interact) {
                        if (plugin.Bankystem.PlayerSystem.hasPlayerAccount(player)) {
                            plugin.GUI.MoneyInfoGui.showGUI(player, "Bank: " + plugin.Bankystem.PlayerSystem.getPlayerAccount(player).getMoneyAsFormatedString());
                        } else {
                            plugin.GUI.MoneyInfoGui.showGUI(player, "Bank: (No Account)");
                        }
                    }
                    return Result.OK;
                }
            }
            return Result.Misspelled;
        }

        private Result Bank() {
            if (Line2.isBlank() || Line2.isEmpty()) {
                return Result.Misspelled;
            }
            switch (Line2.toLowerCase()) {
                case "out" -> {
                    if (Interact) {
                        plugin.GUI.CashInOutGui.showGUI(player, CashInOutGUI.Modus.Out);
                    }
                    return Result.OK;
                }
                case "in" -> {
                    if (Interact) {
                        plugin.GUI.CashInOutGui.showGUI(player, CashInOutGUI.Modus.In);
                    }
                    return Result.OK;
                }
                default -> {
                    return Result.Misspelled;
                }
            }
        }
    }

}
