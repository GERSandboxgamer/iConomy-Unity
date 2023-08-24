package de.sbg.unity.iconomy.GUI;

import de.sbg.unity.iconomy.Events.Money.AddBankMoneyEvent;
import de.sbg.unity.iconomy.Events.Money.AddCashEvent;
import de.sbg.unity.iconomy.Events.Money.RemoveCashEvent;
import de.sbg.unity.iconomy.Utils.TextFormat;
import de.sbg.unity.iconomy.Utils.TransferResult;
import static de.sbg.unity.iconomy.Utils.TransferResult.EventCancel;
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

public class CashInOutGUI {

    private final iConomy plugin;
    private final icConsole Console;
    private final TextFormat format;

    private final UIElement panel;

    private final UILabel butSend, butCancel, labAmounth, /*labCashPlayer, labBankPlayer,*/ labTitle;
    private final UITextField txtCash;
    private final Modus modus;

    public CashInOutGUI(iConomy plugin, icConsole Console, Player player, Modus modus) {
        this.plugin = plugin;
        this.Console = Console;
        this.modus = modus;
        this.format = new TextFormat();

        this.panel = new UIElement();
        panel.setPosition(50, 50, true);
        panel.setPivot(Pivot.MiddleCenter);
        panel.setSize(500, 500, false);
        panel.setBackgroundColor(0, 0, 102, 1);

        if (modus == Modus.In) {
            this.labTitle = new UILabel(format.Bold(format.Underline("iConomy - Cash to Bank")));
        } else {
            this.labTitle = new UILabel(format.Bold(format.Underline("iConomy - Bank to Cash")));
        }
        labTitle.setPosition(50, 2, true);
        labTitle.setPivot(Pivot.UpperCenter);

        this.labAmounth = new UILabel("Amounth:");
        labAmounth.setPosition(5, 45, true);

        this.txtCash = new UITextField();
        txtCash.setPosition(50, 50, true);
        txtCash.setSize(500, 50, false);
        txtCash.setFontSize(20);

        this.butSend = new UILabel(format.Bold(format.Color("green", "[Send]"))); //TODO Translate
        butSend.setPosition(95, 98, true);

        this.butCancel = new UILabel(format.Bold(format.Color("red", "[Cancel]"))); //TODO Translate
        butCancel.setPosition(5, 98, true);

        plugin.registerEventListener(new CashInOutGuiListener());
        player.addUIElement(panel);
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
                plugin.GUI.CashInOutGui.hideGUI(player);
                plugin.unregisterEventListener(this);
            }

            if (el == butSend) {
                txtCash.getCurrentText(player, (s) -> {
                    if (!s.isBlank() && !s.isEmpty()) {
                        long l;
                        try {
                            l = plugin.moneyFormat.getMoneyAsLong(s);
                            if (modus == Modus.In) {
                                TransferResult tr = plugin.CashSystem.removeCash(player, l, RemoveCashEvent.Reason.CashToBank);
                                switch (tr) {
                                    case EventCancel -> {
                                        
                                    }
                                    case NotEnoughMoney -> {
                                        player.showErrorMessageBox("iConomy - Bank", "You do not have anouth money!");//TODO Translate
                                    }
                                    case Successful -> {
                                        plugin.Bankystem.PlayerSystem.getPlayerAccount(player).cashIn(player, l);
                                    }
                                }
                            } else {
                                TransferResult tr = plugin.Bankystem.PlayerSystem.getPlayerAccount(player).cashOut(player, l);
                                switch (tr) {
                                    case EventCancel -> {
                                        
                                    }
                                    case NotEnoughMoney -> {
                                        player.showErrorMessageBox("iConomy - Bank", "You do not have anouth money!");//TODO Translate
                                    }
                                    case Successful -> {
                                        plugin.CashSystem.addCash(player, l, AddCashEvent.Reason.BankToCash);
                                    }
                                }
                            }
                        } catch (NumberFormatException ex) {
                            player.showErrorMessageBox("iConomy - Bank", "The format of the money is wrong!");//TODO Translate
                        }
                    } else {
                        player.showErrorMessageBox("iConomy - Bank", "The amounth can not be empty!");//TODO Translate
                    }
                });

            }
        }
    }

}
