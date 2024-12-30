package de.sbg.unity.iconomy.GUI.Banksystem;

import de.sbg.unity.iconomy.Banksystem.BankAccount;
import de.sbg.unity.iconomy.Banksystem.FactoryAccount;
import de.sbg.unity.iconomy.Exeptions.NotFactoryAccountExeption;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import net.risingworld.api.events.Listener;
import net.risingworld.api.objects.Player;

public class FactoryPermissions extends MenuElement implements Listener{
    
    private FactoryAccount factoryAccount;
    private final iConomy plugin;
    private final Player player;
    
    public FactoryPermissions(Player player, iConomy plugin) {
        this.plugin = plugin;
        this.player = player;
    }

    @Override
    public void setBankAccount(BankAccount bankAccount) {
        if (bankAccount instanceof FactoryAccount fa) {
            this.factoryAccount = fa;
        } else {
            try {
                throw new NotFactoryAccountExeption();
            } catch (NotFactoryAccountExeption ex) {
                icConsole Console = new icConsole(plugin);
                Console.sendErr(ex.getMessage());
                for (StackTraceElement st : ex.getStackTrace()) {
                    Console.sendErr(st.toString());
                }
            }
        }
    }

    public FactoryAccount getFactoryAccount() {
        return factoryAccount;
    }
    
    
    
}
