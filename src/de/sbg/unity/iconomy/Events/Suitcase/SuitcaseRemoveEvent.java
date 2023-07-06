
package de.sbg.unity.iconomy.Events.Suitcase;

import de.sbg.unity.iconomy.Objects.SuitcaseObject;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;


public class SuitcaseRemoveEvent extends Event implements Cancellable{
    
    private final Player player;
    private final SuitcaseObject suitcase;
    private final Cause cause;
    
    public SuitcaseRemoveEvent(Player player, SuitcaseObject suitcase, Cause cause) {
        this.player = player;
        this.cause = cause;
        this.suitcase = suitcase;
    }

    public Cause getCause() {
        return cause;
    }

    public Player getPlayer() {
        return player;
    }

    public SuitcaseObject getSuitcase() {
        return suitcase;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean bln) {
        cancelled = bln;
    }
    
    
    public enum Cause {
        PlayerInteraktion,
        Time,
        Command,
        API;
    }
    
}
