package de.sbg.unity.iconomy.Events.Money;

import de.sbg.unity.iconomy.Banksystem.BankMember;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;


public class RemoveMemberEvent extends Event implements Cancellable{
    
    private final BankMember member;
    private final Player player;
    
    public RemoveMemberEvent(Player p, BankMember m) {
        this.member = m;
        this.player = p;
    }

    public Player getPlayer() {
        return player;
    }

    public BankMember getMember() {
        return member;
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
