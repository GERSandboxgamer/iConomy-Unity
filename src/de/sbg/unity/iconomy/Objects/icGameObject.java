package de.sbg.unity.iconomy.Objects;

import de.sbg.unity.iconomy.Events.Suitcase.SuitcaseRemoveEvent;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.util.HashMap;
import net.risingworld.api.assets.PrefabAsset;
import net.risingworld.api.objects.Player;
import net.risingworld.api.utils.Vector3f;

public class icGameObject {

    private final iConomy plugin;
    private final icConsole Console;
    private final HashMap<String, PrefabAsset> ListBundle;
    private final String icObjectAtt;

    public final Suitcase suitcase;

    public icGameObject(iConomy plugin, icConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        this.ListBundle = new HashMap<>();
        this.suitcase = new Suitcase();
        this.icObjectAtt = "SBG-iConomy-GameObject";
    }

    public HashMap<String, PrefabAsset> getList() {
        return ListBundle;
    }
    
    public void add(String name, PrefabAsset asset) {
        ListBundle.put(name, asset);
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
            SuitcaseObject so = new SuitcaseObject(ListBundle.get("Geldkoffer"), plugin, Console, player, amounth, pos);
            
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

        public boolean remove(Player player) {
            return remove(player, SuitcaseRemoveEvent.Cause.API);
        }

        public boolean remove(Player player, SuitcaseRemoveEvent.Cause Cause) {
            SuitcaseObject sc = getByPlayer(player);
            if (sc != null) {
                SuitcaseRemoveEvent evt = new SuitcaseRemoveEvent(player, sc, Cause);
                return !evt.isCancelled() && (getList().remove(player.getUID()) != null);
            }
            return false;
        }

    }

}
