package de.sbg.unity.iconomy.Factory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The FactoryMember class represents a member of a factory in the iConomy system.
 * Each member is identified by a unique UID and has associated permissions for
 * performing various actions within the factory, such as adding or removing members,
 * changing ownership, renaming the factory, managing plots, and handling workers.
 */
public class FactoryMember implements Serializable{

    private final String UID;
    private final FactoryMemberPermissions Permissions;

    /**
     * Constructor for creating a new FactoryMember with a specified UID.
     * By default, the member's permissions are set to false.
     *
     * @param UID The unique identifier for the factory member.
     */
    public FactoryMember(String UID) {
        this.UID = UID;
        this.Permissions = new FactoryMemberPermissions();
        Permissions.setDefault();
    }

    /**
     * Gets the UID of the factory member.
     *
     * @return The unique identifier of the factory member.
     */
    public String getUID() {
        return UID;
    }

    /**
     * Gets the permissions associated with the factory member.
     *
     * @return The FactoryMemberPermissions object containing the member's permissions.
     */
    public FactoryMemberPermissions getPermissions() {
        return Permissions;
    }

    /**
     * The FactoryMemberPermissions class represents the permissions that a factory member
     * has within a factory. These permissions include the ability to add or remove members,
     * change ownership, rename the factory, manage plots, and add or remove workers.
     */
    public class FactoryMemberPermissions {

        public boolean AddMember, RemoveMember, ChangeOwner, RenameFactory, ChangePlot, AddWorker, RemoveWorker, 
                EnterPlots, LeavePlots, TeleportOut, TeleportIn, Build;
        public String EnterMessage, LeaveMessage;
        public List<Long> OpenDoors;
        
        
        /**
         * Sets the default permissions for a factory member. All permissions are set to false by default.
         */
        private void setDefault() {
            AddMember = false;
            RemoveMember = false;
            RenameFactory = false;
            ChangeOwner = false;
            ChangePlot = false;
            AddWorker = false;
            RemoveWorker = false;
            EnterPlots = true;
            LeavePlots = true;
            TeleportIn = true;
            TeleportOut = true;
            Build = false;
            EnterMessage = "";
            LeaveMessage = "";
            OpenDoors = new ArrayList<>();
        }
    }
}
