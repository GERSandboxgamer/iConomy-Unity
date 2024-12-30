package de.sbg.unity.iconomy.GUI.Banksystem;

import de.sbg.unity.iconomy.Banksystem.BankAccount;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.utils.ColorRGBA;

public abstract class MenuElement extends UIElement {

    public abstract void setBankAccount(BankAccount bankAccount);

    public MenuElement() {
        this.setBorder(1);
        //this.setBorderColor(ColorRGBA.White.toIntRGBA());
        
        this.style.borderTopColor.set(ColorRGBA.Clear);
        this.style.borderLeftColor.set(ColorRGBA.White);
        this.style.borderRightColor.set(ColorRGBA.Clear);
        this.style.borderBottomColor.set(ColorRGBA.Clear);
    }
}
