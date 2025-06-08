package de.sbg.unity.iconomy.Listeners.Commands;

import de.sbg.unity.iconomy.Utils.TextFormat;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.Server;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerCommandEvent;
import java.sql.*;
import net.risingworld.api.objects.Area;
import net.risingworld.api.objects.Player;

public class AdminBusinessCommandListener implements Listener {

    private final iConomy plugin;
    private final icConsole Console;
    private final TextFormat format;

    public AdminBusinessCommandListener(iConomy plugin, icConsole console) {
        this.plugin = plugin;
        this.Console = console;
        this.format = new TextFormat();
    }

    @EventMethod
    public void onAdminCommandEvent(PlayerCommandEvent event) {
        Player player = event.getPlayer();
        String[] cmd = event.getCommand().split(" ");

        if (player.isAdmin()) {
            if (cmd.length >= 2) {
                if (cmd[0].toLowerCase().equals("/ic") || cmd[0].toLowerCase().equals("/iconomy") || cmd[0].toLowerCase().equals("/money")) {
                    if (cmd[1].toLowerCase().equals("business") || cmd[1].toLowerCase().equals("bus")) {
                        if (cmd[2].toLowerCase().equals("plot") || cmd[2].toLowerCase().equals("p")) {
                            Area a = player.getCurrentArea();
                            if (cmd.length == 4) {
                                if (cmd[3].toLowerCase().equals("select") || cmd[3].toLowerCase().equals("sel") || cmd[3].toLowerCase().equals("s")) {
                                    player.enableAreaSelectionTool(false);
                                    plugin.Attribute.player.setBusinessPlotSelection(player, true);
                                    player.sendTextMessage(format.Color("green", "Start Selection Tool!")); //TODO Lang Business
                                }
                                if (cmd[3].toLowerCase().equals("cselect") || cmd[3].toLowerCase().equals("csel") || cmd[3].toLowerCase().equals("cs")) {
                                    player.disableAreaSelectionTool();
                                    plugin.Attribute.player.setBusinessPlotSelection(player, false);
                                    player.sendTextMessage(format.Color("red", "Stop Selection Tool!")); //TODO Lang Business
                                }
                                if (cmd[3].toLowerCase().equals("add") || cmd[3].toLowerCase().equals("a")) {
                                    if (plugin.Attribute.player.getBusinessPlotSelection(player)) {
                                        plugin.Attribute.player.setBusinessPlotSelection(player, false);
                                        player.getAreaSelectionData((t) -> {
                                            try {
                                                Area area = new Area(t.getStartPosition(), t.getEndPosition());
                                                plugin.Business.businessPlots.addPlot(area);
                                                Server.addArea(area, true);
                                                plugin.Attribute.area.setAreaShow(area, false);
                                                player.sendTextMessage(format.Color("green", "Add Area!"));//TODO Lang Business
                                            } catch (SQLException ex) {
                                                player.sendTextMessage("Can not save Plot do Database!");
                                                Console.sendErr("============== iConomy-Facotry Error ==============");
                                                Console.sendErr("AdminCommand-plot-add", "Can not save plot do database!");
                                                Console.sendErr("AdminCommand-plot-add", ex.getMessage());
                                                for (StackTraceElement ste : ex.getStackTrace()) {
                                                    Console.sendErr("AdminCommand-plot-add", ste.toString());
                                                }
                                                Console.sendErr("PluginInfo", "Dev: Sandboxgamer");
                                                Console.sendErr("============== iConomy-Facotry Error ==============");
                                            }
                                        });
                                    } else {
                                        if (a != null) {
                                            if (!plugin.Business.isBusinessPlot(a)) {
                                                try {
                                                    plugin.Business.businessPlots.addPlot(a);
                                                    player.sendTextMessage(format.Color("green", "Add Area!"));//TODO Lang Business
                                                } catch (SQLException ex) {
                                                    player.sendTextMessage(format.Color("red", "Can not save Plot do Database!"));
                                                    Console.sendErr("============== iConomy-Facotry Error ==============");
                                                    Console.sendErr("AdminCommand-plot-add", "Can not save plot do database!");
                                                    Console.sendErr("AdminCommand-plot-add", ex.getMessage());
                                                    for (StackTraceElement ste : ex.getStackTrace()) {
                                                        Console.sendErr("AdminCommand-plot-add", ste.toString());
                                                    }
                                                    Console.sendErr("PluginInfo", "Dev: Sandboxgamer");
                                                    Console.sendErr("============== iConomy-Facotry Error ==============");
                                                }
                                            }
                                        }
                                    }
                                }
                                if (cmd[3].toLowerCase().equals("showall") || cmd[3].toLowerCase().equals("sa")) {
                                    if (!plugin.Business.getAllBusinessPlots().isEmpty()) {
                                        plugin.Business.getAllBusinessPlots().forEach((t) -> {
                                            player.addGameObject(plugin.Attribute.area.getArea3D(t.getArea()));
                                        });
                                    } else {
                                        player.sendTextMessage(format.Color("red", "Found no plots!"));
                                    }
                                }
                                if (cmd[3].toLowerCase().equals("hideall") || cmd[3].toLowerCase().equals("ha")) {
                                    if (!plugin.Business.getAllBusinessPlots().isEmpty()) {
                                        plugin.Business.getAllBusinessPlots().forEach((t) -> {
                                            player.removeGameObject(plugin.Attribute.area.getArea3D(t.getArea()));
                                        });
                                    } else {
                                        player.sendTextMessage(format.Color("red", "Found no plots!"));
                                    }
                                }
                            }
                            if (cmd.length == 5) {
                                if (cmd[3].toLowerCase().equals("select") || cmd[3].toLowerCase().equals("sel") || cmd[3].toLowerCase().equals("s")) {
                                    if (cmd[4].toLowerCase().equals("true")) {
                                        player.enableAreaSelectionTool(true);
                                        plugin.Attribute.player.setBusinessPlotSelection(player, true);
                                        player.sendTextMessage(format.Color("green", "Start Selection Tool!")); //TODO Lang Business
                                    }
                                }
                                if (cmd[3].toLowerCase().equals("price") || cmd[3].toLowerCase().equals("p")) {
                                    if (a != null && plugin.Business.businessPlots.isPlot(a)) {
                                        plugin.Business.businessPlots.getPlot(a).setPrice(plugin.moneyFormat.getMoneyAsLong(cmd[4]));
                                        player.sendTextMessage(format.Color("green","Set Price"));//TODO Lang Business
                                    } else {
                                        player.sendTextMessage(format.Color("red","You are not in a factory plot"));//TODO Lang Business
                                    }
                                }
                                if (cmd[3].toLowerCase().equals("name") || cmd[3].toLowerCase().equals("n")) {
                                    if (a != null && plugin.Business.businessPlots.isPlot(a)) {
                                        plugin.Business.businessPlots.getPlot(a).setName(cmd[4]);
                                        player.sendTextMessage(format.Color("green","Set name to " + cmd[4]));//TODO Lang Business
                                    } else {
                                        player.sendTextMessage(format.Color("red","You are not in a factory plot"));//TODO Lang Business
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
