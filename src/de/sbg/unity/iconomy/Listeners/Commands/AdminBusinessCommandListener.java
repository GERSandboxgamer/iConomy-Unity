package de.sbg.unity.iconomy.Listeners.Commands;

import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.events.Listener;

public class AdminBusinessCommandListener implements Listener {

    private final iConomy plugin;
    private final icConsole Console;

    public AdminBusinessCommandListener(iConomy plugin, icConsole console) {
        this.plugin = plugin;
        this.Console = console;
    }

//    @EventMethod
//    public void onAdminCommandEvent(PlayerCommandEvent event) {
//        Player player = event.getPlayer();
//        String[] cmd = event.getCommand().split(" ");
//
//        if (player.isAdmin()) {
//            if (cmd.length >= 2) {
//                if (cmd[0].toLowerCase().equals("/ic") || cmd[0].toLowerCase().equals("/iconomy") || cmd[0].toLowerCase().equals("/money")) {
//                    if (cmd[1].toLowerCase().equals("factory") || cmd[1].toLowerCase().equals("fa")) {
//                        if (cmd.length == 4) {
//                            if (cmd[2].toLowerCase().equals("plot") || cmd[2].toLowerCase().equals("p")) {
//                                if (cmd[3].toLowerCase().equals("select") || cmd[3].toLowerCase().equals("sel") || cmd[3].toLowerCase().equals("s")) {
//                                    player.enableAreaSelectionTool();
//                                    plugin.Attribute.player.setFactoryPlotSelection(player, true);
//                                }
//                                if (cmd[3].toLowerCase().equals("cselect") || cmd[3].toLowerCase().equals("csel") || cmd[3].toLowerCase().equals("cs")) {
//                                    player.disableAreaSelectionTool();
//                                    plugin.Attribute.player.setFactoryPlotSelection(player, false);
//                                }
//                                if (cmd[3].toLowerCase().equals("add") || cmd[3].toLowerCase().equals("a")) {
//                                    if (plugin.Attribute.player.getFactoryPlotSelection(player)) {
//                                        plugin.Attribute.player.setFactoryPlotSelection(player, false);
//                                        player.getAreaSelectionData((t) -> {
//
//                                            try {
//                                                plugin.Factory.factoryPlots.addPlot(t);
//                                                Server.addArea(t, true);
//                                                plugin.Attribute.area.setAreaShow(t, false);
//                                                player.sendTextMessage("Add Area");//TODO Lang
//                                            } catch (SQLException ex) {
//                                                player.sendTextMessage("Can not save Plot do Database!");
//                                                Console.sendErr("============== iConomy-Facotry Error ==============");
//                                                Console.sendErr("AdminCommand-plot-add", "Can not save plot do database!");
//                                                Console.sendErr("AdminCommand-plot-add", ex.getMessage());
//                                                for (StackTraceElement ste : ex.getStackTrace()) {
//                                                    Console.sendErr("AdminCommand-plot-add", ste.toString());
//                                                }
//                                                Console.sendErr("PluginInfo", "Dev: Sandboxgamer");
//                                                Console.sendErr("============== iConomy-Facotry Error ==============");
//                                            }
//                                        });
//                                    }
//                                }
//                                if (cmd[3].toLowerCase().equals("showall") || cmd[3].toLowerCase().equals("sa")) {
//                                    if (!plugin.Factory.getAllFactoryPlots().isEmpty()) {
//                                        plugin.Factory.getAllFactoryPlots().forEach((t) -> {
//                                            player.addGameObject(plugin.Attribute.area.getArea3D(t));
//                                        });
//                                    } else {
//                                        player.sendTextMessage("Found no plots!");
//                                    }
//                                }
//                                if (cmd[3].toLowerCase().equals("hideall") || cmd[3].toLowerCase().equals("ha")) {
//                                    if (!plugin.Factory.getAllFactoryPlots().isEmpty()) {
//                                        plugin.Factory.getAllFactoryPlots().forEach((t) -> {
//                                            player.removeGameObject(plugin.Attribute.area.getArea3D(t));
//                                        });
//                                    } else {
//                                        player.sendTextMessage("Found no plots!");
//                                    }
//                                }
//                            }
//                        }
//                        if (cmd.length == 5) {
//                            if (cmd[2].toLowerCase().equals("plot") || cmd[2].toLowerCase().equals("p")) {
//                                Area a = player.getCurrentArea();
//
//                                if (cmd[3].toLowerCase().equals("price") || cmd[3].toLowerCase().equals("p")) {
//                                    if (a != null && plugin.Factory.factoryPlots.isPlot(a)) {
//                                        plugin.Factory.factoryPlots.getPlot(a).setPrice(plugin.moneyFormat.getMoneyAsLong(cmd[4]));
//                                        player.sendTextMessage("Set Price");//TODO Lang
//                                    } else {
//                                        player.sendTextMessage("You are not in a factory plot");//TODO Lang
//                                    }
//                                }
//                                if (cmd[3].toLowerCase().equals("name") || cmd[3].toLowerCase().equals("n")) {
//                                    if (a != null && plugin.Factory.factoryPlots.isPlot(a)) {
//                                        plugin.Factory.factoryPlots.getPlot(a).setName(cmd[4]);
//                                        player.sendTextMessage("Set name to " + cmd[4]);//TODO Lang
//                                    } else {
//                                        player.sendTextMessage("You are not in a factory plot");//TODO Lang
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
