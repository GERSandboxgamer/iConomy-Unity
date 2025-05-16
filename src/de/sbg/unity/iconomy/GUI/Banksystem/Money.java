/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.sbg.unity.iconomy.GUI.Banksystem;

import de.chaoswg.gui.UIButton;
import de.sbg.unity.iconomy.Banksystem.BankAccount;
import de.sbg.unity.iconomy.Banksystem.BankMember;
import de.sbg.unity.iconomy.Banksystem.PlayerAccount;
import de.sbg.unity.iconomy.Events.Money.AddBankMoneyEvent;
import de.sbg.unity.iconomy.Utils.PlayerAccountPermission;
import de.sbg.unity.iconomy.Utils.TransferResult;
import static de.sbg.unity.iconomy.Utils.TransferResult.NotEnoughMoney;
import de.sbg.unity.iconomy.iConomy;
import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.Server;
import net.risingworld.api.assets.TextureAsset;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.ui.PlayerUIElementClickEvent;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.UITextField;
import net.risingworld.api.ui.style.FlexDirection;
import net.risingworld.api.ui.style.Font;
import net.risingworld.api.ui.style.Justify;
import net.risingworld.api.ui.style.Pivot;
import net.risingworld.api.ui.style.ScaleMode;
import net.risingworld.api.ui.style.TextAnchor;
import net.risingworld.api.ui.style.Unit;
import net.risingworld.api.utils.ColorRGBA;

public class Money extends MenuElement implements Listener {
    
    private final String lang;
    private final Player player;
    private final iConomy plugin;
    private final UIElement groupSendMony, titelBoxSendMoney, groupCash, titelBoxCash;
    private final UITextField txtSendMonyUID, txtSendMonyAmounth, txtCashAmounth;
    private final UILabel infoBank, infoCash;
    private final UIButton butCashIn, butCashOut, butSend;    
    
    private BankAccount bankAccount;
    private final UIElement findPlayer;
    
