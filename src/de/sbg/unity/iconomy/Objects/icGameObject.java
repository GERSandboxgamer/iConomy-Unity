package de.sbg.unity.iconomy.Objects;

import de.chaoswg.events.call.ModelAuswahl;
import de.chaoswg.model3d.Model3DObject;
import de.chaoswg.model3d.Model3DPlace;
import de.sbg.unity.iconomy.Events.Money.AddCashEvent;
import de.sbg.unity.iconomy.Events.Objects.Suitcase.PlayerSuitcaseRemoveEvent;
import de.sbg.unity.iconomy.Events.Objects.Suitcase.SuitcaseRemoveEvent;
import de.sbg.unity.iconomy.Utils.AtmUtils.AtmType;
import de.sbg.unity.iconomy.Utils.ModelPlaceSave;
import de.sbg.unity.iconomy.Utils.PrefabVorlage;
import de.sbg.unity.iconomy.Utils.TransferResult;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.risingworld.api.Server;
import net.risingworld.api.objects.Player;
import net.risingworld.api.utils.Layer;
import net.risingworld.api.utils.Quaternion;
import net.risingworld.api.utils.Vector3f;
import net.risingworld.api.worldelements.GameObject;

public class icGameObject {

    private final iConomy plugin;
    private final icConsole Console;
    private final HashMap<String, GameObject> ListFiles;
    private final HashMap<String, PrefabVorlage> ListBundle;

    public final Suitcase suitcase;
    public final ATM atm;

    public icGameObject(iConomy plugin, icConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        this.ListFiles = new HashMap<>();
        this.ListBundle = new HashMap<>();
        this.suitcase = new Suitcase();
        this.atm = new ATM();
    }

    public HashMap<String, GameObject> getListFiles() {
        return ListFiles;
    }

    public HashMap<String, PrefabVorlage> getListBundle() {
        return ListBundle;
    }

    public void add(String name, PrefabVorlage asset) {
        ListBundle.put(name, asset);
    }

    public void add(String name, GameObject asset) {
        ListFiles.put(name, asset);
    }

    public class Suitcase {

        private final HashMap<String, SuitcaseObject> SuitcaseList;

        public Suitcase() {
            SuitcaseList = new HashMap<>();
        }

        public void spawn(Player player, long amounth) {
            spawn(player, amounth, player.getPosition());
        }

        public void spawn(Player player, long amounth, Vector3f pos) {
            SuitcaseObject so = new SuitcaseObject(ListBundle.get("Geldkoffer").getPrefabAsset(), plugin, Console, player, amounth, pos);
            so.setLayer(Layer.OBJECT);

            //System.out.println("Suitcase Layer 2: " + so.getLayer());
            if (so.isSpawnned()) {
                addToList(player.getUID(), so);
            }
        }

        public SuitcaseObject getByPlayer(Player player) {
            return SuitcaseList.get(player.getUID());
        }

        public SuitcaseObject getByPlayer(String uid) {
            return SuitcaseList.get(uid);
        }

        public HashMap<String, SuitcaseObject> getList() {
            return SuitcaseList;
        }

        public void addToList(String uid, SuitcaseObject s) {
            SuitcaseList.put(uid, s);
        }

        public boolean remove(SuitcaseObject sc, SuitcaseRemoveEvent.Cause cause) {
            SuitcaseRemoveEvent evt = new SuitcaseRemoveEvent(sc, cause);
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                SuitcaseList.remove(sc.getPlayer().getUID());
                Arrays.asList(Server.getAllPlayers()).forEach((p2) -> {
                    p2.removeGameObject(sc);
                });
                return true;
            }
            return false;
        }

        public boolean remove(Player player, SuitcaseObject sc, PlayerSuitcaseRemoveEvent.Cause cause) {
            PlayerSuitcaseRemoveEvent evt = new PlayerSuitcaseRemoveEvent(player, sc, cause);
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                if (cause == PlayerSuitcaseRemoveEvent.Cause.PlayerInteraktion) {
                    TransferResult tr = plugin.CashSystem.addCash(player, sc.getAmount(), AddCashEvent.Reason.Player);
                    switch (tr) {
                        case Successful -> {
                            sc.stopTimer();
                            SuitcaseList.remove(sc.getPlayer().getUID());
                            Arrays.asList(Server.getAllPlayers()).forEach((p2) -> {
                                p2.removeGameObject(sc);
                            });
                            player.sendTextMessage(plugin.Language.getGameObject().getFindMoney(player.getLanguage()));
                            return true;
                        }
                        case EventCancel -> {
                            player.sendTextMessage(plugin.Language.getStatus().getTransferCancel(player.getLanguage()));
                        }
                    }
                } else {
                    sc.stopTimer();
                    SuitcaseList.remove(sc.getPlayer().getUID());
                    Arrays.asList(Server.getAllPlayers()).forEach((p2) -> {
                        p2.removeGameObject(sc);
                    });
                    return true;
                }
            }
            return false;
        }

    }

    public class ATM {

        private final List<AtmObject> AtmList;
        private final HashMap<Integer, ModelPlaceSave> ModelSaveList;

        public ATM() {
            this.AtmList = new ArrayList<>();
            this.ModelSaveList = new HashMap<>();

        }

        public HashMap<Integer, ModelPlaceSave> getModelSaveList() {
            return ModelSaveList;
        }

        public List<AtmObject> getAtmList() {
            return AtmList;
        }

        public void saveAtm(AtmType type, Vector3f pos, Quaternion rot) throws SQLException {
            AtmObject atm = plugin.Databases.Money.ATM.add(plugin.GameObject.getListBundle().get("ATM"), type, pos, rot);
            AtmList.add(atm);
            Arrays.asList(Server.getAllPlayers()).forEach((p2) -> {
                p2.addGameObject(atm);
            });
        }

        public void createAtm(Player player, AtmType type) {

            Model3DPlace place = Model3DPlace.create(plugin, player, plugin.Language.getRadialPlace());
            //plugin.Attribute.player.setPlacer(player, place);
            ModelAuswahl modelAuswahl = new ModelAuswahl();
            modelAuswahl.setBundle(true);
            modelAuswahl.setBundle(plugin.GameObject.getListBundle().get("ATM").getBundlePath() + "/" + "ATM" + "/" + plugin.GameObject.getListBundle().get("ATM").getPrefabAsset().getPath());
            Model3DObject gameObject = new Model3DObject(player.getUID(), -1, modelAuswahl);
//            player.setListenForMouseInput(true);
//            plugin.registerEventListener(place);
            place.place(gameObject, false, false);
            ModelPlaceSave mps = new ModelPlaceSave(place, gameObject, gameObject.getID(), type);
            ModelSaveList.put(gameObject.getID(), mps);
            if (plugin.Config.Debug > 0) {
                player.sendTextMessage("Add ModelSaveList with ID " + gameObject.getID());
            }
        }

        public void removeAtm(AtmObject atm) throws SQLException {
            plugin.Databases.Money.ATM.remove(atm);
            AtmList.remove(atm);
            Arrays.asList(Server.getAllPlayers()).forEach((p) -> {
                p.removeGameObject(atm);
            });
        }

    }

}
