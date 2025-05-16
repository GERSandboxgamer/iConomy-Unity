package de.sbg.unity.iconomy.GUI.Banksystem;

import de.sbg.unity.iconomy.Banksystem.BankAccount;
import de.sbg.unity.iconomy.Banksystem.BusinessAccount;
import de.sbg.unity.iconomy.Exeptions.NotBusinessAccountExeption;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.events.Listener;
import net.risingworld.api.objects.Player;

public class BusinessPermissions extends MenuElement implements Listener{
    
    private BusinessAccount factoryAccount;
    private final iConomy plugin;
    private final Player player;
    
    public BusinessPermissions(Player player, iConomy plugin) {
        this.plugin = plugin;
        this.player = player;
    }

    @Override
    public void setBankAccount(BankAccount bankAccount) {
        if (bankAccount instanceof BusinessAccount fa) {
            this.factoryAccount = fa;
        } else {
            try {
                throw new NotBusinessAccountExeption();
            } catch (NotBusinessAccountExeption ex) {
                icConsole Console = new icConsole(plugin);
                Console.sendErr(ex.getMessage());
                for (StackTraceElement st : ex.getStackTrace()) {
                    Console.sendErr(st.toString());
                }
            }
        }
    }

    public BusinessAccount getBusinessAccount() {
        return factoryAccount;
    }
    
    
    
}
