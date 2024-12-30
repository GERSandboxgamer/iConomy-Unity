/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.sbg.unity.iconomy.Listeners.Commands;

import de.sbg.unity.iconomy.Factory.FactoryPlots;
import de.sbg.unity.iconomy.iConomy;
import net.risingworld.api.Server;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerCommandEvent;
import net.risingworld.api.objects.Area;
import net.risingworld.api.objects.Player;

/**
 *
 * @author pbronke
 */
public class PlayerFactoryCommandsListener implements Listener {

    private final iConomy plugin;

    public PlayerFactoryCommandsListener(iConomy plugin) {
        this.plugin = plugin;
    }

    @EventMethod
    public void onPlayerCommandEvent(PlayerCommandEvent event) {
        Player player = event.getPlayer();
        String lang = player.getLanguage();
        String[] cmd = event.getCommand().split(" ");

        if (cmd.length >= 2) {
            if (cmd[0].toLowerCase().equals("/ic") || cmd[0].toLowerCase().equals("/iconomy") || cmd[0].toLowerCase().equals("/money")) {
                if (cmd[1].toLowerCase().equals("factory") || cmd[1].toLowerCase().equals("fa")) {

                    if (cmd.length == 2) {
                        //TODO Factory GUI
                    }
                    if (cmd.length == 4) {
                        if (cmd[2].toLowerCase().equals("plot") || cmd[2].toLowerCase().equals("p")) {
                            Area area = player.getCurrentArea();
                            if (cmd[3].toLowerCase().equals("price")) {

                                if (area != null && plugin.Factory.factoryPlots.isPlot(area)) {
                                    FactoryPlots.FactoryPlot fp = plugin.Factory.factoryPlots.getPlot(area);
                                    player.sendTextMessage("Price: " + plugin.moneyFormat.getMoneyAsFormatedString(player, fp.getPrice()));
                                }
                            }

                            if (cmd[3].toLowerCase().equals("name")) {
                                if (area != null && plugin.Factory.factoryPlots.isPlot(area)) {
                                    FactoryPlots.FactoryPlot fp = plugin.Factory.factoryPlots.getPlot(area);
                                    if (!fp.getName().isBlank()) {
                                        player.sendTextMessage("Name: '" + fp.getName() + "'");
                                    } else {
                                        player.sendTextMessage("[Factory] No name set!"); //TODO Lang
                                    }
                                    
                                }
                            }

                            if (cmd[3].toLowerCase().equals("show")) {
                                if (area != null && plugin.Factory.factoryPlots.isPlot(area)) {
                                    if (!plugin.Attribute.area.getAreaShow(area)) {
                                        for (Player p : Server.getAllPlayers()) {
                                            p.addGameObject(plugin.Attribute.area.getArea3D(area));
                                            
                                        }
                                        player.sendTextMessage("Show plot!");
                                    } else {
                                        //TODO MSG
                                    }
                                }

                            }
                            if (cmd[3].toLowerCase().equals("hide")) {
                                if (area != null && plugin.Factory.factoryPlots.isPlot(area)) {
                                    if (plugin.Attribute.area.getAreaShow(area)) {
                                        for (Player p : Server.getAllPlayers()) {
                                            p.removeGameObject(plugin.Attribute.area.getArea3D(area));
                                            player.sendTextMessage("Show plot!");
                                        }
                                        player.sendTextMessage("Show plot!");
                                    } else {
                                        //TODO MSG
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
