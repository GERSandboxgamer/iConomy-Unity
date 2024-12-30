package de.sbg.unity.iconomy.GUI.Banksystem;

import de.sbg.unity.iconomy.Utils.TextFormat;
import de.sbg.unity.iconomy.Utils.TransferResult;
import static de.sbg.unity.iconomy.Utils.TransferResult.EventCancel;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.assets.TextureAsset;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.ui.PlayerUIElementClickEvent;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.UITextField;
import net.risingworld.api.ui.style.Pivot;

public class CashInOutGUI {

    private final iConomy plugin;
    private final icConsole Console;
    private final TextFormat format;

    private final UIElement panel;

    private final UILabel butSend, butCancel, labCashPlayer, labBankPlayer, labAmount;
    private UILabel labTitel;
    private final UITextField txtCash;
    private final Modus modus;
    private final String lang;

    public CashInOutGUI(iConomy plugin, icConsole Console, Player player, Modus modus) {
        this.plugin = plugin;
        this.Console = Console;
        this.modus = modus;
        this.format = new TextFormat();
        this.lang = player.getLanguage();

        this.panel = new UIElement();
        panel.setPosition(50, 50, true);
        panel.setPivot(Pivot.MiddleCenter);
        panel.setSize(500, 500, false);
        //panel.setBackgroundColor(0, 0, 102, 1);
        
        TextureAsset image = TextureAsset.loadFromPlugin(plugin, "/resources/guiBackground1_Viereck.png");
        panel.style.backgroundImage.set(image);

        TitelBar();

        UILabel message;
        if (this.modus == Modus.In) {
            message = new UILabel(plugin.Language.getGui().getCashInOutGUI_In_Message(lang));
        } else {
            message = new UILabel(plugin.Language.getGui().getCashInOutGUI_Out_Message(lang));
        }
        message.setPosition(50, 15, true);
        message.setPivot(Pivot.UpperCenter);
        message.setSize(800, 50, false);
        message.setFontSize(25);
        panel.addChild(message);

        labCashPlayer = new UILabel(plugin.Language.getGui().getYourCash(lang) + " " + format.Bold(format.Color("yellow", plugin.CashSystem.getCashAsFormatedString(player))));
        labCashPlayer.setFontSize(25);
        labCashPlayer.setPosition(50, 35, true);
        labCashPlayer.setPivot(Pivot.UpperCenter);
        panel.addChild(labCashPlayer);

        labBankPlayer = new UILabel(plugin.Language.getGui().getYourBank(lang) + " " + format.Bold(format.Color("yellow", plugin.Bankystem.PlayerSystem.getPlayerAccount(player).getMoneyAsFormatedString())));
        labBankPlayer.setFontSize(25);
        labBankPlayer.setPosition(50, 45, true);
        labBankPlayer.setPivot(Pivot.UpperCenter);
        panel.addChild(labBankPlayer);

        this.labAmount = new UILabel(plugin.Language.getGui().getGUI_Amount(lang));
        labAmount.setPosition(5, 57, true);
        labAmount.setPivot(Pivot.UpperLeft);
        labAmount.setFontSize(25);
        panel.addChild(labAmount);

        this.txtCash = new UITextField();
        txtCash.setPosition(5, 65, true);
        txtCash.setSize(450, 50, false);
        txtCash.setFontSize(20);
        panel.addChild(txtCash);

        this.butSend = new UILabel(format.Bold(format.Color("green", "[" + plugin.Language.getGui().getSend(lang) + "]")));
        butSend.setPosition(95, 95, true);
        butSend.setPivot(Pivot.LowerRight);
        butSend.setClickable(true);
        butSend.setFontSize(25);
        panel.addChild(butSend);

        this.butCancel = new UILabel(format.Bold(format.Color("red", "[" + plugin.Language.getGui().getCancel(lang) + "]")));
        butCancel.setPosition(5, 95, true);
        butCancel.setPivot(Pivot.LowerLeft);
        butCancel.setFontSize(25);
        butCancel.setClickable(true);
        panel.addChild(butCancel);

        plugin.registerEventListener(new CashInOutGuiListener());
        player.addUIElement(panel);
    }