    public Money(Player player, iConomy plugin) {
        
        this.plugin = plugin;
        this.player = player;
        this.lang = player.getLanguage();
        int bc = 0x5dd1ff;
        
        this.style.flexDirection.set(FlexDirection.Column);
        this.setSize(100, 100, true);
        
        UILabel labTitel = new UILabel(plugin.Language.getGui().getMoney(lang));
        labTitel.setBorder(3);
        labTitel.setBorderColor(ColorRGBA.White.toIntRGBA());
        labTitel.setFontSize(28);
        labTitel.setFont(Font.DefaultBold);
        labTitel.style.width.set(100, Unit.Percent);
        labTitel.setTextAlign(TextAnchor.MiddleCenter);
        
        UILabel labText = new UILabel(plugin.Language.getGui().getMoneyText(lang));
        labText.setFontSize(20);
        labText.style.width.set(100, Unit.Percent);
        labText.setTextAlign(TextAnchor.MiddleCenter);

        //INFO
        UIElement titelBox1 = new UIElement();
        titelBox1.style.width.set(95, Unit.Percent);
        titelBox1.style.height.set(50, Unit.Pixel);
        titelBox1.style.marginLeft.set(2, Unit.Percent);
        titelBox1.style.marginRight.set(2, Unit.Percent);
        titelBox1.style.marginTop.set(5, Unit.Pixel);
        titelBox1.style.marginBottom.set(2, Unit.Pixel);
        
        UILabel infoLabel = new UILabel("== Info ==");
        infoLabel.setFont(Font.DefaultBold);
        infoLabel.setFontSize(28);
        infoLabel.setFontColor(ColorRGBA.White.toIntRGBA());
        infoLabel.setPivot(Pivot.MiddleCenter);
        infoLabel.setPosition(50, 50, true);
        titelBox1.addChild(infoLabel);
        
        UIElement groupInfo = new UIElement();
        groupInfo.style.flexDirection.set(FlexDirection.Column);
        groupInfo.style.width.set(95, Unit.Percent);
        groupInfo.style.height.set(100, Unit.Pixel);
        groupInfo.setBorder(1);
        groupInfo.setBorderColor(ColorRGBA.White.toIntRGBA());
        groupInfo.style.borderBottomLeftRadius.set(12, Unit.Pixel);
        groupInfo.style.borderBottomRightRadius.set(12, Unit.Pixel);
        groupInfo.style.borderTopLeftRadius.set(12, Unit.Pixel);
        groupInfo.style.borderTopRightRadius.set(12, Unit.Pixel);
        groupInfo.style.borderBottomWidth.set(1);
        groupInfo.style.borderTopWidth.set(1);
        groupInfo.style.borderLeftWidth.set(1);
        groupInfo.style.borderRightWidth.set(1);
        groupInfo.setBackgroundColor(0, 0, 102, 1);
        groupInfo.style.marginLeft.set(2, Unit.Percent);
        groupInfo.style.marginRight.set(2, Unit.Percent);
        groupInfo.style.paddingTop.set(10, Unit.Pixel);
        groupInfo.style.paddingLeft.set(10, Unit.Pixel);
        groupInfo.style.paddingBottom.set(10, Unit.Pixel);
        groupInfo.style.paddingRight.set(10, Unit.Pixel);
        
        infoBank = new UILabel("XXX.XX $");
        infoBank.setSize(100, 50, true);
        infoBank.setTextAlign(TextAnchor.MiddleCenter);
        infoBank.setFontSize(20);
        
        infoCash = new UILabel(plugin.Language.getGui().getYourCash(lang) + ": " + plugin.CashSystem.getCashAsFormatedString(player));
        infoCash.setSize(100, 50, true);
        infoCash.setTextAlign(TextAnchor.MiddleCenter);
        infoCash.setFontSize(20);
        
        groupInfo.addChild(infoBank);
        groupInfo.addChild(infoCash);
        //INFO

        //SEND MONEY
        titelBoxSendMoney = new UIElement();
        titelBoxSendMoney.style.width.set(95, Unit.Percent);
        titelBoxSendMoney.style.height.set(50, Unit.Pixel);
        titelBoxSendMoney.style.marginLeft.set(2, Unit.Percent);
        titelBoxSendMoney.style.marginRight.set(2, Unit.Percent);
        titelBoxSendMoney.style.marginTop.set(20, Unit.Pixel);
        titelBoxSendMoney.style.marginBottom.set(5, Unit.Pixel);
        
        UILabel titelSendMoney = new UILabel("== " + plugin.Language.getGui().getTransfer(lang) + " ==");
        titelSendMoney.setFont(Font.DefaultBold);
        titelSendMoney.setFontSize(28);
        titelSendMoney.setFontColor(ColorRGBA.White.toIntRGBA());
        titelSendMoney.setPivot(Pivot.MiddleCenter);
        titelSendMoney.setPosition(50, 50, true);
        titelBoxSendMoney.addChild(titelSendMoney);
        
        groupSendMony = new UIElement();
        groupSendMony.style.flexDirection.set(FlexDirection.Column);
        groupSendMony.style.width.set(95, Unit.Percent);
        groupSendMony.style.height.set(200, Unit.Pixel);
        groupSendMony.setBorder(1);
        groupSendMony.setBorderColor(ColorRGBA.White.toIntRGBA());
        groupSendMony.style.borderBottomLeftRadius.set(12, Unit.Pixel);
        groupSendMony.style.borderBottomRightRadius.set(12, Unit.Pixel);
        groupSendMony.style.borderTopLeftRadius.set(12, Unit.Pixel);
        groupSendMony.style.borderTopRightRadius.set(12, Unit.Pixel);
        groupSendMony.style.borderBottomWidth.set(1);
        groupSendMony.style.borderTopWidth.set(1);
        groupSendMony.style.borderLeftWidth.set(1);
        groupSendMony.style.borderRightWidth.set(1);
        groupSendMony.setBackgroundColor(0, 0, 102, 1);
        groupSendMony.style.marginLeft.set(2, Unit.Percent);
        groupSendMony.style.marginRight.set(2, Unit.Percent);
        groupSendMony.style.paddingTop.set(10, Unit.Pixel);
        groupSendMony.style.paddingLeft.set(10, Unit.Pixel);
        groupSendMony.style.paddingBottom.set(10, Unit.Pixel);
        groupSendMony.style.paddingRight.set(10, Unit.Pixel);

        // SendMoney: Oben
        UIElement gSMOben = new UIElement();
        gSMOben.style.width.set(100, Unit.Percent);
        gSMOben.style.borderBottomColor.set(ColorRGBA.White);
        gSMOben.style.borderBottomWidth.set(1);
        
        UILabel smLabel = new UILabel(plugin.Language.getGui().getTransferText(lang));
        smLabel.setFontSize(20);
        smLabel.setSize(100, 100, true);
        smLabel.setTextAlign(TextAnchor.MiddleCenter);
        
        gSMOben.addChild(smLabel);
        
        groupSendMony.addChild(gSMOben);

        // SendMoney: Mitte
        UIElement z1 = new UIElement();
        z1.style.flexDirection.set(FlexDirection.Row);
        z1.style.width.set(100, Unit.Percent);
        z1.style.marginTop.set(10, Unit.Pixel);
        
        UILabel labSendMoneyUID = new UILabel("UID:");
        labSendMoneyUID.setFontSize(20);
        z1.addChild(labSendMoneyUID);
        
        UIElement z2 = new UIElement();
        z2.style.flexDirection.set(FlexDirection.Row);
        z2.style.width.set(100, Unit.Percent);
        
        txtSendMonyUID = new UITextField();
        txtSendMonyUID.style.width.set(50, Unit.Percent);
        txtSendMonyUID.style.marginRight.set(10, Unit.Pixel);
        txtSendMonyUID.setFontSize(20);
        z2.addChild(txtSendMonyUID);
        
        findPlayer = new UIElement();
        findPlayer.style.backgroundImage.set(TextureAsset.loadFromPlugin(plugin, "/resources/Suche.png"));
        findPlayer.setSize(32, 32, false);
        findPlayer.setClickable(true);
        findPlayer.style.backgroundImageScaleMode.set(ScaleMode.ScaleToFit);
        z2.addChild(findPlayer);
        
        UIElement z3 = new UIElement();
        z3.style.flexDirection.set(FlexDirection.Row);
        z3.style.width.set(100, Unit.Percent);
        z3.style.marginTop.set(10, Unit.Pixel);
        
        UILabel labSendMoneyAmounth = new UILabel(plugin.Language.getGui().getGUI_Amount(lang) + ":");
        labSendMoneyAmounth.setFontSize(20);
        z3.addChild(labSendMoneyAmounth);
        
        UIElement z4 = new UIElement();
        z4.style.flexDirection.set(FlexDirection.Row);
        z4.style.width.set(100, Unit.Percent);
        
        txtSendMonyAmounth = new UITextField();
        txtSendMonyAmounth.style.width.set(50, Unit.Percent);
        txtSendMonyAmounth.setFontSize(20);
        txtSendMonyAmounth.style.marginRight.set(10, Unit.Pixel);
        z4.addChild(txtSendMonyAmounth);
        
        butSend = new UIButton(plugin.Language.getGui().getSend(lang));
        butSend.setFontSize(20);
        butSend.style.height.set(100, Unit.Percent);
        butSend.style.width.set(150, Unit.Pixel);
        butSend.setTextAlign(TextAnchor.MiddleCenter);
        butSend.setBackgroundColor(bc);
        butSend.style.marginBottom.set(5, Unit.Pixel);
        z4.addChild(butSend);
        
        groupSendMony.addChild(z1);
        groupSendMony.addChild(z2);
        groupSendMony.addChild(z3);
        groupSendMony.addChild(z4);
        //SEND MONEY ENDE

        //CASH
        titelBoxCash = new UIElement();
        titelBoxCash.style.width.set(95, Unit.Percent);
        titelBoxCash.style.height.set(50, Unit.Pixel);
        titelBoxCash.style.marginLeft.set(2, Unit.Percent);
        titelBoxCash.style.marginRight.set(2, Unit.Percent);
        titelBoxCash.style.marginTop.set(20, Unit.Pixel);
        titelBoxCash.style.marginBottom.set(5, Unit.Pixel);
        
        UILabel titelCash = new UILabel("== " + plugin.Language.getGui().getCash(lang) + " ==");
        titelCash.setFont(Font.DefaultBold);
        titelCash.setFontSize(28);
        titelCash.setFontColor(ColorRGBA.White.toIntRGBA());
        titelCash.setPivot(Pivot.MiddleCenter);
        titelCash.setPosition(50, 50, true);
        titelBoxCash.addChild(titelCash);
        
        groupCash = new UIElement();
        groupCash.style.flexDirection.set(FlexDirection.Row);
        groupCash.style.width.set(95, Unit.Percent);
        groupCash.style.height.set(100, Unit.Pixel);
        groupCash.setBorder(1);
        groupCash.setBorderColor(ColorRGBA.White.toIntRGBA());
        groupCash.style.borderBottomLeftRadius.set(12, Unit.Pixel);
        groupCash.style.borderBottomRightRadius.set(12, Unit.Pixel);
        groupCash.style.borderTopLeftRadius.set(12, Unit.Pixel);
        groupCash.style.borderTopRightRadius.set(12, Unit.Pixel);
        groupCash.style.borderBottomWidth.set(1);
        groupCash.style.borderTopWidth.set(1);
        groupCash.style.borderLeftWidth.set(1);
        groupCash.style.borderRightWidth.set(1);
        groupCash.setBackgroundColor(0, 0, 102, 1);
        groupCash.style.marginLeft.set(2, Unit.Percent);
        groupCash.style.marginRight.set(2, Unit.Percent);
        groupCash.style.paddingTop.set(10, Unit.Pixel);
        groupCash.style.paddingLeft.set(10, Unit.Pixel);
        groupCash.style.paddingBottom.set(10, Unit.Pixel);
        groupCash.style.paddingRight.set(10, Unit.Pixel);

        //Links
        UIElement cashLeft = new UIElement();
        cashLeft.style.flexDirection.set(FlexDirection.Column);
        cashLeft.style.width.set(50, Unit.Percent);
        groupCash.addChild(cashLeft);
        
        UILabel labCash = new UILabel(plugin.Language.getGui().getGUI_Amount(lang) + ":");
        labCash.setFontSize(20);
        cashLeft.addChild(labCash);
        
        txtCashAmounth = new UITextField();
        txtCashAmounth.style.width.set(100, Unit.Percent);
        txtCashAmounth.setFontSize(20);
        cashLeft.addChild(txtCashAmounth);

        //Rechts
        UIElement cashRight = new UIElement();
        cashRight.style.flexDirection.set(FlexDirection.Column);
        cashRight.style.justifyContent.set(Justify.SpaceAround);
        cashRight.style.marginLeft.set(10, Unit.Pixel);
        
        cashRight.style.width.set(50, Unit.Percent);
        groupCash.addChild(cashRight);
        
        butCashIn = new UIButton("Cash IN");
        //butCashIn.setSize(50, 48, true);
        butCashIn.style.width.set(150, Unit.Pixel);
        butCashIn.style.height.set(50, Unit.Percent);
        butCashIn.setFontSize(20);
        butCashIn.setTextAlign(TextAnchor.MiddleCenter);
        butCashIn.setBackgroundColor(bc);
        butCashIn.style.marginBottom.set(5, Unit.Pixel);
        cashRight.addChild(butCashIn);
        
        butCashOut = new UIButton("Cash OUT");
        butCashOut.style.width.set(150, Unit.Pixel);
        butCashOut.style.height.set(50, Unit.Percent);
        butCashOut.setFontSize(20);
        butCashOut.setTextAlign(TextAnchor.MiddleCenter);
        butCashOut.setBackgroundColor(bc);
        cashRight.addChild(butCashOut);
        
        this.addChild(labTitel);
        this.addChild(labText);
        this.addChild(titelBox1);
        this.addChild(groupInfo);
        this.addChild(titelBoxSendMoney);
        this.addChild(groupSendMony);
        this.addChild(titelBoxCash);
        this.addChild(groupCash);
        
        if (plugin.Config.Debug > 0) {
            gSMOben.setBorder(1);
            gSMOben.setBorderColor(ColorRGBA.White.toIntRGBA());
            smLabel.setBackgroundColor(ColorRGBA.Black.toIntRGBA());
            z1.setBorder(1);
            z1.setBorderColor(ColorRGBA.Red.toIntRGBA());
            z2.setBorder(1);
            z2.setBorderColor(ColorRGBA.Red.toIntRGBA());
            z3.setBorder(1);
            z3.setBorderColor(ColorRGBA.Red.toIntRGBA());
            z4.setBorder(1);
            z4.setBorderColor(ColorRGBA.Red.toIntRGBA());
        }
        updatePermission();
    }
    
