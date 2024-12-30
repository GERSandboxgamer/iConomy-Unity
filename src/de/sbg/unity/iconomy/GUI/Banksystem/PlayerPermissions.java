
package de.sbg.unity.iconomy.GUI.Banksystem;

import de.sbg.unity.iconomy.Banksystem.BankAccount;
import de.sbg.unity.iconomy.Banksystem.PlayerAccount;
import de.sbg.unity.iconomy.Exeptions.NotPlayerAccountExeption;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.events.Listener;
import net.risingworld.api.objects.Player;

public class PlayerPermissions extends MenuElement implements Listener{

    private PlayerAccount playerAccount;
    private final Player player;
    private final iConomy plugin;
    
    public PlayerPermissions(Player player, iConomy plugin) {
        this.plugin = plugin;
        this.player = player;
    }
    
    @Override
    public void setBankAccount(BankAccount bankAccount) {
        if (bankAccount instanceof PlayerAccount pa) {
            this.playerAccount = pa;
            
        } else {
            try {
                throw new NotPlayerAccountExeption();
            } catch(NotPlayerAccountExeption ex) {
                icConsole Console = new icConsole(plugin);
                Console.sendErr(ex.getMessage());
                for (StackTraceElement st : ex.getStackTrace()) {
                    Console.sendErr(st.toString());
                }
            }
        }
    }
    
}
