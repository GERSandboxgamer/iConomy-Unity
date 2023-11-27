package de.sbg.unity.iconomy.GUI.Banksystem;

import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.objects.Player;

public class BankGuiSystem {

    private final iConomy plugin;
    private final icConsole Console;

    public final CashInOut CashInOutGui;
    public final SelectCashInOut SelectCashInOutGui;

    public BankGuiSystem(iConomy plugin, icConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        this.CashInOutGui = new CashInOut();
        this.SelectCashInOutGui = new SelectCashInOut();
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
            if (player.hasAttribute(GuiPlayerAtt)) {
                player.removeUIElement(((CashInOutGUI) player.getAttribute(GuiPlayerAtt)).getPanel());
                player.deleteAttribute(GuiPlayerAtt);
                player.setMouseCursorVisible(false);
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
