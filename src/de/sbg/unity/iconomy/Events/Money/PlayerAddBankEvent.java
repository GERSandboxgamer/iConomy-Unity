package de.sbg.unity.iconomy.Events.Money;

import de.sbg.unity.iconomy.Banksystem.PlayerAccount;
import net.risingworld.api.events.Cancellable;
import net.risingworld.api.events.Event;
import net.risingworld.api.objects.Player;


public class PlayerAddBankEvent extends Event implements Cancellable{
    
    private final Player player;
    private final PlayerAccount playerAccount;

    public PlayerAddBankEvent(Player player, PlayerAccount pa) {
        this.player = player;
        this.playerAccount = pa;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerAccount getPlayerAccount() {
        return playerAccount;
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
