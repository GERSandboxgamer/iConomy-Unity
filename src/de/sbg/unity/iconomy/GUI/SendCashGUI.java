package de.sbg.unity.iconomy.GUI;

import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.ui.PlayerUIElementClickEvent;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.style.Pivot;


public class SendCashGUI {
    
    private final UIElement panel;
    private final icConsole Console;
    private final Player player;
    private final iConomy plugin;
    public UILabel butSend, butCancel;
    
    public SendCashGUI(iConomy plugin, icConsole Console, Player player) {
        panel = new UIElement();
        this.Console = Console;
        this.player = player;
        this.plugin = plugin;

        panel.setPosition(50, 50, true);  // <- true indicates we're using relative coordinates (0-100%)
        panel.setPivot(Pivot.MiddleCenter);
        panel.setSize(800, 500, false);
        panel.setBackgroundColor(0, 0, 102, 1);  // black background
        
        TitelBar();
        Body();
        player.addUIElement(panel);
        plugin.registerEventListener(new SendCashGuiListener());
    }
    
    private void Body(){
        UILabel message = new UILabel("Send money to a player!");
        message.setPosition(50, 15, true);
        message.setPivot(Pivot.UpperCenter);
        message.setSize(800, 50, false);
        message.setFontSize(25);
        panel.addChild(message);
        
        UILabel PlayerCash = new UILabel("Yor Cash: " + plugin.CashSystem.getCashAsFormatedString(player));
        PlayerCash.setPosition(50, 30, true);
        PlayerCash.setPivot(Pivot.UpperCenter);
        PlayerCash.setSize(100, 50, false);
        PlayerCash.setFontSize(25);
        panel.addChild(PlayerCash);
        
        
        butSend = new UILabel("Send");
        butSend.setPosition(100, 100, true);
        butSend.setPivot(Pivot.LowerRight);
        butSend.setSize(800, 50, false);
        butSend.setClickable(true);
        butSend.setFontSize(25);
        
        panel.addChild(butSend);
        
        butCancel = new UILabel("Cancel");
        butCancel.setPosition(0, 100, true);
        butCancel.setPivot(Pivot.LowerLeft);
        butCancel.setSize(100, 50, false);
        butCancel.setFontSize(25);
        butCancel.setClickable(true);
        panel.addChild(butCancel);
    }
    
    private void TitelBar() {
        
        UIElement tielBar = new UIElement();
        tielBar.setPosition(50, 0, true);  // <- true indicates we're using relative coordinates (0-100%)
        tielBar.setPivot(Pivot.UpperCenter);
        tielBar.setSize(800, 50, false);
        tielBar.setBackgroundColor(255, 153, 51, 1);  // black background
        
        UILabel titel = new UILabel("Send Money");
        titel.setPosition(50, 50, true);
        titel.setPivot(Pivot.MiddleCenter);
        titel.setFontSize(25);
        titel.setFontColor(255, 255, 255, 1);
        titel.setBackgroundColor(255, 153, 51, 1);
        tielBar.addChild(titel);  // <- label becomes a child of panel
        panel.addChild(tielBar);
    } 
    
    public UIElement getPanel() {
        return panel;
    }
    
    public class SendCashGuiListener implements Listener {
                
        @EventMethod
        public void onGuiKlickEvent(PlayerUIElementClickEvent event) {
            UIElement el = event.getUIElement();
            Player player = event.getPlayer();
            
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("GuiClick", "el: " + el);
            }
            
            if (el.getID() == butCancel.getID()) {
                plugin.GUI.SendCashGui.hideGUI(player);
                plugin.unregisterEventListener(this);
            }
            
        }
        
    }
    
}
