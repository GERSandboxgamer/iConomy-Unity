package de.sbg.unity.iconomy.Banksystem;

import java.io.Serializable;
import net.risingworld.api.Server;

/**
 * The BankMember class represents a member of a bank account in the iConomy system.
 * Each member has a unique identifier (UID) and a set of permissions that define
 * what actions they can perform on the bank account, such as adding/removing members,
 * sending money, and changing ownership.
 */
public class BankMember implements Serializable{

    private final String UID;
    private final Permissions Permissions;

    /**
     * Constructor for creating a new BankMember with a specified UID.
     * By default, the member's permissions are initialized.
     * 
     * @param uid The unique identifier (UID) for the bank member.
     */
    public BankMember(String uid) {
        UID = uid;
        this.Permissions = new Permissions();
        Permissions.setDefault();
    }

    /**
     * Gets the UID of the bank member.
     * 
     * @return The UID of the bank member.
     */
    public String getUID() {
        return UID;
    }

    /**
     * Gets the permissions associated with the bank member.
     * 
     * @return The Permissions object representing the member's permissions.
     */
    public Permissions getPermissions() {
        return Permissions;
    }

    /**
     * Retrieves the last known name of the bank member based on their UID.
     * 
     * @return The last known name of the bank member as a String.
     */
    public String getLastKnownMemberName() {
        return Server.getLastKnownPlayerName(UID);
    }

    /**
     * The Permissions class defines the set of actions a bank member can perform
     * on a bank account. These actions include adding/removing members, creating 
     * accounts, sending money, and changing ownership or permissions.
     */
    public class Permissions implements Serializable{

        public boolean AddMember, RemoveMember, CreateAccount, SendMoney, ChangeOwner, DeleteAccount, UseAccount, ChangePermissions;

        /**
         * Sets the default permissions for a bank member. By default, members can send 
         * money and use the account, but cannot add/remove members or change account settings.
         */
        private void setDefault() {
            AddMember = false;
            RemoveMember = false;
            CreateAccount = false;
            DeleteAccount = false;
            ChangeOwner = false;
            SendMoney = true;
            UseAccount = true;
            ChangePermissions = false;
        }
    }
}
