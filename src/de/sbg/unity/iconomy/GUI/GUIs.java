package de.sbg.unity.iconomy.GUI;

import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.objects.Player;

public class GUIs {

    private final icConsole Console;
    private final iConomy plugin;
    public final MoneyInfo MoneyInfoGui;
    public final SendCash SendCashGui;

    public GUIs(iConomy plugin, icConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        this.MoneyInfoGui = new MoneyInfo();
        this.SendCashGui = new SendCash();
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
            MoneyInfoGUI gui = new MoneyInfoGUI(plugin, Console, player, Text);
            player.setAttribute(GuiPlayerAtt, gui);
        }

        public void showGUI(Player player, String Line1, String Line2) {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("GUIs", "showGUI (Line 2)");
            }
            MoneyInfoGUI gui = new MoneyInfoGUI(plugin, Console, player, Line1, Line2);
            player.setAttribute(GuiPlayerAtt, gui);
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
        
         public void showGUI(Player player) {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("GUIs", "showGUI (Line 1)");
            }
            SendCashGUI gui = new SendCashGUI(plugin, Console, player);
            player.setAttribute(GuiPlayerAtt, gui);
            player.setMouseCursorVisible(true);
            
        }
         
         public boolean hideGUI(Player player) {
            if (player.hasAttribute(GuiPlayerAtt)) {
                player.removeUIElement(((SendCashGUI)player.getAttribute(GuiPlayerAtt)).getPanel());
                player.deleteAttribute(GuiPlayerAtt);
                player.setMouseCursorVisible(false);
                return true;
            }
            return false;
        }
        
        
    }

}
