package de.sbg.unity.iconomy.GUI;

import de.sbg.unity.iconomy.iConomy;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.style.Pivot;
import net.risingworld.api.utils.ColorRGBA;


public class MoneyInfoMiniGUI extends UIElement{
    
    
    public MoneyInfoMiniGUI(iConomy plugin, Player player) {
        this.setBackgroundColor(ColorRGBA.Black.toIntRGBA()); //TODO Ändern zu Rising World GUI Background
        
        UILabel cash = new UILabel("Cash: " + plugin.Bankystem.PlayerSystem.getPlayerAccount(player).getMoneyAsFormatedString());
        cash.setFontSize(12);
        cash.setFontColor(ColorRGBA.White.toIntRGBA());
        this.addChild(this);
        this.setPosition(0, 50, true);
        this.setPivot(Pivot.UpperCenter);
    }
    
}
