package de.sbg.unity.iconomy.GUI.Banksystem;

import de.sbg.unity.iconomy.Banksystem.BankAccount;
import de.sbg.unity.iconomy.Banksystem.BusinessAccount;
import de.sbg.unity.iconomy.Exeptions.NotBusinessAccountExeption;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.style.Pivot;
import net.risingworld.api.utils.ColorRGBA;


public class BusinessInfo extends MenuElement {
    
    private final iConomy plugin;
    private BusinessAccount factoryAccount;
    private final UILabel factoryname;
    private final UILabel cash;
    //TODO Evtl. Ownser einfügen!
    
    public BusinessInfo(iConomy plugin) {
        this.plugin = plugin;
        this.setPivot(Pivot.LowerRight);
        this.setSize(75, 80, true);
        this.setPosition(100, 0, true);
        this.setBackgroundColor(ColorRGBA.Blue.toIntRGBA());
        this.factoryname = new UILabel();
        this.cash = new UILabel();
        
        this.factoryname.setPosition(50, 5, true);
        this.factoryname.setFontColor(ColorRGBA.White.toIntRGBA());
        this.factoryname.setPivot(Pivot.UpperLeft);
        this.factoryname.setFontSize(20);
        
        this.cash.setPosition(50, 25, true);
        this.cash.setFontColor(ColorRGBA.White.toIntRGBA());
        this.cash.setPivot(Pivot.UpperLeft);
        this.cash.setFontSize(20);
        this.addChild(cash);
    }

    public BusinessAccount getBusinessAccount() {
        return factoryAccount;
    }
    
    @Override
    public void setBankAccount(BankAccount bankAccount) {
        if (bankAccount instanceof BusinessAccount fa) {
            this.factoryAccount = fa;
        } else {
            try {
                throw new NotBusinessAccountExeption();
            } catch (NotBusinessAccountExeption ex) {
                icConsole Console = new icConsole(plugin);
                Console.sendErr(ex.getMessage());
                for (StackTraceElement st : ex.getStackTrace()) {
                    Console.sendErr(st.toString());
                }
            }
        }
    }    

    public UILabel getCash() {
        return cash;
    }

    public UILabel getBusinessname() {
        return factoryname;
    }

    
    
}
