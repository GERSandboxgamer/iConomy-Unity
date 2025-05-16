/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.sbg.unity.iconomy.Listeners.Commands;

import de.sbg.unity.iconomy.iConomy;
import net.risingworld.api.events.Listener;

/**
 *
 * @author pbronke
 */
public class PlayerBusinessCommandsListener implements Listener {

    private final iConomy plugin;

    public PlayerBusinessCommandsListener(iConomy plugin) {
        this.plugin = plugin;
    }

//    @EventMethod
//    public void onPlayerCommandEvent(PlayerCommandEvent event) {
//        Player player = event.getPlayer();
//        String lang = player.getLanguage();
//        String[] cmd = event.getCommand().split(" ");
//
//        if (cmd.length >= 2) {
//            if (cmd[0].toLowerCase().equals("/ic") || cmd[0].toLowerCase().equals("/iconomy") || cmd[0].toLowerCase().equals("/money")) {
//                if (cmd[1].toLowerCase().equals("business") || cmd[1].toLowerCase().equals("fa")) {
//
//                    if (cmd.length == 2) {
//                        //TODO Business GUI
//                    }
//                    if (cmd.length == 4) {
//                        if (cmd[2].toLowerCase().equals("plot") || cmd[2].toLowerCase().equals("p")) {
//                            Area area = player.getCurrentArea();
//                            if (cmd[3].toLowerCase().equals("price")) {
//
//                                if (area != null && plugin.Business.businessPlots.isPlot(area)) {
//                                    BusinessPlots.BusinessPlot fp = plugin.Business.businessPlots.getPlot(area);
//                                    player.sendTextMessage("Price: " + plugin.moneyFormat.getMoneyAsFormatedString(player, fp.getPrice()));
//                                }
//                            }
//
//                            if (cmd[3].toLowerCase().equals("name")) {
//                                if (area != null && plugin.Business.businessPlots.isPlot(area)) {
//                                    BusinessPlots.BusinessPlot fp = plugin.Business.businessPlots.getPlot(area);
//                                    if (!fp.getName().isBlank()) {
//                                        player.sendTextMessage("Name: '" + fp.getName() + "'");
//                                    } else {
//                                        player.sendTextMessage("[Business] No name set!"); //TODO Lang
//                                    }
//                                    
//                                }
//                            }
//
//                            if (cmd[3].toLowerCase().equals("show")) {
//                                if (area != null && plugin.Business.businessPlots.isPlot(area)) {
//                                    if (!plugin.Attribute.area.getAreaShow(area)) {
//                                        for (Player p : Server.getAllPlayers()) {
//                                            p.addGameObject(plugin.Attribute.area.getArea3D(area));
//                                            
//                                        }
//                                        player.sendTextMessage("Show plot!");
//                                    } else {
//                                        //TODO MSG
//                                    }
//                                }
//
//                            }
//                            if (cmd[3].toLowerCase().equals("hide")) {
//                                if (area != null && plugin.Business.businessPlots.isPlot(area)) {
//                                    if (plugin.Attribute.area.getAreaShow(area)) {
//                                        for (Player p : Server.getAllPlayers()) {
//                                            p.removeGameObject(plugin.Attribute.area.getArea3D(area));
//                                            player.sendTextMessage("Show plot!");
//                                        }
//                                        player.sendTextMessage("Show plot!");
//                                    } else {
//                                        //TODO MSG
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
}