    public void updateInfo() {
        infoBank.setText("Bank: " + bankAccount.getMoneyAsFormatedString());
        infoCash.setText(plugin.Language.getGui().getYourCash(lang) + ": " + plugin.CashSystem.getCashAsFormatedString(player));
    }
    
    public final void updatePermission() {
        if (bankAccount instanceof PlayerAccount pa) {
            if (pa.isMember(player)) {
                BankMember bm = pa.getMember(player);
                boolean cashAdd = true;
                boolean cashRemove = true;
                boolean sendMoney = true;
                if (!bm.hasPermission(PlayerAccountPermission.ADD_CASH)) {
                    cashAdd = false;
                }
                
                if (!bm.hasPermission(PlayerAccountPermission.REMOVE_CASH)) {
                    cashRemove = false;
                }
                
                if (!bm.hasPermission(PlayerAccountPermission.SEND_MONEY)) {
                    sendMoney = false;
                }
                titelBoxCash.setVisible((cashAdd | cashRemove));
                groupCash.setVisible((cashAdd | cashRemove));
                
                titelBoxSendMoney.setVisible(sendMoney);
                groupSendMony.setVisible(sendMoney);
                
                
            }
        }
    }
    
    public UIButton getButSend() {
        return butSend;
    }
    
    public UIButton getButCashIn() {
        return butCashIn;
    }
    