    private void TitelBar() {

        UIElement tielBar = new UIElement();
        tielBar.setPosition(50, 0, true);
        tielBar.setPivot(Pivot.UpperCenter);
        tielBar.setSize(500, 50, false);
        tielBar.setBackgroundColor(255, 153, 51, 1);

        if (modus == Modus.In) {
            this.labTitel = new UILabel(format.Bold(format.Underline("iConomy - Cash > Bank")));
        } else {
            this.labTitel = new UILabel(format.Bold(format.Underline("iConomy - Bank > Cash")));
        }
        labTitel.setPosition(50, 50, true);
        labTitel.setPivot(Pivot.MiddleCenter);
        labTitel.setFontSize(25);
        labTitel.setFontColor(255, 255, 255, 1);
        labTitel.setBackgroundColor(255, 153, 51, 1);
        tielBar.addChild(labTitel);
        panel.addChild(tielBar);
    }

    public UIElement getPanel() {
        return panel;
    }

    public UILabel getButCancel() {
        return butCancel;
    }

    public UILabel getButSend() {
        return butSend;
    }

    public Modus getModus() {
        return modus;
    }

    public UITextField getTxtCash() {
        return txtCash;
    }

    public enum Modus {
        In,
        Out;
    }

    public class CashInOutGuiListener implements Listener {

        @EventMethod
        public void onPlayerUIKlickEvent(PlayerUIElementClickEvent event) {
            Player player = event.getPlayer();
            UIElement el = event.getUIElement();

            if (el == butCancel) {
                plugin.GUI.Bankystem.CashInOutGui.hideGUI(player);
                plugin.unregisterEventListener(this);
            }

            if (el == butSend) {
                txtCash.getCurrentText(player, (s) -> {
                    if (!s.isBlank() && !s.isEmpty()) {
                        long l;
                        try {
                            l = plugin.moneyFormat.getMoneyAsLong(s);
                            if (l > 0) {
                                if (modus == Modus.In) {
                                    TransferResult tr = plugin.Bankystem.PlayerSystem.getPlayerAccount(player).cashIn(player, l);
                                    switch (tr) {
                                        case EventCancel -> {
                                            player.showErrorMessageBox("iConomy - Bank", plugin.Language.getStatus().getTransferCancel(lang));
                                        }
                                        case NotEnoughMoney -> {
                                            player.showErrorMessageBox("iConomy - Bank", plugin.Language.getStatus().getPlayerNotAnounthMoney(lang));
                                        }
                                        case Successful -> {
                                            plugin.GUI.Bankystem.CashInOutGui.hideGUI(player);
                                            String l1, l2;
                                            l1 = "Cash: " + plugin.CashSystem.getCashAsFormatedString(player) + format.Color("red", " (-" + plugin.moneyFormat.getMoneyAsFormatedString(player, l) + ")");
                                            l2 = "Bank: " + plugin.Bankystem.PlayerSystem.getPlayerAccount(player).getMoneyAsFormatedString() + format.Color("green", " (+" + plugin.moneyFormat.getMoneyAsFormatedString(player, l) + ")");
                                            plugin.GUI.MoneyInfoGui.showGUI(player, l1, l2);
                                        }
                                    }

                                } else {
                                    TransferResult tr = plugin.Bankystem.PlayerSystem.getPlayerAccount(player).cashOut(player, l);
                                    switch (tr) {
                                        case EventCancel -> {
                                            player.showErrorMessageBox("iConomy - Bank", plugin.Language.getStatus().getTransferCancel(lang));
                                        }
                                        case NotEnoughMoney -> {
                                            player.showErrorMessageBox("iConomy - Bank", plugin.Language.getStatus().getPlayerNotAnounthMoney(lang));
                                        }
                                        case Successful -> {
                                            plugin.GUI.Bankystem.CashInOutGui.hideGUI(player);
                                            String l1, l2;
                                            l1 = "Cash: " + plugin.CashSystem.getCashAsFormatedString(player) + format.Color("green", " (+" + plugin.moneyFormat.getMoneyAsFormatedString(player, l) + ")");
                                            l2 = "Bank: " + plugin.Bankystem.PlayerSystem.getPlayerAccount(player).getMoneyAsFormatedString() + format.Color("red", " (-" + plugin.moneyFormat.getMoneyAsFormatedString(player, l) + ")");
                                            plugin.GUI.MoneyInfoGui.showGUI(player, l1, l2);
                                        }
                                    }
                                }
                            } else {
                                player.showErrorMessageBox("Error", plugin.Language.getStatus().getAmountBigger(lang));
                            }
                        } catch (NumberFormatException ex) {
                            player.showErrorMessageBox("iConomy - Bank", plugin.Language.getStatus().getMoneyMustBeNumber(lang));
                        }
                    } else {
                        player.showErrorMessageBox("iConomy - Bank", plugin.Language.getStatus().getEmptyAmount(lang));
                    }
                });

            }
        }
    }

}
