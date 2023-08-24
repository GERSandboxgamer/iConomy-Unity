package de.sbg.unity.iconomy.Banksystem;

import de.sbg.unity.iconomy.Events.Money.AddMemberEvent;
import de.sbg.unity.iconomy.Events.Money.RemoveMemberEvent;
import de.sbg.unity.iconomy.Utils.AccountTyp;
import de.sbg.unity.iconomy.iConomy;
import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.Server;
import net.risingworld.api.objects.Player;

public class PlayerAccount  extends BankAccount{

    private final List<BankMember> Members;
    private final iConomy plugin;
    private final String OwnerUID;

    public PlayerAccount(iConomy plugin, String OwnerUID) {
        super(plugin, AccountTyp.PlayerAccount);
        this.Members = new ArrayList<>();
        this.plugin = plugin;
        this.OwnerUID = OwnerUID;
        this.setMoney(plugin.Config.PlayerBankStartAmounth);
    }

    public List<BankMember> getMembers() {
        return Members;
    }

    public List<String> getMemberNames() {
        List<String> result = new ArrayList<>();
        for (BankMember m : getMembers()) {
            String name = Server.getPlayerByUID(m.getUID()).getName();
            result.add(name);
        }
        return result;
    }

    public boolean addMember(Player sender, BankMember m) {
        AddMemberEvent evt = new AddMemberEvent(sender, m);
        plugin.triggerEvent(evt);
        if (!evt.isCancelled()) {
            return Members.add(m);
        }
        return false;
    }

    public BankMember addMember(Player sender, Player member) {
        return addMember(sender, member.getUID());
    }

    public BankMember addMember(Player sender, String uid) {
        BankMember m = new BankMember(uid);
        AddMemberEvent evt = new AddMemberEvent(sender, m);
        plugin.triggerEvent(evt);
        if (!evt.isCancelled()) {
            Members.add(m);
            return m;
        }
        return null;
    }

    public boolean removeMember(Player sender, BankMember m) {
        RemoveMemberEvent evt = new RemoveMemberEvent(sender, m);
        plugin.triggerEvent(evt);
        if (!evt.isCancelled()) {
            return Members.remove(m);
        }
        return false;
    }

    public boolean removeMember(Player sender, Player member) {
        return removeMember(sender, member.getUID());
    }

    public boolean removeMember(Player sender, String uid) {
        BankMember m = getMember(uid);
        RemoveMemberEvent evt = new RemoveMemberEvent(sender, m);
        if (m != null) {
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                return Members.remove(m);
            }
        }
        return false;
    }

    public BankMember getMember(Player player) {
        return getMember(player.getUID());
    }

    public BankMember getMember(String uid) {
        for (BankMember m : Members) {
            if (m.getUID().equals(uid)) {
                return m;
            }
        }
        return null;
    }

    public String getOwnerUID() {
        return OwnerUID;
    }

    public String getLastKnownOwnerName() {
        return Server.getLastKnownPlayerName(OwnerUID);
    }    
    
    
    
}
