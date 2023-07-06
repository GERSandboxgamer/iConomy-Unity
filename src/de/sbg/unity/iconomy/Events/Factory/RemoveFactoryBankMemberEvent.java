/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.sbg.unity.iconomy.Events.Factory;

import de.sbg.unity.iconomy.Banksystem.FactoryBankMember;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;


public class RemoveFactoryBankMemberEvent extends Event implements Cancellable{
    
    private final Player player;
    private final FactoryBankMember Member;
    
    public RemoveFactoryBankMemberEvent(Player player, FactoryBankMember member) {
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
