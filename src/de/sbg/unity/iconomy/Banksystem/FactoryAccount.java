package de.sbg.unity.iconomy.Banksystem;

import de.sbg.unity.iconomy.Events.Factory.AddFactoryBankMemberEvent;
import de.sbg.unity.iconomy.Events.Factory.RemoveFactoryBankMemberEvent;
import de.sbg.unity.iconomy.Factory.Factory;
import de.sbg.unity.iconomy.Utils.AccountType;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.objects.Player;

/**
 * The FactoryAccount class represents a bank account associated with a factory
 * in the iConomy system. It extends the BankAccount class and includes
 * additional functionality to manage members and ownership within the context
 * of a factory. Factory bank members are also handled, allowing for customized
 * permissions and roles.
 */
public class FactoryAccount extends BankAccount {

    private final Factory Factory;
    private final iConomy plugin;
    private final icConsole Console;
    private final List<FactoryBankMember> Members;
    private final int AccountID;

    /**
     * Constructor for creating a FactoryAccount associated with a factory.
     *
     * @param plugin The iConomy plugin instance.
     * @param Console The console used for logging and output.
     * @param f The factory associated with this bank account.
     * @param id The unique ID of the factory bank account.
     */
    public FactoryAccount(iConomy plugin, icConsole Console, Factory f, int id) {
        super(plugin, Console, AccountType.FactoryAccount);
        this.AccountID = id;
        this.Factory = f;
        this.Members = new ArrayList<>();
        this.setMoney(plugin.Config.FactoryBankStartAmount);
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
     * @return The Factory object associated with the account.
     */
    public Factory getFactory() {
        return Factory;
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
        return Factory.getOwners().contains(UID);
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
        for (FactoryBankMember m : Members) {
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
     * @return The FactoryBankMember object representing the player's
     * membership.
     */
    public FactoryBankMember getMember(Player player) {
        return getMember(player.getUID());
    }

    /**
     * Gets a factory bank member based on their UID.
     *
     * @param uid The unique identifier (UID) of the member.
     * @return The FactoryBankMember object representing the member.
     */
    public FactoryBankMember getMember(String uid) {
        for (FactoryBankMember m : Members) {
            if (m.getUID().equals(uid)) {
                return m;
            }
        }
        return null;
    }

    public void addAllMembers(List<FactoryBankMember> memb) {
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

    /**
     * Adds a new FactoryBankMember to the factory's bank account.
     *
     * @param player The player adding the member.
     * @param m The FactoryBankMember object representing the new member.
     * @return True if the member was successfully added, false otherwise.
     */
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

    public boolean removeMember(Player player, FactoryBankMember member) {
        if (isMember(player)) {
            RemoveFactoryBankMemberEvent evt = new RemoveFactoryBankMemberEvent(player, member);
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
            FactoryBankMember member = getMember(uid);
            RemoveFactoryBankMemberEvent evt = new RemoveFactoryBankMemberEvent(player, member);
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
            FactoryBankMember m = getMember(member);
            RemoveFactoryBankMemberEvent evt = new RemoveFactoryBankMemberEvent(player, m);
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
     * @return A List of FactoryBankMember objects representing the members.
     */
    public List<FactoryBankMember> getMembers() {
        return Members;
    }

}
