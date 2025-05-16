package de.sbg.unity.iconomy.GUI;

import de.chaoswg.gui.UIButton;
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
import net.risingworld.api.assets.TextureAsset;
import net.risingworld.api.ui.style.ScaleMode;

public class SendCashGUI {

    private final UIElement panel;
    private final icConsole Console;
    private final Player player;
    private final iConomy plugin;
    private final TextFormat format;
    private UILabel buttonSend, buttonCancel;
    private UITextField Cash, Receiver;
    //private UILabel labPlayername;
    private UIElement findPlayer;
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

        UILabel labReceiver = new UILabel("UID:");
        labReceiver.setPosition(5, 35, true);
        labReceiver.setPivot(Pivot.UpperLeft);
        labReceiver.setSize(25, 25, false);
        labReceiver.setFontSize(25);
        panel.addChild(labReceiver);

        Receiver = new UITextField();
        Receiver.setPosition(5, 53, true);
        Receiver.setPivot(Pivot.LowerLeft);
        Receiver.setSize(300, 50, false);
        Receiver.setFontSize(25);
        panel.addChild(Receiver);

        findPlayer = new UIElement();
        findPlayer.style.backgroundImage.set(TextureAsset.loadFromPlugin(plugin, "/resources/Suche.png"));
        findPlayer.setSize(32, 32, false);
        findPlayer.setClickable(true);
        findPlayer.setPosition(80, 45, true);
        findPlayer.style.backgroundImageScaleMode.set(ScaleMode.ScaleToFit);
        panel.addChild(findPlayer);

//        labPlayername = new UILabel();
//        labPlayername.setPosition(5, 48, true);
//        labPlayername.setPivot(Pivot.UpperCenter);
//        labPlayername.setFontSize(25);
//        labPlayername.setVisible(false);
//        panel.addChild(labReceiver);
        UILabel labCash = new UILabel(plugin.Language.getGui().getGUI_Amount(lang));
        labCash.setPosition(5, 64, true);
        labCash.setPivot(Pivot.UpperLeft);
        labCash.setFontSize(25);
        panel.addChild(labCash);

        Cash = new UITextField();
        Cash.setPosition(5, 72, true);
        Cash.setPivot(Pivot.UpperLeft);
        Cash.setSize(250, 50, false);
        Cash.setFontSize(25);
        panel.addChild(Cash);

        buttonSend = new UILabel(format.Bold(format.Color("green", "[" + plugin.Language.getGui().getSend(lang) + "]")));
        buttonSend.setPosition(95, 95, true);
        buttonSend.setPivot(Pivot.LowerRight);
        buttonSend.setClickable(true);
        buttonSend.setFontSize(25);
        panel.addChild(buttonSend);

        buttonCancel = new UILabel(format.Bold(format.Color("red", "[" + plugin.Language.getGui().getCancel(lang) + "]")));
        buttonCancel.setPosition(5, 95, true);
        buttonCancel.setPivot(Pivot.LowerLeft);
        buttonCancel.setFontSize(25);
        buttonCancel.setClickable(true);
        panel.addChild(buttonCancel);
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

    public UIElement getFindPlayerButton() {
        return findPlayer;
    }

    public UILabel getButtonCancel() {
        return buttonCancel;
    }

    public UILabel getButtonSend() {
        return buttonSend;
    }

    public UITextField getReceiver() {
        return Receiver;
    }

    public UITextField getCash() {
        return Cash;
    }

    public class SendCashGuiListener implements Listener {

        @EventMethod
        public void onGuiKlickEvent(PlayerUIElementClickEvent event) {
            UIElement el = event.getUIElement();
            Player player = event.getPlayer();

            if (plugin.Config.Debug > 0) {
                Console.sendDebug("GuiClick", "el: " + el);
            }

            if (el == buttonCancel) {
                plugin.GUI.SendCashGui.hideGUI(player);
                plugin.unregisterEventListener(this);
            }

            if (el == findPlayer) {
                if (plugin.Config.Debug > 0) {
                    Console.sendDebug("onGuiKlickEvent", "buttonPlayer");
                }
                plugin.GUI.SelectOnlinePlayerGui.showGui(player, (sector, select, lastSelect) -> {
                    Receiver.setText(select.getPlayerObject().getUID());
                    plugin.GUI.SelectOnlinePlayerGui.hideGui(player);
                    if (plugin.Config.Debug > 0) {
                        Console.sendDebug("SendCashGuiListener", "Selected Player: " + select.getPlayerObject().getName());
                    }
                });

            }

            if (el == buttonSend) {
                Receiver.getCurrentText(player, (s) -> {
                    Player p2 = Server.getPlayerByUID(s);
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
