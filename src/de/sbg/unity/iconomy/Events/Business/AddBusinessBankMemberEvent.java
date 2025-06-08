package de.sbg.unity.iconomy.Events.Business;

import de.sbg.unity.iconomy.Banksystem.BusinessBankMember;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;

public class AddBusinessBankMemberEvent extends Event implements Cancellable{
    
    private final Player player;
    private final BusinessBankMember Member;
    
    public AddBusinessBankMemberEvent(Player player, BusinessBankMember member) {
        this.player = player;
        this.Member = member;
    }

    public BusinessBankMember getMember() {
        return Member;
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
