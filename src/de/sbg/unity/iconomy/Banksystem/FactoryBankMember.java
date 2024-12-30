package de.sbg.unity.iconomy.Banksystem;

import java.io.Serializable;
import net.risingworld.api.Server;

/**
 * The FactoryBankMember class represents a member of a factory's bank account 
 * within the iConomy system. Each member has a unique identifier (UID) and 
 * associated permissions that define their capabilities in managing and using 
 * the bank account.
 */
public class FactoryBankMember implements Serializable{

    private final String UID;
    private final Permissions Permissions;

    /**
     * Constructor for creating a new FactoryBankMember with a specified UID.
     * The member's permissions are initialized to default values.
     * 
     * @param uid The unique identifier (UID) for the factory bank member.
     */
    public FactoryBankMember(String uid) {
        UID = uid;
        this.Permissions = new Permissions();
        Permissions.setDefault();
    }

    /**
     * Gets the unique identifier (UID) of the factory bank member.
     * 
     * @return The UID of the factory bank member.
     */
    public String getUID() {
        return UID;
    }

    /**
     * Gets the permissions associated with the factory bank member.
     * 
     * @return The Permissions object representing the member's permissions.
     */
    public Permissions getPermissions() {
        return Permissions;
    }

    /**
     * Retrieves the last known name of the factory bank member based on their UID.
     * 
     * @return The last known name of the factory bank member as a String.
     */
    public String getLastKnownMemberName() {
        return Server.getLastKnownPlayerName(UID);
    }

    /**
     * The Permissions class defines the actions a factory bank member can perform
     * on a factory's bank account. These actions include adding/removing members, 
     * using money, changing ownership, and changing permissions.
     */
    public class Permissions implements Serializable{

        public boolean AddMember, RemoveMember, UseMoney, ChangeOwner, ChangePermissions, UseAccount;

        /**
         * Sets the default permissions for a factory bank member.
         * By default, members can use the account, but they cannot add/remove members, 
         * use money, or change ownership or permissions.
         */
        private void setDefault() {
            AddMember = false;
            RemoveMember = false;
            UseMoney = false;
            ChangeOwner = false;
            ChangePermissions = false;
            UseAccount = true;
        }
    }
}
