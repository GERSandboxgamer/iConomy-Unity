package de.sbg.unity.iconomy.Events.Factory;

import de.sbg.unity.iconomy.Factory.Factory;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;

public class RemoveFactoryEvent extends Event implements Cancellable{
    
    private final Player player;
    private final Factory factory;
    
    public RemoveFactoryEvent(Player p, Factory f) {
        this.player = p;
        this.factory = f;
    }

    public Player getPlayer() {
        return player;
    }

    public Factory getFactory() {
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
