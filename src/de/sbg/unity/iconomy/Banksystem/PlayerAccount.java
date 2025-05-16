package de.sbg.unity.iconomy.Banksystem;

import de.sbg.unity.iconomy.Events.Money.AddMemberEvent;
import de.sbg.unity.iconomy.Events.Money.RemoveMemberEvent;
import de.sbg.unity.iconomy.Utils.AccountType;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.Server;
import net.risingworld.api.objects.Player;

/**
 * The PlayerAccount class represents a bank account owned by a player in the iConomy system.
 * It extends the BankAccount class and includes additional functionality to manage members, 
 * allowing multiple players to interact with the account depending on their permissions.
 */
public class PlayerAccount extends BankAccount {

    private final List<BankMember> Members;
    private final iConomy plugin;
    private final String OwnerUID;
    private final icConsole Console;

    /**
     * Constructor for creating a PlayerAccount associated with a player.
     * 
     * @param plugin The iConomy plugin instance.
     * @param Console The console used for logging and output.
     * @param OwnerUID The unique identifier (UID) of the player who owns the account.
     */
    public PlayerAccount(iConomy plugin, icConsole Console, String OwnerUID) {
        super(plugin, Console, AccountType.PlayerAccount);
        this.Members = new ArrayList<>();
        this.plugin = plugin;
        this.Console = Console;
        this.OwnerUID = OwnerUID;
        this.setMoney(plugin.Config.PlayerBankStartAmount);
        
    }

    /**
     * Retrieves the list of members who have access to this player account.
     * 
     * @return A List of BankMember objects representing the members.
     */
    public List<BankMember> getMembers() {
        return Members;
    }

    /**
     * Adds multiple members to the player account.
     * 
     * @param mem A List of BankMember objects to be added to the account.
     */
    public void addAllMembers(List<BankMember> mem) {
        mem.forEach(m -> {
            getMembers().add(m);
        });
    }

    /**
     * Retrieves the names of all members in the player account.
     * 
     * @return A List of Strings representing the names of the members.
     */
    public List<String> getMemberNames() {
        List<String> result = new ArrayList<>();
        getMembers().forEach((t) -> {
            result.add(t.getLastKnownMemberName());
        });
        return result;
    }
    
    /**
     * Retrieves the UIDs of all members in the player account.
     * 
     * @return A List of Strings representing the UIDs of the members.
     */
    public List<String> getMembersUID(){
        List<String> result = new ArrayList<>();
        getMembers().forEach((t) -> {
            result.add(t.getUID());
        });
        return result;
    }
    
    public boolean addMember(BankMember m) {
        return Members.add(m);
    }

    /**
     * Adds a new member to the player account.
     * 
     * @param sender The player who is adding the new member.
     * @param m The BankMember to be added.
     * @return True if the member was successfully added, false otherwise.
     */
    public boolean addMember(Player sender, BankMember m) {
        AddMemberEvent evt = new AddMemberEvent(sender, m);
        plugin.triggerEvent(evt);
        if (!evt.isCancelled()) {
            return Members.add(m);
        }
        return false;
    }

    /**
     * Adds a new member to the player account using the Player object.
     * 
     * @param sender The player who is adding the new member.
     * @param member The player to be added as a member.
     * @return The BankMember object that was added.
     */
    public BankMember addMember(Player sender, Player member) {
        return addMember(sender, member.getUID());
    }

    /**
     * Adds a new member to the player account using their UID.
     * 
     * @param sender The player who is adding the new member.
     * @param uid The UID of the player to be added as a member.
     * @return The BankMember object that was added, or null if the event was cancelled.
     */
    public BankMember addMember(Player sender, String uid) {
        BankMember m = new BankMember(uid);
        m.setDefaultPermission();
        AddMemberEvent evt = new AddMemberEvent(sender, m);
        plugin.triggerEvent(evt);
        if (!evt.isCancelled()) {
            Members.add(m);
            return m;
        }
        return null;
    }

    /**
     * Removes a member from the player account.
     * 
     * @param sender The player who is removing the member.
     * @param m The BankMember to be removed.
     * @return True if the member was successfully removed, false otherwise.
     */
    public boolean removeMember(Player sender, BankMember m) {
        RemoveMemberEvent evt = new RemoveMemberEvent(sender, m);
        plugin.triggerEvent(evt);
        if (!evt.isCancelled()) {
            return Members.remove(m);
        }
        return false;
    }

    /**
     * Removes a member from the player account using the Player object.
     * 
     * @param sender The player who is removing the member.
     * @param member The player to be removed as a member.
     * @return True if the member was successfully removed, false otherwise.
     */
    public boolean removeMember(Player sender, Player member) {
        return removeMember(sender, member.getUID());
    }

    /**
     * Removes a member from the player account using their UID.
     * 
     * @param sender The player who is removing the member.
     * @param uid The UID of the player to be removed as a member.
     * @return True if the member was successfully removed, false otherwise.
     */
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

    /**
     * Checks if a player is a member of the player account using their UID.
     * 
     * @param uid The UID of the player to check.
     * @return True if the player is a member, false otherwise.
     */
    public boolean isMember(String uid) {
        return getMember(uid) != null;
    }

    /**
     * Checks if a player is a member of the player account using the Player object.
     * 
     * @param player The player to check.
     * @return True if the player is a member, false otherwise.
     */
    public boolean isMember(Player player) {
        return getMember(player.getUID()) != null;
    }

    /**
     * Retrieves the BankMember object representing the player.
     * 
     * @param player The player whose membership is being retrieved.
     * @return The BankMember object, or null if the player is not a member.
     */
    public BankMember getMember(Player player) {
        return getMember(player.getUID());
    }

    /**
     * Retrieves the BankMember object representing a member by their UID.
     * 
     * @param uid The UID of the member to retrieve.
     * @return The BankMember object, or null if the player is not a member.
     */
    public BankMember getMember(String uid) {
        for (BankMember m : Members) {
            if (m.getUID().equals(uid)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Gets the UID of the owner of the player account.
     * 
     * @return The UID of the owner.
     */
    public String getOwnerUID() {
        return OwnerUID;
    }

    /**
     * Gets the last known name of the account owner based on their UID.
     * 
     * @return The last known name of the account owner as a String.
     */
    public String getLastKnownOwnerName() {
        return Server.getLastKnownPlayerName(OwnerUID);
    }
}
