package de.sbg.unity.iconomy.Events.Factory;

import de.sbg.unity.iconomy.Banksystem.FactoryBankMember;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;

public class AddFactoryBankMemberEvent extends Event implements Cancellable{
    
    private final Player player;
    private final FactoryBankMember Member;
    
    public AddFactoryBankMemberEvent(Player player, FactoryBankMember member) {
        this.player = player;
        this.Member = member;
    }

    public FactoryBankMember getMember() {
        return Member;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isCancelled() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setCancelled(boolean bln) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
