package de.sbg.unity.iconomy.GUI;

import de.sbg.unity.iconomy.GUI.List.AccountList;
import de.sbg.unity.iconomy.iConomy;
import net.risingworld.api.objects.Player;
import net.risingworld.api.ui.UIElement;

public class SelectAccountGUI {
    

    private final Player player;
    private final iConomy plugin;
    private final UIElement window;
    
    public SelectAccountGUI(Player player, String titelText, iConomy plugin, AccountList.SelectCallback cb){
        this.player = player;
        this.plugin = plugin;
        
        
        window = new UIElement();
        
        AccountList list = new AccountList(player, true, false, plugin, cb);
        
    }

    public UIElement getWindow() {
        return window;
    }

    
    
    
}
