
package de.sbg.unity.iconomy;

import de.chaoswg.model3d.Model3DPlace;
import net.risingworld.api.objects.Player;


public class icAttribute {
    
    public icPlayer player;
    
    public icAttribute() {
        this.player = new icPlayer();
    }
    
    public class icPlayer {
        public final String icAtmPlacer;
        
        public icPlayer() {
            icAtmPlacer = "sbg-iConomy-icAtmPlacer";
        }
        
        public Model3DPlace getPlacer(Player player) {
            return (Model3DPlace)player.getAttribute(icAtmPlacer);
        }
        public void setPlacer(Player player, Model3DPlace placer) {
            player.setAttribute(icAtmPlacer, placer);
        }
    }
    
}
