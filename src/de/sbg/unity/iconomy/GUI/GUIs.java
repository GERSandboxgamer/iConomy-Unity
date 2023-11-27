package de.sbg.unity.iconomy.GUI;

import de.sbg.unity.iconomy.GUI.Banksystem.BankGuiSystem;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.objects.Player;

public class GUIs {

    private final icConsole Console;
    private final iConomy plugin;
    public final MoneyInfo MoneyInfoGui;
    public final SendCash SendCashGui;
    public final BankGuiSystem Bankystem;
    public final PlaceAtm PlaceAtmGui;

    public GUIs(iConomy plugin, icConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        this.MoneyInfoGui = new MoneyInfo();
        this.SendCashGui = new SendCash();
        this.Bankystem = new BankGuiSystem(plugin, Console);
        this.PlaceAtmGui = new PlaceAtm();
    }

    public class MoneyInfo {

        private final String GuiPlayerAtt;

        public MoneyInfo() {
            GuiPlayerAtt = "iConomy-MoneyInfoGUI";
        }

        public void showGUI(Player player, String Text) {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("GUIs", "showGUI (Line 1)");
            }
            MoneyInfoGUI gui;
            if (!player.hasAttribute(GuiPlayerAtt)) {
                gui = new MoneyInfoGUI(plugin, Console, player, Text);
                player.setAttribute(GuiPlayerAtt, gui);
            } else {
                gui = (MoneyInfoGUI) player.getAttribute(GuiPlayerAtt);
                if (gui.getLineAmount() == 1) {
                    gui.stopCloseTimer();
                    gui.getLine1().setText(Text);
                    gui.startCloseTimer();
                } else {
                    gui.stopCloseTimer();
                    hideGUI(player);
                    MoneyInfoGUI gui2 = new MoneyInfoGUI(plugin, Console, player, Text);
                    player.setAttribute(GuiPlayerAtt, gui2);
                }
            }
        }

        public void showGUI(Player player, String Line1, String Line2) {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("GUIs", "showGUI (Line 2)");
            }
            MoneyInfoGUI gui;
            if (!player.hasAttribute(GuiPlayerAtt)) {
                gui = new MoneyInfoGUI(plugin, Console, player, Line1, Line2);
                player.setAttribute(GuiPlayerAtt, gui);
            } else {
                gui = (MoneyInfoGUI) player.getAttribute(GuiPlayerAtt);
                if (gui.getLineAmount() == 2) {
                    gui.stopCloseTimer();
                    gui.getLine1().setText(Line1);
                    gui.getLine2().setText(Line2);
                    gui.startCloseTimer();
                } else {
                    gui.stopCloseTimer();
                    hideGUI(player);
                    MoneyInfoGUI gui2 = new MoneyInfoGUI(plugin, Console, player, Line1, Line2);
                    player.setAttribute(GuiPlayerAtt, gui2);
                }
            }
        }

        public boolean hideGUI(Player player) {
            if (player.hasAttribute(GuiPlayerAtt)) {
                player.removeUIElement(((MoneyInfoGUI) player.getAttribute(GuiPlayerAtt)).getPanel());
                player.deleteAttribute(GuiPlayerAtt);
                return true;
            }
            return false;
        }
    }

    public class SendCash {

        private final String GuiPlayerAtt;

        public SendCash() {
            GuiPlayerAtt = "iConomy-SendCashGUI";
        }

        public SendCashGUI showGUI(Player player) {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("GUIs-SendCash", "showGUI");
            }
            SendCashGUI gui = new SendCashGUI(plugin, Console, player);
            player.setAttribute(GuiPlayerAtt, gui);
            player.setMouseCursorVisible(true);
            return gui;
        }

        public boolean hideGUI(Player player) {
            if (player.hasAttribute(GuiPlayerAtt)) {
                player.removeUIElement(((SendCashGUI) player.getAttribute(GuiPlayerAtt)).getPanel());
                player.deleteAttribute(GuiPlayerAtt);
                player.setMouseCursorVisible(false);
                return true;
            }
            return false;
        }
        
        public SendCashGUI getGui(Player player) {
            if (player.hasAttribute(GuiPlayerAtt)) {
                SendCashGUI gui = (SendCashGUI) player.getAttribute(GuiPlayerAtt);
                return gui;
            }
            return null;
        }

    }
    public class PlaceAtm {
        
        private final String GuiPlayerAtt;

        public PlaceAtm() {
            this.GuiPlayerAtt = "iConomy-PlaceAtmGUI";
        }
        
        public PlaceAtmGUI showGUI(Player player) {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("GUIs-PlaceAtm", "showGUI");
            }
            PlaceAtmGUI gui = new PlaceAtmGUI(player, plugin, Console);
            player.setAttribute(GuiPlayerAtt, gui);
            return gui;
        }
        
        public boolean hideGUI(Player player) {
            if (player.hasAttribute(GuiPlayerAtt)) {
                player.removeUIElement(((PlaceAtmGUI) player.getAttribute(GuiPlayerAtt)).getPanel());
                player.deleteAttribute(GuiPlayerAtt);
                return true;
            }
            return false;
        }
        
        public PlaceAtmGUI getGui(Player player) {
            if (player.hasAttribute(GuiPlayerAtt)) {
                PlaceAtmGUI gui = (PlaceAtmGUI) player.getAttribute(GuiPlayerAtt);
                return gui;
            }
            return null;
        }
    }
}
