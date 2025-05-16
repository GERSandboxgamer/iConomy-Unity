package de.sbg.unity.iconomy.Banksystem;

import de.sbg.unity.iconomy.Events.Business.AddBusinessBankMemberEvent;
import de.sbg.unity.iconomy.Events.Business.RemoveBusinessBankMemberEvent;
import de.sbg.unity.iconomy.Business.Business;
import de.sbg.unity.iconomy.Utils.AccountType;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.objects.Player;

/**
 * The BusinessAccount class represents a bank account associated with a factory
 * in the iConomy system. It extends the BankAccount class and includes
 * additional functionality to manage members and ownership within the context
 * of a factory. Business bank members are also handled, allowing for customized
 * permissions and roles.
 */
public class BusinessAccount extends BankAccount {

    private final Business Business;
    private final iConomy plugin;
    private final icConsole Console;
    private final List<BusinessBankMember> Members;
    private final int AccountID;

    /**
     * Constructor for creating a BusinessAccount associated with a factory.
     *
     * @param plugin The iConomy plugin instance.
     * @param Console The console used for logging and output.
     * @param f The factory associated with this bank account.
     * @param id The unique ID of the factory bank account.
     */
    public BusinessAccount(iConomy plugin, icConsole Console, Business f, int id) {
        super(plugin, Console, AccountType.BusinessAccount);
        this.AccountID = id;
        this.Business = f;
        this.Members = new ArrayList<>();
        this.setMoney(plugin.Config.BusinessBankStartAmount);
        this.plugin = plugin;
        this.Console = Console;
    }

    /**
     * Gets the unique ID of the factory bank account.
     *
     * @return The ID of the factory bank account.
     */
    public int getAccountID() {
        return AccountID;
    }

    /**
     * Gets the factory associated with the bank account.
     *
     * @return The Business object associated with the account.
     */
    public Business getBusiness() {
        return Business;
    }

    /**
     * Checks if a player is an owner of the factory.
     *
     * @param player The player to check.
     * @return True if the player is an owner, false otherwise.
     */
    public boolean isOwner(Player player) {
        return isOwner(player.getUID());
    }

    /**
     * Checks if a player, identified by their UID, is an owner of the factory.
     *
     * @param UID The unique identifier (UID) of the player.
     * @return True if the player is an owner, false otherwise.
     */
    public boolean isOwner(String UID) {
        return Business.getOwners().contains(UID);
    }

    /**
     * Checks if a player is a member of the factory's bank account.
     *
     * @param player The player to check.
     * @return True if the player is a member, false otherwise.
     */
    public boolean isMember(Player player) {
        return isMember(player.getUID());
    }

    /**
     * Checks if a player, identified by their UID, is a member of the factory's
     * bank account.
     *
     * @param UID The unique identifier (UID) of the player.
     * @return True if the player is a member, false otherwise.
     */
    public boolean isMember(String UID) {
        for (BusinessBankMember m : Members) {
            if (m.getUID().matches(UID)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a factory bank member based on the player's UID.
     *
     * @param player The player whose membership is being retrieved.
     * @return The BusinessBankMember object representing the player's
     * membership.
     */
    public BusinessBankMember getMember(Player player) {
        return getMember(player.getUID());
    }

    /**
     * Gets a factory bank member based on their UID.
     *
     * @param uid The unique identifier (UID) of the member.
     * @return The BusinessBankMember object representing the member.
     */
    public BusinessBankMember getMember(String uid) {
        for (BusinessBankMember m : Members) {
            if (m.getUID().equals(uid)) {
                return m;
            }
        }
        return null;
    }

    public void addAllMembers(List<BusinessBankMember> memb) {
        Members.addAll(memb);
    }

    /**
     * Adds a new member to the factory's bank account.
     *
     * @param player The player adding the member.
     * @param member The player to be added as a member.
     * @return True if the member was successfully added, false otherwise.
     */
    public boolean addMember(Player player, Player member) {
        return addMember(player, member.getUID());
    }

    /**
     * Adds a new member to the factory's bank account by UID.
     *
     * @param player The player adding the member.
     * @param uid The unique identifier (UID) of the new member.
     * @return True if the member was successfully added, false otherwise.
     */
    public boolean addMember(Player player, String uid) {
        BusinessBankMember m = new BusinessBankMember(uid);
        AddBusinessBankMemberEvent evt = new AddBusinessBankMemberEvent(player, m);
        plugin.triggerEvent(evt);
        if (!evt.isCancelled()) {
            if (!isMember(uid)) {
                Members.add(m);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a new BusinessBankMember to the factory's bank account.
     *
     * @param player The player adding the member.
     * @param m The BusinessBankMember object representing the new member.
     * @return True if the member was successfully added, false otherwise.
     */
    public boolean addMember(Player player, BusinessBankMember m) {
        if (!Members.stream().noneMatch(m2 -> (m2.getUID().matches(m.getUID())))) {
            return false;
        }
        AddBusinessBankMemberEvent evt = new AddBusinessBankMemberEvent(player, m);
        plugin.triggerEvent(evt);
        if (!evt.isCancelled()) {
            Members.add(m);
            return true;
        }
        return false;
    }

    public boolean removeMember(Player player, BusinessBankMember member) {
        if (isMember(player)) {
            RemoveBusinessBankMemberEvent evt = new RemoveBusinessBankMemberEvent(player, member);
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                Members.remove(member);
                return true;
            }
        }
        return false;
    }

    public boolean removeMember(Player player, String uid) {
        if (isMember(player)) {
            BusinessBankMember member = getMember(uid);
            RemoveBusinessBankMemberEvent evt = new RemoveBusinessBankMemberEvent(player, member);
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                Members.remove(member);
                return true;
            }
        }
        return false;
    }

    public boolean removeMember(Player player, Player member) {
        if (isMember(player)) {
            BusinessBankMember m = getMember(member);
            RemoveBusinessBankMemberEvent evt = new RemoveBusinessBankMemberEvent(player, m);
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                Members.remove(m);
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a list of all members in the factory's bank account.
     *
     * @return A List of BusinessBankMember objects representing the members.
     */
    public List<BusinessBankMember> getMembers() {
        return Members;
    }

}
