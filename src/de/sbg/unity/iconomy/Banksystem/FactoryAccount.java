package de.sbg.unity.iconomy.Banksystem;

import de.sbg.unity.iconomy.Events.Factory.AddFactoryBankMemberEvent;
import de.sbg.unity.iconomy.Factory.Factory;
import de.sbg.unity.iconomy.Utils.AccountTyp;
import de.sbg.unity.iconomy.iConomy;
import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.objects.Player;

public class FactoryAccount extends BankAccount{

    private final Factory Factory;
    private final iConomy plugin;
    private final List<FactoryBankMember> Members;
    private final int AccountID;

    public FactoryAccount(iConomy plugin, Factory f, int accountID) {
        super(plugin,AccountTyp.FactoryAccount);
        this.plugin = plugin;
        this.Factory = f;
        this.AccountID = accountID;
        this.Members = new ArrayList<>();
        this.setMoney(plugin.Config.FactoryBankStartAmounth);
    }

    public int getAccountID() {
        return AccountID;
    }

    public Factory getFactory() {
        return Factory;
    }
    
    public boolean isOwner(Player player) {
        return isOwner(player.getUID());
    }

    public boolean isOwner(String UID) {
        return Factory.getOwners().contains(UID);
    }

    public boolean isMember(Player player) {
        return isMember(player.getUID());
    }

    public boolean isMember(String UID) {
        for (FactoryBankMember m : Members) {
            if (m.getUID().matches(UID)) {
                return true;
            }
        }
        return false;
    }

    

    public boolean addMember(Player player, Player member) {
        return addMember(player, member.getUID());
    }

    public boolean addMember(Player player, String uid) {
        FactoryBankMember m = new FactoryBankMember(uid);
        AddFactoryBankMemberEvent evt = new AddFactoryBankMemberEvent(player, m);
        plugin.triggerEvent(evt);
        if (!evt.isCancelled()) {
            if (!isMember(uid)) {
                Members.add(m);
                return true;
            }
        }
        return false;
    }

    public boolean addMember(Player player, FactoryBankMember m) {
        if (!Members.stream().noneMatch(m2 -> (m2.getUID().matches(m.getUID())))) {
            return false;
        }
        AddFactoryBankMemberEvent evt = new AddFactoryBankMemberEvent(player, m);
        plugin.triggerEvent(evt);
        if (!evt.isCancelled()) {
            Members.add(m);
            return true;
        }
        return false;
    }

    public List<FactoryBankMember> getMembers() {
        return Members;
    }

}
