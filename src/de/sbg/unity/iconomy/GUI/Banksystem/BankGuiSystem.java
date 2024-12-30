package de.sbg.unity.iconomy.GUI.Banksystem;

import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.objects.Player;

public class BankGuiSystem {

    private final iConomy plugin;
    private final icConsole Console;

    public final CashInOut CashInOutGui;
    public final SelectCashInOut SelectCashInOutGui;
    public final Main MainGui;

    public BankGuiSystem(iConomy plugin, icConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        this.CashInOutGui = new CashInOut();
        this.SelectCashInOutGui = new SelectCashInOut();
        this.MainGui = new Main();
    }

    public class Main {

        private final String GuiPlayerAtt;

        public Main() {
            GuiPlayerAtt = "iConomy-Bank-Main";
        }

        public void showGUI(Player player) {
            showGUI(player, false);
        }

        public MainGUI getGui(Player player) {
            if (player.hasAttribute(GuiPlayerAtt)) {
                return (MainGUI) player.getAttribute(GuiPlayerAtt);
            }
            return null;
        }

        public void showGUI(Player player, boolean showAllAccounts) {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("GUIs-CashInOut", "showGUI");
            }
            MainGUI gui = new MainGUI(player, showAllAccounts, plugin);
            plugin.registerEventListener(gui);
            player.setAttribute(GuiPlayerAtt, gui);
        }

        public boolean hideGUI(Player player) {
            return hideGUI(player, true);
        }

        public boolean hideGUI(Player player, boolean hideMouse) {
            if (player.hasAttribute(GuiPlayerAtt)) {
                MainGUI gui = (MainGUI)player.getAttribute(GuiPlayerAtt);
                plugin.unregisterEventListener(gui);
                player.removeUIElement(gui.getWindow());
                player.deleteAttribute(GuiPlayerAtt);
                if (hideMouse) {
                    player.setMouseCursorVisible(false);
                }
                return true;
            }
            return false;
        }

    }

    public class CashInOut {

        private final String GuiPlayerAtt;

        public CashInOut() {
            GuiPlayerAtt = "iConomy-CashInOutGUI";
        }

        public void showGUI(Player player, CashInOutGUI.Modus modus) {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("GUIs-CashInOut", "showGUI");
            }
            CashInOutGUI gui = new CashInOutGUI(plugin, Console, player, modus);
            player.setAttribute(GuiPlayerAtt, gui);
            player.setMouseCursorVisible(true);
        }

        public boolean hideGUI(Player player) {
            return hideGUI(player, true);
        }

        public boolean hideGUI(Player player, boolean hideMouse) {
            if (player.hasAttribute(GuiPlayerAtt)) {
                player.removeUIElement(((CashInOutGUI) player.getAttribute(GuiPlayerAtt)).getPanel());
                player.deleteAttribute(GuiPlayerAtt);

                if (hideMouse) {
                    player.setMouseCursorVisible(false);
                }
                return true;
            }
            return false;
        }
    }

    public class SelectCashInOut {

        private final String GuiPlayerAtt;

        public SelectCashInOut() {
            GuiPlayerAtt = "iConomy-SelectCashInOut";
        }

        public void showGUI(Player player) {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("GUIs-SelectCashInOut", "showGUI");
            }
            SelectCashInOutGUI gui = new SelectCashInOutGUI(plugin, Console, player);
            player.setAttribute(GuiPlayerAtt, gui);
            player.setMouseCursorVisible(true);
        }

        public boolean hideGUI(Player player) {
            return hideGUI(player, true);
        }

        public boolean hideGUI(Player player, boolean hideMouse) {
            if (player.hasAttribute(GuiPlayerAtt)) {
                player.removeUIElement(((SelectCashInOutGUI) player.getAttribute(GuiPlayerAtt)).getPanel());
                player.deleteAttribute(GuiPlayerAtt);
                if (hideMouse) {
                    player.setMouseCursorVisible(false);
                }
                return true;
            }
            return false;
        }
    }

}
