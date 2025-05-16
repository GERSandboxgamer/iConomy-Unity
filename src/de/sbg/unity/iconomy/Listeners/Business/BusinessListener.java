package de.sbg.unity.iconomy.Listeners.Business;


import de.sbg.unity.iconomy.iConomy;
import net.risingworld.api.events.Listener;

public class BusinessListener implements Listener {

    private final iConomy plugin;

    public BusinessListener(iConomy plugin) {
        this.plugin = plugin;
    }

//    @EventMethod
//    public void onPlayerEnterBusinessPlot(PlayerEnterAreaEvent event) {
//        Player player = event.getPlayer();
//        Area area = event.getArea();
//        if (plugin.Business.isBusinessPlot(area)) {
//            Business b = plugin.Business.getBusinessByPlot(player);
//            if (!b.isOwner(player) && !(b.isMember(player) && b.getMember(player).hasPermission(BusinessPermission.ENTER_PLOTS)) && !b.getPlayerPermissions().EnterPlots) {
//                event.setCancelled(true);
//                player.showStatusMessage("Du darfst das Firmengelände nicht betreten!", 5); //TODOD Lang
//            } else {
//                player.showLocationTicker("Firma: " + b.getName(), 5); //TODO Lang
//                b.addInfo(new BusinessInfoMessage(player).PlayerEnterPlot);
//                String msg;
//                if (b.isMember(player)) {
//                    msg = b.getMember(player).getPermissions().EnterMessage;
//                    if (msg != null) {
//                        player.sendTextMessage(b.getName() + ": " + msg);
//                    }
//                } else if (!b.isOwner(player)) {
//                    msg = b.getPlayerPermissions().EnterMessage;
//                    if (msg != null) {
//                        player.sendTextMessage(b.getName() + ": " + msg);
//                    }
//                }
//                
//            }
//        }
//    }
    
//    @EventMethod
//    public void onPlayerLeaveBusinessPlot(PlayerLeaveAreaEvent event) {
//        Player player = event.getPlayer();
//        Area area = event.getArea();
//       
//    }
    
//    @EventMethod
//    public void onPlayerBusinessCommandEvent(PlayerCommandEvent event) {
//        Player player = event.getPlayer();
//        String[] cmd = event.getCommand().split(" ");
//
//        if (cmd.length >= 1) {
//            if (cmd[0].toLowerCase().equals("/business") || cmd[0].toLowerCase().equals("/icb") || cmd[0].toLowerCase().equals("/b")) {
//                if (cmd.length == 1) {
//                    Business f = plugin.Business.getBusinessByPlot(player);
//                    if (f != null) {
//                        //TODO Business GUI
//                    }
//                }
//                if (cmd.length == 2) {
//                    if (cmd[1].toLowerCase().equals("help")) {
//
//                    }
//                    if (cmd[1].toLowerCase().equals("addplot")) {
//                        if (player.isAdmin()) {
//
//                        }
//                    }
//                }
//                if (cmd.length == 3) {
//                    if (cmd[1].toLowerCase().equals("create")) {
//                        String faName = cmd[2];
//
//                        if (!plugin.Config.BusinessPlotByAdmin || plugin.Business.isBusinessPlot(player.getCurrentArea()) || player.isAdmin()) {
//
//                        }
//                    }
//                }
//            }
//        }
//        
//    }
//           

}
