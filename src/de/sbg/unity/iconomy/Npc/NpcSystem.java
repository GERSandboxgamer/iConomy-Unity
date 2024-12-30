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
    private final HashMap<String, SpeakSystem> speak;

    public NpcSystem(iConomy plugin) {
        this.plugin = plugin;
        npcList = new ArrayList<>();
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

    public boolean isICNpc(Npc npc) {
        return npcList.contains(npc);
    }

    public List<Npc> getNpcList() {
        return npcList;
    }

    public void addNpc(long id, int mode) throws SQLException{
        Npc npc = World.getNpc(id);
        if (npc != null) {
            addNpc(npc, mode);
        }

    }

    public void addNpc(Npc npc, int mode) throws SQLException{
        npcList.add(npc);
        plugin.Attribute.npc.setNpcMode(npc, mode);
       
            plugin.Databases.Money.NPC.add(npc, mode);
        
    }

    public boolean removeNpc(long id) {
        Npc npc = World.getNpc(id);
        if (npc != null) {
            return removeNpc(npc);
        }
        return false;
    }

    public boolean removeNpc(Npc npc) {
        return npcList.remove(npc);
    }

}
