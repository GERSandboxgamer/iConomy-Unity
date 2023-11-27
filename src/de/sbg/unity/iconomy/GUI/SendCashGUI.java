package de.sbg.unity.iconomy.GUI;

import de.sbg.unity.iconomy.Events.Money.AddCashEvent;
import de.sbg.unity.iconomy.Events.Money.RemoveCashEvent;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.ui.PlayerUIElementClickEvent;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.UITextField;
import net.risingworld.api.ui.style.Pivot;
import de.sbg.unity.iconomy.Utils.TextFormat;
import de.sbg.unity.iconomy.Utils.TransferResult;
import net.risingworld.api.Server;

public class SendCashGUI {
    
    private final UIElement panel;
    private final icConsole Console;
    private final Player player;
    private final iConomy plugin;
    private final TextFormat format;
    public UILabel butSend, butCancel;
    public UITextField Cash, Receiver;
    private final String lang;
    
    public SendCashGUI(iConomy plugin, icConsole Console, Player player) {
        panel = new UIElement();
        this.Console = Console;
        this.player = player;
        this.plugin = plugin;
        this.format = new TextFormat();
        this.lang = player.getLanguage();
        
        panel.setPosition(50, 50, true);
        panel.setPivot(Pivot.MiddleCenter);
        panel.setSize(500, 500, false);
        panel.setBackgroundColor(0, 0, 102, 1);
        
        TitelBar();
        Body();
        player.addUIElement(panel);
        plugin.registerEventListener(new SendCashGuiListener());
    }
    
    private void Body() {
        UILabel message = new UILabel(plugin.Language.getGui().getSendCashGUI_BodyText(lang));
        message.setPosition(50, 15, true);
        message.setPivot(Pivot.UpperCenter);
        message.setSize(800, 50, false);
        message.setFontSize(25);
        panel.addChild(message);
        
        UILabel PlayerCash = new UILabel(plugin.Language.getGui().getYourCash(lang) + " " + format.Bold(format.Color("yellow", plugin.CashSystem.getCashAsFormatedString(player))));
        PlayerCash.setPosition(50, 25, true);
        PlayerCash.setPivot(Pivot.UpperCenter);
        PlayerCash.setFontSize(25);
        panel.addChild(PlayerCash);
        
        UILabel labReceiver = new UILabel(plugin.Language.getGui().getSendCashGUI_Player(lang));
        labReceiver.setPosition(50, 37, true);
        labReceiver.setPivot(Pivot.UpperCenter);
        labReceiver.setFontSize(25);
        panel.addChild(labReceiver);
        
        Receiver = new UITextField();
        Receiver.setPosition(50, 45, true);
        Receiver.setPivot(Pivot.UpperCenter);
        Receiver.setSize(300, 50, false);
        Receiver.setFontSize(25);
        panel.addChild(Receiver);
        
        UILabel labCash = new UILabel(plugin.Language.getGui().getGUI_Amount(lang));
        labCash.setPosition(50, 57, true);
        labCash.setPivot(Pivot.UpperCenter);
        labCash.setFontSize(25);
        panel.addChild(labCash);
        
        Cash = new UITextField();
        Cash.setPosition(50, 65, true);
        Cash.setPivot(Pivot.UpperCenter);
        Cash.setSize(250, 50, false);
        Cash.setFontSize(25);
        panel.addChild(Cash);
        
        butSend = new UILabel(format.Bold(format.Color("green", "[" + plugin.Language.getGui().getSend(lang) + "]")));
        butSend.setPosition(95, 95, true);
        butSend.setPivot(Pivot.LowerRight);
        butSend.setClickable(true);
        butSend.setFontSize(25);
        panel.addChild(butSend);
        
        butCancel = new UILabel(format.Bold(format.Color("red", "[" + plugin.Language.getGui().getCancel(lang) + "]")));
        butCancel.setPosition(5, 95, true);
        butCancel.setPivot(Pivot.LowerLeft);
        butCancel.setFontSize(25);
        butCancel.setClickable(true);
        panel.addChild(butCancel);
    }
    
    private void TitelBar() {
        
        UIElement tielBar = new UIElement();
        tielBar.setPosition(50, 0, true);
        tielBar.setPivot(Pivot.UpperCenter);
        tielBar.setSize(500, 50, false);
        tielBar.setBackgroundColor(255, 153, 51, 1);
        
        UILabel titel = new UILabel(plugin.Language.getGui().getSendCashGUI_Title(lang));
        titel.setPosition(50, 50, true);
        titel.setPivot(Pivot.MiddleCenter);
        titel.setFontSize(25);
        titel.setFontColor(255, 255, 255, 1);
        titel.setBackgroundColor(255, 153, 51, 1);
        tielBar.addChild(titel);
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
            
            if (el == butCancel) {
                plugin.GUI.SendCashGui.hideGUI(player);
                plugin.unregisterEventListener(this);
            }
            
            if (el == butSend) {
                Receiver.getCurrentText(player, (s) -> {
                    Player p2 = Server.getPlayerByName(s);
                    if (p2 != null && p2.isConnected()) {
                        if (!s.equals(player.getName())) {
                            Cash.getCurrentText(player, (c) -> {
                                long l;
                                try {
                                    l = plugin.moneyFormat.getMoneyAsLong(c);
                                    if (l > 0) {
                                        TransferResult tr = plugin.CashSystem.removeCash(player, l, RemoveCashEvent.Reason.Player);
                                        switch (tr) {
                                            case Successful -> {
                                                plugin.CashSystem.addCash(p2, l, AddCashEvent.Reason.Player);
                                                plugin.GUI.SendCashGui.hideGUI(player);
                                            }
                                            case NotEnoughMoney -> {
                                                player.showErrorMessageBox("iConomy - Bank", plugin.Language.getStatus().getPlayerNotAnounthMoney(lang));
                                            }
                                            case PlayerNotExist -> {
                                                player.showErrorMessageBox("iConomy - Bank", plugin.Language.getStatus().getPlayerNotExist(lang));
                                            }
                                            case PlayerNotConnected -> {
                                                player.showErrorMessageBox("iConomy - Bank", plugin.Language.getStatus().getPlayerNotConnected(lang));
                                            }
                                            case EventCancel -> {
                                                player.showErrorMessageBox("iConomy - Bank", plugin.Language.getStatus().getTransferCancel(lang));
                                            }
                                            
                                        }
                                    } else {
                                        player.showErrorMessageBox("Error", plugin.Language.getStatus().getAmountBigger(lang));
                                    }
                                } catch (NumberFormatException ex) {
                                    
                                }
                            });
                        } else {
                            player.showErrorMessageBox("iConomy - Send Money", plugin.Language.getStatus().getSendCashToSelf(lang));
                        }
                    } else {
                        player.showErrorMessageBox("iConomy - Send Money", plugin.Language.getStatus().getPlayerNotConnected(lang));
                    }
                });
            }
            
        }
        
    }
    
}