    public UIButton getButCashOut() {
        return butCashOut;
    }
    
    public UIElement getGroupCash() {
        return groupCash;
    }
    
    public UIElement getGroupSendMony() {
        return groupSendMony;
    }
    
    public UIElement getFindPlayer() {
        return findPlayer;
    }
    
    public UILabel getInfoBank() {
        return infoBank;
    }
    
    public BankAccount getBankAccount() {
        return bankAccount;
    }
    
    @Override
    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
        infoBank.setText("Bank: " + bankAccount.getMoneyAsFormatedString());
    }
    
    @EventMethod
    public void onPlayerClickButtonEvent(PlayerUIElementClickEvent event) {
        Player p = event.getPlayer();
        UIElement el = event.getUIElement();
        
        if (el == findPlayer) {
            plugin.GUI.SelectOnlinePlayerGui.showGui(p, (selector, select, lastSelected) -> {
                txtSendMonyUID.setText(select.getPlayerObject().getUID());
                plugin.GUI.SelectOnlinePlayerGui.hideGui(p);
            });
        }
        if (el == butSend) {
            
            txtSendMonyAmounth.getCurrentText(p, (amounth) -> {
                txtSendMonyUID.getCurrentText(p, (uid) -> {
                    try {
                        BankAccount empf = plugin.Bankystem.PlayerSystem.getPlayerAccount(uid);
                        
                        long money = plugin.moneyFormat.getMoneyAsLong(amounth);
                        TransferResult tr = bankAccount.removeMoney(empf, money);
                        
                        switch (tr) {
                            case Successful -> {
                                TransferResult empfRes = empf.addMoney(bankAccount, money);
                                switch (empfRes) {
                                    case Successful -> {
                                        player.showInfoMessageBox("Money Send", plugin.Language.getStatus().getSendMoneyOK(lang));
                                        txtSendMonyUID.setText("");
                                        txtSendMonyAmounth.setText("");
                                        updateInfo();
                                        Player empfänger = Server.getPlayerByUID(uid);
                                        MainGUI gui = plugin.GUI.Bankystem.MainGui.getGui(empfänger);
                                        if (gui != null) {
                                            gui.getMoney().updateInfo();
                                        }
                                    }
                                    case EventCancel -> {
                                        bankAccount.addMoney(player, money, AddBankMoneyEvent.Reason.Error, "Transfer Cancelt");
                                        p.showErrorMessageBox("Send Money Error",  plugin.Language.getStatus().getTransferCancel(lang));
                                    }
                                    case NoBankAccount -> {
                                        bankAccount.addMoney(player, money, AddBankMoneyEvent.Reason.Error, "Player has no bank account");
                                        p.showErrorMessageBox("Send Money Error", plugin.Language.getStatus().getPlayerHasNoAccount(lang));
                                    }
                                    case PlayerNotConnected -> {
                                        bankAccount.addMoney(player, money, AddBankMoneyEvent.Reason.Error, "Player not connected");
                                        p.showErrorMessageBox("Send Money Error", plugin.Language.getStatus().getPlayerNotConnected(lang));
                                    }
                                    case PlayerNotExist -> {
                                        bankAccount.addMoney(player, money, AddBankMoneyEvent.Reason.Error, "Player not exist");
                                        p.showErrorMessageBox("Send Money Error", plugin.Language.getStatus().getPlayerNotExist(lang));
                                    }
                                }
                                
                            }
                            case NotEnoughMoney -> {
                                p.showErrorMessageBox("Send Money Error", plugin.Language.getStatus().getPlayerNotAnounthMoney(lang));
                            }
                            case MoneyFormat -> {
                                p.showErrorMessageBox("Send Money Error", plugin.Language.getStatus().getMoneyMustBeNumber(lang));
                            }
                        }
                    } catch (NumberFormatException ex) {
                        player.showErrorMessageBox("Money format error", plugin.Language.getStatus().getMoneyMustBeNumber(lang));
                    }
                });
            });
        }
        
        if (el == butCashIn) {
            
            txtCashAmounth.getCurrentText(p, (cString) -> {
                try {
                    long money = plugin.moneyFormat.getMoneyAsLong(cString);
                    TransferResult tr = bankAccount.cashIn(p, money);
                    
                    switch (tr) {
                        case Successful -> {
                            p.showInfoMessageBox("Cash In", "Add cash successful to bank account!"); //TODO Lang
                            txtCashAmounth.setText("");
                            updateInfo();
                        }
                        case NotEnoughMoney -> {
                            p.showErrorMessageBox("Cash In Error", plugin.Language.getStatus().getPlayerNotAnounthMoney(lang));
                        }
                        case EventCancel -> {
                            p.showErrorMessageBox("Cash In Error", plugin.Language.getStatus().getTransferCancel(lang));
                        }
                    }
                    
                } catch (NumberFormatException ex) {
                    p.showErrorMessageBox("Cash In Error", plugin.Language.getStatus().getMoneyMustBeNumber(lang));
                }
            });
            
        }
        if (el == butCashOut) {
            txtCashAmounth.getCurrentText(p, (cash) -> {
                try {
                    long money = plugin.moneyFormat.getMoneyAsLong(cash);
                    TransferResult tr = bankAccount.cashOut(p, money);
                    
                    switch (tr) {
                        case Successful -> {
                            p.showInfoMessageBox("Cash Out", "Remove cash successful from bank account!"); //TODO Lang
                            txtCashAmounth.setText("");
                            updateInfo();
                        }
                        case NotEnoughMoney -> {
                            p.showErrorMessageBox("Cash In Error", plugin.Language.getStatus().getPlayerNotAnounthMoney(lang));
                        }
                        case EventCancel -> {
                            p.showErrorMessageBox("Cash In Error", plugin.Language.getStatus().getTransferCancel(lang));
                        }
                    }
                    
                } catch (NumberFormatException ex) {
                    p.showErrorMessageBox("Cash In Error", plugin.Language.getStatus().getMoneyMustBeNumber(lang));
                }
            });
        }
        
    }
    
}
