package de.sbg.unity.iconomy.Objects;

import de.sbg.unity.iconomy.Utils.AtmUtils.AtmType;
import de.sbg.unity.iconomy.Utils.PrefabVorlage;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.utils.Quaternion;
import net.risingworld.api.utils.Vector3f;
import net.risingworld.api.worldelements.Prefab;


public class AtmObject extends Prefab {
    
    private final icConsole Console;
    private final iConomy plugin;
    private AtmType Type;
    private int livePoints;
    private final int dbID;
    
    public AtmObject(int dbID, PrefabVorlage auswahl, AtmType Type, Vector3f pos, Quaternion rot, iConomy plugin, icConsole Console) {
        super(auswahl.getPrefabAsset());
        this.plugin = plugin;
        this.Console = Console;
        this.dbID = dbID;
        this.Type = Type;
        this.setLocalPosition(pos);
        this.setLocalRotation(rot);
        this.livePoints = 100;
    }
    
//    public AtmObject(PrefabVorlage auswahl, AtmType Type, Player player, iConomy plugin, icConsole Console){
//        super(auswahl.getPrefabAsset());
//        this.plugin = plugin;
//        this.Console = Console;
//        this.Type = Type;
//        this.livePoints = 100;
//        
//        player.setListenForMouseInput(true);
//    }
    
    public int removeLivePoints(int points){
        int rest = livePoints - points;
        if (rest >= 0) {
            setLivePoints(rest);
            return rest;
        } else {
            setLivePoints(0);
            return 0;
        }
    }

    public int getLivePoints() {
        return livePoints;
    }

    public void setLivePoints(int livePoints) {
        this.livePoints = livePoints;
    }

    public AtmType getType() {
        return Type;
    }

    public void setType(AtmType Type) {
        this.Type = Type;
        plugin.Databases.saveAtm();
    }

    public int getDbID() {
        return dbID;
    }
    
}
