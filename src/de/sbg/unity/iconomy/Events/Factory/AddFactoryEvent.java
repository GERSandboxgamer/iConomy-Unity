package de.sbg.unity.iconomy.Events.Factory;

import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;

public class AddFactoryEvent extends Event implements Cancellable{
    
    private final Player player;
    
    public AddFactoryEvent(Player p) {
        this.player = p;
    }

    public Player getPlayer() {
        return player;
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
