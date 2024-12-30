package de.sbg.unity.iconomy.Events.Objects.ATM;

import de.sbg.unity.iconomy.Objects.AtmObject;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;

public class PlayerAtmPlaceEvent extends Event implements Cancellable{
    
    private final Player player;
    private final AtmObject atm;
    
    public PlayerAtmPlaceEvent(Player player, AtmObject atm) {
        this.player = player;
        this.atm = atm;
    }

    public Player getPlayer() {
        return player;
    }

    public AtmObject getAtm() {
        return atm;
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
