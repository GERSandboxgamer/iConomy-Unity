package de.sbg.unity.iconomy.Events.Business;

import de.sbg.unity.iconomy.Business.Business;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;

public class RemoveBusinessEvent extends Event implements Cancellable{
    
    private final Player player;
    private final Business factory;
    
    public RemoveBusinessEvent(Player p, Business f) {
        this.player = p;
        this.factory = f;
    }

    public Player getPlayer() {
        return player;
    }

    public Business getFactory() {
        return factory;
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
