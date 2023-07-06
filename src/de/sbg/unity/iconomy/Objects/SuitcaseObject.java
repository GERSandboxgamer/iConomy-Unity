package de.sbg.unity.iconomy.Objects;

import de.sbg.unity.iconomy.Events.Suitcase.SuitcaseSpawnEvent;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.util.Arrays;
import net.risingworld.api.Server;
import net.risingworld.api.assets.PrefabAsset;
import net.risingworld.api.objects.Player;
import net.risingworld.api.utils.Vector3f;
import net.risingworld.api.worldelements.GameObject;
import net.risingworld.api.worldelements.Prefab;

public class SuitcaseObject extends GameObject{
    
    private long amounth;
    private final Player player;
    private boolean spawnned;
    
    

    public SuitcaseObject(PrefabAsset KofferVorlage, iConomy plugin, icConsole Console, Player player, long amounth, Vector3f pos) {
        Prefab prefabKoffer = new Prefab(KofferVorlage);
        this.addChild(prefabKoffer);
        this.setLocalPosition(pos);
        Arrays.asList(Server.getAllPlayers()).forEach((p2) -> {
            p2.addGameObject(this);
        });
        this.player = player;
        
        plugin.triggerEvent(new SuitcaseSpawnEvent(player, this));
    }

    public boolean isSpawnned() {
        return spawnned;
    }
    
    public long getAmounth() {
        return amounth;
    }

    public Player getPlayer() {
        return player;
    }

    public void setAmounth(long amounth) {
        this.amounth = amounth;
    }

}
