package de.sbg.unity.iconomy.GUI;

import de.sbg.unity.iconomy.GUI.List.PlayerList;
import de.sbg.unity.iconomy.GUI.Banksystem.BankGuiSystem;
import de.sbg.unity.iconomy.GUI.List.AccountList;
import de.sbg.unity.iconomy.GUI.List.PlayerList.SelectCallback;
import de.sbg.unity.iconomy.GUI.Speak.SpeakGuiSystem;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.Server;
import net.risingworld.api.objects.Player;

public class GUIs {

    private final icConsole Console;
    private final iConomy plugin;
    public final MoneyInfo MoneyInfoGui;
    public final SendCash SendCashGui;
    public final BankGuiSystem Bankystem;
    public final PlaceAtm PlaceAtmGui;
    public final SelectOnlinePlayer SelectOnlinePlayerGui;
    public final SelectAccount SelectAccountGui;
    public final SpeakGuiSystem speakGuiSystem;
    public final SmallCash smallCash;

    public GUIs(iConomy plugin, icConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        this.MoneyInfoGui = new MoneyInfo();
        this.SendCashGui = new SendCash();
        this.Bankystem = new BankGuiSystem(plugin, Console);
        this.PlaceAtmGui = new PlaceAtm();
        this.SelectOnlinePlayerGui = new SelectOnlinePlayer();
        this.SelectAccountGui = new SelectAccount();
        this.speakGuiSystem  = new SpeakGuiSystem(plugin);
        this.smallCash = new SmallCash();
    }
    
    public class SmallCash {
        
        private final String guiCashAtt;

        public SmallCash() {
            this.guiCashAtt = "sbg-iConomy-SpeakCashGuiAtt";
        }
        
        public SmallCashGUI show(Player player){
            if (!player.hasAttribute(guiCashAtt)) {
                SmallCashGUI gui = new SmallCashGUI(plugin, player);
                player.setAttribute(guiCashAtt, gui);
                player.addUIElement(gui);
            }
            return get(player);
        }
        
        public void hide(Player player) {
            if (player.hasAttribute(guiCashAtt)) {
                SmallCashGUI gui = (SmallCashGUI)player.getAttribute(guiCashAtt);
                player.removeUIElement(gui);
                player.deleteAttribute(guiCashAtt);
            }
        }
        
        public SmallCashGUI get(Player player){
            if (player.hasAttribute(guiCashAtt)) {
               return (SmallCashGUI)player.getAttribute(guiCashAtt);
            }
            return null;
        }
        
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
            if (!player.hasAttribute(GuiPlayerAtt)) {
                SendCashGUI gui = new SendCashGUI(plugin, Console, player);
                player.setAttribute(GuiPlayerAtt, gui);
                player.setMouseCursorVisible(true);
                return gui;
            } else {
                player.showErrorMessageBox("iConomy - Error", "GUI bereits geöffnet!"); //TODO Translate
                if (plugin.Config.Debug > 0) {
                    Console.sendWarning("ShopMain-showGUI", "GUI is already open!");
                }
            }
            return getGui(player);
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

    public class SelectOnlinePlayer {

        private final String GuiPlayerAtt;

        public SelectOnlinePlayer() {
            this.GuiPlayerAtt = "iConomy-SlectOnlinePlayerGUI";
        }

        public PlayerList showGui(Player player, SelectCallback cb) {
            if (!player.hasAttribute(GuiPlayerAtt)) {
                if (Server.getPlayerCount() >= 2 || plugin.Config.Debug > 0) {
                    String titeltext = "Select Online Player"; //TODO Lang
                    PlayerList gui = new PlayerList(player, true, titeltext, plugin, cb);
                    gui.getPlayerListElements().addElements();
                    plugin.registerEventListener(gui);
                    player.setAttribute(GuiPlayerAtt, gui);
                    player.setMouseCursorVisible(true);
                    return gui;
                }
                player.showErrorMessageBox("No Players", "Es wurden keine weiteren Spieler gefunden!"); //TODO Lang
                return null;
            }
            return getGui(player);
        }

        public boolean hideGui(Player player) {

            if (player.hasAttribute(GuiPlayerAtt)) {
                PlayerList gui = (PlayerList) player.getAttribute(GuiPlayerAtt);
                plugin.unregisterEventListener(gui);
                player.removeUIElement(gui.getPanel());
                player.deleteAttribute(GuiPlayerAtt);
                
                return true;
            }
            return false;
        }

        public PlayerList getGui(Player player) {
            if (player.hasAttribute(GuiPlayerAtt)) {
                PlayerList gui = (PlayerList) player.getAttribute(GuiPlayerAtt);
                return gui;
            }
            return null;
        }
    }
    
    public class SelectAccount {
        
        private final String GuiSelectAccount;
        
        public SelectAccount() {
            this.GuiSelectAccount = "iConomy-GuiSelectAccount";
        }
        
        public SelectAccountGUI showGUI(Player player, AccountList.SelectCallback cb) {
            if (!player.hasAttribute(GuiSelectAccount)) {
                SelectAccountGUI gui = new SelectAccountGUI(player, "Select Account", plugin, cb); //TODO Lang
                player.setAttribute(GuiSelectAccount, gui);
                player.setMouseCursorVisible(true);
                player.addUIElement(gui.getWindow());
                return gui;
            } else {
                SelectAccountGUI gui = (SelectAccountGUI) player.getAttribute(GuiSelectAccount);
                return gui;
            }
        }
        
        public boolean hideGUI(Player player) {
            if (player.hasAttribute(GuiSelectAccount)) {
                SelectAccountGUI gui = (SelectAccountGUI) player.getAttribute(GuiSelectAccount);
                player.removeUIElement(gui.getWindow());
                player.deleteAttribute(GuiSelectAccount);
            }
            return false;
        }
        
        public SelectAccountGUI getGui(Player player) {
            if (player.hasAttribute(GuiSelectAccount)) {
                SelectAccountGUI gui = (SelectAccountGUI) player.getAttribute(GuiSelectAccount);
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
