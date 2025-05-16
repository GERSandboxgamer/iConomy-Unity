/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.sbg.unity.iconomy.GUI.Banksystem;

import de.sbg.unity.iconomy.Banksystem.BankAccount;
import de.sbg.unity.iconomy.Banksystem.PlayerAccount;
import de.sbg.unity.iconomy.Exeptions.NotPlayerAccountExeption;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.style.FlexDirection;
import net.risingworld.api.ui.style.Font;
import net.risingworld.api.ui.style.TextAnchor;
import net.risingworld.api.ui.style.Unit;
import net.risingworld.api.utils.ColorRGBA;

/**
 *
 * @author patri
 */
public class AccountInfo extends MenuElement{
    
    private PlayerAccount playerAccount;
    private final iConomy plugin;
    private final icConsole Console;
    private final UILabel labBalanceValue, labMinBalanceValue, labOwnerNameValue, labOwnerUidValue;
    private final Player player;
    private final String lang;
    
    public AccountInfo(Player player, iConomy plugin) {
        this.plugin = plugin;
        this.Console = new icConsole(plugin);
        this.player = player;
        this.lang = player.getLanguage();
        
        this.style.flexDirection.set(FlexDirection.Column);
        this.setSize(100, 100, true);
               
        
        UILabel labTitel = new UILabel("Account Info");
        labTitel.setBorder(3);
        labTitel.setBorderColor(ColorRGBA.White.toIntRGBA());
        labTitel.setFontSize(28);
        labTitel.setFont(Font.DefaultBold);
        labTitel.style.width.set(100, Unit.Percent);
        labTitel.setTextAlign(TextAnchor.MiddleCenter);
        this.addChild(labTitel);
        
        UILabel labText = new UILabel(plugin.Language.getGui().getAccountInfoText(lang));
        labText.setFontSize(20);
        labText.style.width.set(100, Unit.Percent);
        labText.setTextAlign(TextAnchor.MiddleCenter);
        this.addChild(labText);
        
        UIElement panelTop = new UIElement();
        panelTop.setSize(100, 100, true);
        panelTop.style.flexDirection.set(FlexDirection.Row);
        panelTop.style.paddingTop.set(20, Unit.Pixel);
        
        this.addChild(panelTop);
        
        UIElement panelLift = new UIElement();   
        panelLift.setSize(50, 100, true);
        panelLift.style.flexDirection.set(FlexDirection.Column);
        panelLift.style.paddingRight.set(5, Unit.Pixel);
        panelTop.addChild(panelLift);
        
        UILabel labBalance = new UILabel(plugin.Language.getGui().getBalance(lang) + ":");
        labBalance.setFontSize(24);
        labBalance.style.width.set(100, Unit.Percent);
        labBalance.setTextAlign(TextAnchor.MiddleRight);
        panelLift.addChild(labBalance);
        
        UILabel labMinBalance = new UILabel(plugin.Language.getGui().getMinBalance(lang) + ":");
        labMinBalance.setFontSize(24);
        labMinBalance.style.width.set(100, Unit.Percent);
        labMinBalance.setTextAlign(TextAnchor.MiddleRight);
        panelLift.addChild(labMinBalance);
        
        UILabel labOwnerName = new UILabel(plugin.Language.getGui().getOwner(lang) + ":");
        labOwnerName.setFontSize(24);
        labOwnerName.style.width.set(100, Unit.Percent);
        labOwnerName.setTextAlign(TextAnchor.MiddleRight);
        panelLift.addChild(labOwnerName);
        
        UILabel labOwnerUid = new UILabel(plugin.Language.getGui().getOwner(lang) + " UID: ");
        labOwnerUid.setFontSize(24);
        labOwnerUid.style.width.set(100, Unit.Percent);
        labOwnerUid.setTextAlign(TextAnchor.MiddleRight);
        panelLift.addChild(labOwnerUid);
        
        
        UIElement panelRight = new UIElement();
        panelRight.setSize(50, 100, true);
        panelRight.style.flexDirection.set(FlexDirection.Column);
        
        panelTop.addChild(panelRight);
        
        labBalanceValue = new UILabel();
        labBalanceValue.setFontSize(24);
        labBalanceValue.setFont(Font.DefaultBold);
        labBalanceValue.style.width.set(100, Unit.Percent);
        labBalanceValue.setTextAlign(TextAnchor.MiddleLeft);
        panelRight.addChild(labBalanceValue);
        
        labMinBalanceValue = new UILabel();
        labMinBalanceValue.setFontSize(24);
        labMinBalanceValue.setFont(Font.DefaultBold);
        labMinBalanceValue.style.width.set(100, Unit.Percent);
        labMinBalanceValue.setTextAlign(TextAnchor.MiddleLeft);
        panelRight.addChild(labMinBalanceValue);
        
        labOwnerNameValue = new UILabel();
        labOwnerNameValue.setFontSize(24);
        labOwnerNameValue.setFont(Font.DefaultBold);
        labOwnerNameValue.style.width.set(100, Unit.Percent);
        labOwnerNameValue.setTextAlign(TextAnchor.MiddleLeft);
        panelRight.addChild(labOwnerNameValue);
        
        labOwnerUidValue = new UILabel();
        labOwnerUidValue.setFontSize(24);
        labOwnerUidValue.setFont(Font.DefaultBold);
        labOwnerUidValue.style.width.set(100, Unit.Percent);
        labOwnerUidValue.setTextAlign(TextAnchor.MiddleLeft);
        panelRight.addChild(labOwnerUidValue);
        
        if (plugin.Config.Debug > 0) {
            panelTop.setBorder(2);
            panelTop.setBorderColor(ColorRGBA.White.toIntRGBA());
            panelRight.setBorder(2);
            panelRight.setBorderColor(ColorRGBA.Green.toIntRGBA());
            panelLift.setBorder(2);
            panelLift.setBorderColor(ColorRGBA.Black.toIntRGBA());
        }
        
        
        
        
    }

    @Override
    public void setBankAccount(BankAccount bankAccount) {
        if (bankAccount instanceof PlayerAccount pa) {
            pa = playerAccount;
            
            labBalanceValue.setText(bankAccount.getMoneyAsFormatedString());
            labOwnerNameValue.setText(((PlayerAccount) bankAccount).getLastKnownOwnerName());
            labMinBalanceValue.setText(plugin.moneyFormat.getMoneyAsFormatedString(player, bankAccount.getMin()));
            labOwnerUidValue.setText(((PlayerAccount) bankAccount).getOwnerUID());     
            
        } else {
            try {
                throw new NotPlayerAccountExeption();
            } catch (NotPlayerAccountExeption ex) {
                Console.sendErr(ex.getMessage());
                for (StackTraceElement st : ex.getStackTrace()) {
                    Console.sendErr(st.toString());
                }
            }
        }
    }

    public PlayerAccount getPlayerAccount() {
        return playerAccount;
    }
    
    
    
}
