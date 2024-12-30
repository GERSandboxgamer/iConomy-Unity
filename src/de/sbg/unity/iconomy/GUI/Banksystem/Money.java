/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.sbg.unity.iconomy.GUI.Banksystem;

import de.sbg.unity.iconomy.Banksystem.BankAccount;
import de.sbg.unity.iconomy.Banksystem.FactoryAccount;
import de.sbg.unity.iconomy.Banksystem.PlayerAccount;
import de.sbg.unity.iconomy.iConomy;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.ui.PlayerUIElementClickEvent;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.style.FlexDirection;
import net.risingworld.api.ui.style.Font;
import net.risingworld.api.ui.style.Pivot;
import net.risingworld.api.ui.style.TextAnchor;
import net.risingworld.api.ui.style.Unit;
import net.risingworld.api.utils.ColorRGBA;

public class Money extends MenuElement implements Listener {

    private final String lang;
    private final Player player;
    private final iConomy plugin;
    private final UIElement groupSendMony, titelBoxSendMoney, groupCash;
    private final UILabel infoBank;

    private BankAccount selectedAccount;

    private BankAccount bankAccount;

    public Money(Player player, iConomy plugin) {

        this.plugin = plugin;
        this.player = player;
        this.lang = player.getLanguage();

        this.style.flexDirection.set(FlexDirection.Column);
        this.setSize(100, 100, true);

        UIElement titelBox = new UIElement();

        UILabel labTitel = new UILabel("Money");
        labTitel.setBorder(3);
        labTitel.setBorderColor(ColorRGBA.White.toIntRGBA());
        labTitel.setFontSize(28);
        labTitel.setFont(Font.DefaultBold);
        labTitel.style.width.set(100, Unit.Percent);
        labTitel.setTextAlign(TextAnchor.MiddleCenter);

        UILabel labText = new UILabel("Hier wird die Beschreibung stehen!"); //TODO Beschreibung
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
        groupInfo.style.marginTop.set(5, Unit.Pixel);
        groupInfo.style.marginLeft.set(2, Unit.Percent);
        groupInfo.style.marginRight.set(2, Unit.Percent);

        infoBank = new UILabel("XXX.XX $");
        infoBank.setSize(100, 50, true);
        infoBank.setTextAlign(TextAnchor.MiddleCenter);
        infoBank.setFontSize(20);

        UILabel infoCash = new UILabel("Your Cash: " + plugin.CashSystem.getCashAsFormatedString(player));
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

        UILabel titelSendMoney = new UILabel("== Überweisung ==");
        titelSendMoney.setFont(Font.DefaultBold);
        titelSendMoney.setFontSize(28);
        titelSendMoney.setFontColor(ColorRGBA.White.toIntRGBA());
        titelSendMoney.setPivot(Pivot.MiddleCenter);
        titelSendMoney.setPosition(50, 50, true);
        titelBoxSendMoney.addChild(titelSendMoney);
        
        groupSendMony = new UIElement();
        groupSendMony.style.flexDirection.set(FlexDirection.Column);
        groupSendMony.style.width.set(95, Unit.Percent);
        groupSendMony.style.height.set(100, Unit.Pixel);
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
        groupSendMony.style.marginTop.set(5, Unit.Pixel);
        groupSendMony.style.marginLeft.set(2, Unit.Percent);
        groupSendMony.style.marginRight.set(2, Unit.Percent);
        
        // SendMoney: Oben
        UIElement gSMOben = new UIElement();
        gSMOben.style.width.set(100, Unit.Percent);
        gSMOben.setBorder(1);
        gSMOben.setBorderColor(ColorRGBA.White.toIntRGBA());
        
        
        UILabel smLabel = new UILabel("Überweise Geld an einen anderen Spieler");
        smLabel.setFontSize(20);
        smLabel.setSize(100, 100, true);
        smLabel.setTextAlign(TextAnchor.MiddleLeft);
        smLabel.setBackgroundColor(ColorRGBA.Black.toIntRGBA());
        gSMOben.addChild(smLabel);
        
        groupSendMony.addChild(gSMOben);
        
        
        // SendMoney: Unten
        UIElement gSMUnten = new UIElement();
        gSMUnten.style.flexDirection.set(FlexDirection.Row);
        gSMUnten.setBorder(1);
        gSMUnten.setBorderColor(ColorRGBA.Red.toIntRGBA());
        
        // SendMoney: Unten - Links
        UIElement gSMULinks = new UIElement();
        
        
        gSMUnten.addChild(gSMULinks);
        
        // SendMoney: Unten - Rechts
        UIElement gSMURechts = new UIElement();
        gSMUnten.addChild(gSMURechts);

        groupSendMony.addChild(gSMUnten);
        //SEND MONEY ENDE

        //CASH
        groupCash = new UIElement();

        this.addChild(labTitel);
        this.addChild(labText);
        this.addChild(titelBox1);
        this.addChild(groupInfo);
        this.addChild(titelBoxSendMoney);
        this.addChild(groupSendMony);
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    @Override
    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
        infoBank.setText("Bank: " + bankAccount.getMoneyAsFormatedString());
        if (bankAccount instanceof PlayerAccount pa) {

        } else if (bankAccount instanceof FactoryAccount fa) {

        }
    }

    @EventMethod
    public void onPlayerUIElementClickEvent(PlayerUIElementClickEvent event) {

    }

}
