package de.sbg.unity.iconomy.Events.Business;

import de.sbg.unity.iconomy.Business.Business;
import net.risingworld.api.Server;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;

public class RemoveOwnerEvent extends Event implements Cancellable{
    
    private final Player player;
    private final Business factory;
    private final String ownerUID;
    
    public RemoveOwnerEvent(Player p, Business f, String ownerUID) {
        this.player = p;
        this.factory = f;
        this.ownerUID = ownerUID;
    }
    
    public Business getFactory() {
        return factory;
    }

    public Player getPlayer() {
        return player;
    }

    public String getOldOwnerUID() {
        return ownerUID;
    }
    
    public Player getOldOwner() {
        Player p = Server.getPlayerByUID(ownerUID);
        if (p != null && p.isConnected()) {
            return p;
        }
        return null;
    }
    
    
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean bln) {
        cancelled = bln;
    }
}
