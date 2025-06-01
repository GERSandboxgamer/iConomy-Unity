package de.sbg.unity.iconomy.Npc;

import de.sbg.unity.iconomy.iConomy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.risingworld.api.World;
import net.risingworld.api.objects.Npc;
import net.risingworld.api.objects.Player;

public class NpcSystem {

    private final List<Npc> npcList;
    private final iConomy plugin;
    
    public final SpeakSystemManager speakSystem;
    public final FollowSystemManager followSystem;

    public NpcSystem(iConomy plugin) {
        this.plugin = plugin;
        npcList = new ArrayList<>();
        speakSystem = new SpeakSystemManager();
        followSystem = new FollowSystemManager();
    }

    public boolean isICNpc(Npc npc) {
        return npcList.contains(npc);
    }

    public List<Npc> getNpcList() {
        return npcList;
    }

    public void addNpc(long id, int mode) throws SQLException {
        Npc npc = World.getNpc(id);
        if (npc != null) {
            addNpc(npc, mode);
        }

    }

    public void addNpc(Npc npc, int mode) throws SQLException {
        npcList.add(npc);
        plugin.Attribute.npc.setNpcMode(npc, mode);

        plugin.Databases.Money.NPC.add(npc, mode);

    }

    public boolean removeNpc(long id) throws SQLException {
        Npc npc = World.getNpc(id);
        if (npc != null) {
            return removeNpc(npc);
        }
        return false;
    }

    public boolean removeNpc(Npc npc) throws SQLException {
        plugin.Databases.Money.NPC.remove(npc);
        return npcList.remove(npc);
    }

    public class SpeakSystemManager {

        private final HashMap<String, SpeakSystem> speak;

        public SpeakSystemManager() {
            speak = new HashMap<>();
        }

        public void addPlayer(Player player) {
            speak.put(player.getUID(), new SpeakSystem(player, plugin));
        }

        public SpeakSystem getSpeakSystem(Player player) {
            return getSpeakSystem(player.getUID());
        }

        public SpeakSystem getSpeakSystem(String uid) {
            return speak.get(uid);
        }

        public void removePlayer(Player player) {
            removePlayer(player.getUID());
        }

        public void removePlayer(String uid) {
            speak.remove(uid);
        }

    }
    
    public class FollowSystemManager {
        
        private final HashMap<Player, FollowSystem> followList;
        
        public FollowSystemManager() {
            this.followList = new HashMap<>();
        }
        
        public FollowSystem getSystem(Player player) {
            return followList.get(player);
        }
        
        public void addPlayer(Player player){
            followList.put(player, new FollowSystem(player));
        }
    }

}
