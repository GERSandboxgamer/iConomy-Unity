package de.sbg.unity.iconomy.GUI;

import de.sbg.unity.iconomy.iConomy;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.style.Font;
import net.risingworld.api.ui.style.Pivot;
import net.risingworld.api.ui.style.TextAnchor;
import net.risingworld.api.utils.ColorRGBA;


public class SmallCashGUI extends UIElement{
    
    
    public SmallCashGUI(iConomy plugin, Player player) {
        
        this.setPivot(Pivot.UpperLeft);
        this.setPosition(1, 1, true);
        this.setSize(150, 25, false);
        this.setBackgroundColor(ColorRGBA.Blue.r, ColorRGBA.Blue.g, ColorRGBA.Blue.b,0.75f);
        
        UILabel lab = new UILabel();
        lab.setText(plugin.CashSystem.getCashAsFormatedString(player));
        lab.setTextAlign(TextAnchor.MiddleCenter);
        lab.setFontSize(16);
        lab.setFont(Font.DefaultBold);
        lab.setSize(100, 100, true);
        this.addChild(lab);
    }
    
}
