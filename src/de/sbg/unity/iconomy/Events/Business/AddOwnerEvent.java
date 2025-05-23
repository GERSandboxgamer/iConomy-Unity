package de.sbg.unity.iconomy.Events.Business;

import de.sbg.unity.iconomy.Business.Business;
import net.risingworld.api.Server;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;

public class AddOwnerEvent extends Event implements Cancellable{
    
    private final Player player;
    private final Business factory;
    private final String newOwnerUID;
    
    public AddOwnerEvent(Player p, Business f, String newownerUID) {
        this.player = p;
        this.factory = f;
        this.newOwnerUID = newownerUID;
    }
    
    public Business getFactory() {
        return factory;
    }

    public Player getPlayer() {
        return player;
    }

    public String getNewOwnerUID() {
        return newOwnerUID;
    }
    
    /**
     * Get the new owner as Player. <b>Player must be online!</b>
     * @return
     */
    public Player getNewOwner() {
        Player p = Server.getPlayerByUID(newOwnerUID);
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
