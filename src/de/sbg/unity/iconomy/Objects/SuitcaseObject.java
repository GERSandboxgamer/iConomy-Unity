package de.sbg.unity.iconomy.Objects;

import de.sbg.unity.iconomy.Events.Objects.Suitcase.SuitcaseSpawnEvent;
import de.sbg.unity.iconomy.GUI.SuitcaseTimer;
import de.sbg.unity.iconomy.Utils.icPlayer;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.util.Arrays;
import net.risingworld.api.Server;
import net.risingworld.api.assets.PrefabAsset;
import net.risingworld.api.objects.Player;
import net.risingworld.api.utils.Vector3f;
import net.risingworld.api.worldelements.Prefab;

public class SuitcaseObject extends Prefab {

    private long amount;
    private final icPlayer player;
    private boolean spawnned;
    //private final Timer SuitcaseTimer;
    private final iConomy plugin;
    private int HealPoints;
    private final SuitcaseTimer timer;

    public SuitcaseObject(PrefabAsset KofferVorlage, iConomy plugin, icConsole Console, Player player, long amount, Vector3f pos) {
        super(KofferVorlage);
        
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("==================== Suitcase ====================");
            Console.sendDebug("SuitcaseObject", "Spawn new Suitcase");
        }
        this.plugin = plugin;
        this.amount = amount;
        this.HealPoints = 100;

        //this.setLayer(Layer.OBJECT);
        if (plugin.Config.Debug > 0) {
            this.setColliderVisible(true);
        }
        this.setLocalPosition(pos);
        this.timer = new SuitcaseTimer(plugin, this, player.getName(), plugin.Config.SuitcaseTime);
//        this.SuitcaseTimer = new Timer(plugin.Config.SuitcaseTime, 0f, 0, () -> {
//            if (plugin.Config.Debug > 0) {
//                Console.sendDebug("==================== Suitcase ====================");
//                Console.sendDebug("SuitcaseObject", "Timer Tick!");
//                Console.sendDebug("==================== Suitcase ====================");
//            }
//            plugin.GameObject.suitcase.remove(this, SuitcaseRemoveEvent.Cause.Time);
//        });
        //SuitcaseTimer.start();
        if (plugin.Config.Debug > 0) {
            Console.sendDebug("SuitcaseObject", "Start Timer!");
        }
        Arrays.asList(Server.getAllPlayers()).forEach((p2) -> {
            p2.addGameObject(this);
//            p2.setListenForKeyInput(true);
//            p2.registerKeys(Key.F);
        });
        this.player = new icPlayer(player);

        plugin.triggerEvent(new SuitcaseSpawnEvent(player, this));
        Console.sendDebug("==================== Suitcase ====================");
    }
    
//    public void stopTimer() {
//        if (SuitcaseTimer != null && SuitcaseTimer.isActive()) {
//            SuitcaseTimer.kill();
//        }
//    }

    public int getHealPoints() {
        return HealPoints;
    }

    public void setHealPoints(int HealPoints) {
        this.HealPoints = HealPoints;
    }

    public void removeSuitcaseGameObject(Player p2) {
        p2.removeGameObject(this);
    }
    
    /**
     * Removed the suitcase for all players
     */
    public void removeSuitcaseGameObject(){
        for (Player p2 : Server.getAllPlayers()) {
            p2.removeGameObject(this);
        }
    }

    public boolean isSpawnned() {
        return spawnned;
    }

    public long getAmount() {
        return amount;
    }

    public icPlayer getPlayer() {
        return player;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public SuitcaseTimer getTimer() {
        return timer;
    }

}
