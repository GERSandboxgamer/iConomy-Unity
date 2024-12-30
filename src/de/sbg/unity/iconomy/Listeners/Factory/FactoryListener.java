package de.sbg.unity.iconomy.Listeners.Factory;

import de.sbg.unity.iconomy.Factory.Factory;
import de.sbg.unity.iconomy.Factory.Factory.FactoryInfoMessage;
import de.sbg.unity.iconomy.iConomy;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerCommandEvent;
import net.risingworld.api.events.player.PlayerEnterAreaEvent;
import net.risingworld.api.events.player.PlayerLeaveAreaEvent;
import net.risingworld.api.objects.Area;
import net.risingworld.api.objects.Player;

public class FactoryListener implements Listener {

    private final iConomy plugin;

    public FactoryListener(iConomy plugin) {
        this.plugin = plugin;
    }

    @EventMethod
    public void onPlayerEnterFactoryPlot(PlayerEnterAreaEvent event) {
        Player player = event.getPlayer();
        Area area = event.getArea();
        if (plugin.Factory.isFactoryPlot(area)) {
            Factory f = plugin.Factory.getFactoryByPlot(player);
            if (!f.isOwner(player) && !(f.isMember(player) && f.getMember(player).getPermissions().EnterPlots) && !f.getPlayerPermissions().EnterPlots) {
                event.setCancelled(true);
                player.showStatusMessage("Du darfst das Firmengelände nicht betreten!", 5); //TODOD Lang
            } else {
                player.showLocationTicker("Firma: " + f.getName(), 5); //TODO Lang
                f.addInfo(new FactoryInfoMessage(player).PlayerEnterPlot);
                String msg;
                if (f.isMember(player)) {
                    msg = f.getMember(player).getPermissions().EnterMessage;
                    if (msg != null) {
                        player.sendTextMessage(f.getName() + ": " + msg);
                    }
                } else if (!f.isOwner(player)) {
                    msg = f.getPlayerPermissions().EnterMessage;
                    if (msg != null) {
                        player.sendTextMessage(f.getName() + ": " + msg);
                    }
                }
                
            }
        }
    }
    
    @EventMethod
    public void onPlayerLeaveFactoryPlot(PlayerLeaveAreaEvent event) {
        Player player = event.getPlayer();
        Area area = event.getArea();
       
    }
    
    @EventMethod
    public void onPlayerFactoryCommandEvent(PlayerCommandEvent event) {
        Player player = event.getPlayer();
        String[] cmd = event.getCommand().split(" ");
        
        if (cmd.length >= 1) {
            if (cmd[0].toLowerCase().equals("/factory") || cmd[0].toLowerCase().equals("/icf") || cmd[0].toLowerCase().equals("/fa")) {
                if (cmd.length == 1) {
                    Factory f = plugin.Factory.getFactoryByPlot(player);
                    if (f != null) {
                        //TODO Factory GUI
                    }
                }
                if (cmd.length == 2) {
                    if (cmd[1].toLowerCase().equals("help")){
                        
                    }
                    if (cmd[1].toLowerCase().equals("addplot")){
                        if (player.isAdmin()) {
                            
                        }
                    }
                }
                if (cmd.length == 3) {
                    if (cmd[1].toLowerCase().equals("create")){
                        String faName = cmd[2];
                        
                        if (!plugin.Config.FactoryPlotByAdmin || plugin.Factory.isFactoryPlot(player.getCurrentArea()) || player.isAdmin()) {
                            
                        }
                    }
                }
            }
        }
        
    }
           

}
