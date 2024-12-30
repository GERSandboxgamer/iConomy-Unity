package de.sbg.unity.iconomy.Listeners.Player;

import de.chaoswg.events.UIModel3DPlaceEvent;
import de.chaoswg.model3d.Model3DObject;
import de.sbg.unity.iconomy.Events.Objects.ATM.PlayerAtmDestroyEvent;
import de.sbg.unity.iconomy.Objects.AtmObject;
import de.sbg.unity.iconomy.Utils.ModelPlaceSave;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.sql.SQLException;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerGameObjectHitEvent;
import net.risingworld.api.objects.Player;
import net.risingworld.api.utils.Utils;
import net.risingworld.api.worldelements.GameObject;

public class PlayerAtmListener implements Listener {

    private final iConomy plugin;
    private final icConsole Console;

    public PlayerAtmListener(iConomy plugin, icConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
    }

    @EventMethod
    public void onPlayerAtmHitEvent(PlayerGameObjectHitEvent event) {
        GameObject go = event.getGameObject();
        Player player = event.getPlayer();
        String lang = player.getLanguage();
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("onPlayerAtmHitEvent", "Run event!");
            Console.sendDebug("onPlayerPlaceAtmEvent", "go instanceof Model3DObject = " + (go instanceof Model3DObject));
            Console.sendDebug("onPlayerPlaceAtmEvent", "go instanceof AtmObject = " + (go instanceof AtmObject));
        }

        if (go instanceof AtmObject atm) {
            if (plugin.Config.Debug > 0) {
                Console.sendDebug("onPlayerAtmHitEvent", "GameObject = Model3DObject");
            }
            if (player.isAdmin()) {
                if (player.getEquippedItem().getDefinition().id == 21) {
                    int lp = atm.removeLivePoints(Utils.MathUtils.nextRandomInt(5, 25));
                    if (lp <= 0) {
                        PlayerAtmDestroyEvent evt = new PlayerAtmDestroyEvent(player, atm);
                        plugin.triggerEvent(evt);
                        if (!evt.isCancelled()) {
                            try {
                                if (plugin.Config.Debug > 0) {
                                    Console.sendDebug("onPlayerAtmHitEvent", "Try Removed AMT-ID: " + atm.getID());
                                }
                                plugin.GameObject.atm.removeAtm(atm);
                                if (plugin.Config.Debug > 0) {
                                    Console.sendDebug("onPlayerAtmHitEvent", "Done!");
                                }
                                player.sendTextMessage(plugin.Language.getGameObject().getRemoveAtm(lang));
                            } catch (SQLException ex) {
                                player.sendTextMessage(plugin.Language.getGameObject().getSaveAtm_Fail(lang));
                                Console.sendErr("DB-SQL", "========= iConomy-Exception =========");
                                Console.sendErr("DB-SQL", "Can not remove ATM from database!");
                                Console.sendErr("DB-SQL", "Ex-Msg: " + ex.getMessage());
                                Console.sendErr("DB-SQL", "Ex-SQLState: " + ex.getSQLState());
                                for (StackTraceElement st : ex.getStackTrace()) {
                                    Console.sendErr("DB-SQL", st.toString());
                                }
                                Console.sendErr("DB-SQL", "=====================================");
                            }
                        } else {
                            atm.setLivePoints(100);
                        }

                    }
                } else {
                    player.showStatusMessage("Das geht mit diesem Item nicht!", 5); //TODO LANG
                }
            }
        }
    }

    // if (placer!=null && placer.equals(event.getPlacer())){
    @EventMethod
    public void onPlayerPlaceAtmEvent(UIModel3DPlaceEvent event) {
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("onPlayerPlaceAtmEvent", "Run Event!");
        }
        ModelPlaceSave mps = plugin.GameObject.atm.getModelSaveList().get(event.getModelAuswahl().getID());
        Player player = event.getPlayer();
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("onPlayerPlaceAtmEvent", "Load ModelSaveList with ID " + event.getModelAuswahl().getID());
        }
        try {
            if (mps.getPlace().equals(event.getPlacer())) {

                //(AtmDbObject atmDB = new AtmDbObject(plugin.GameObject.getListBundle().get("ATM"), mps.getAtmType(), event.getModelAuswahl().getLocalPosition(), event.getModelAuswahl().getLocalRotation(), plugin, Console);
                try {
                    plugin.GameObject.atm.saveAtm(mps.getAtmType(), event.getModelAuswahl().getLocalPosition(), event.getModelAuswahl().getLocalRotation());
                } catch (SQLException ex) {
                    Console.sendErr("onPlayerPlaceAtmEvent-SQL", "SQL-Exception!! ATM Can not save to database!");
                    Console.sendErr("onPlayerPlaceAtmEvent-SQL", ex.getMessage());
                }
                event.getModelAuswahl().removeFromAllPlayer();
            } else {
                Console.sendInfo("onPlayerPlaceAtmEvent", "MPS-Placer not Event-Placer!");
            }
        } catch (NullPointerException npe) {
            Console.sendInfo("onPlayerPlaceAtmEvent", "mps is null!");
        }
    }
}
