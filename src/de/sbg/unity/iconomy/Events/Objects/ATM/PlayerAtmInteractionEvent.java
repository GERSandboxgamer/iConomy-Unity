package de.sbg.unity.iconomy.Events.Objects.ATM;

import de.sbg.unity.iconomy.Objects.AtmObject;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;


public class PlayerAtmInteractionEvent extends Event implements Cancellable{
    
    private final Player player;
    private final AtmObject ATM;
    private String CancelledReason;
    
    public PlayerAtmInteractionEvent(Player player, AtmObject atm) {
        this.player = player;
        this.ATM = atm;
        CancelledReason = null;
    }

    public Player getPlayer() {
        return player;
    }

    public AtmObject getATM() {
        return ATM;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean bln) {
        cancelled = bln;
    }
    
    public void setCancelledReason(String reason){
        CancelledReason = reason;
    }
    
    public String getCancelledReason(){
        return CancelledReason;
    }
    
}
