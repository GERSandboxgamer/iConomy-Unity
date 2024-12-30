package de.sbg.unity.iconomy.Factory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The FactoryWorker class represents a worker in a factory within the iConomy system.
 * Each worker is identified by a unique UID and has associated permissions for
 * entering and leaving plots, teleporting in and out, and an optional entry message.
 */
public class FactoryWorker implements Serializable{
    
    private final String UID;
    private final WorkerPermissions Permissions;
    
    /**
     * Constructor for creating a new FactoryWorker with a specified UID.
     * The worker's permissions are initialized to default values.
     *
     * @param uid The unique identifier for the factory worker.
     */
    public FactoryWorker(String uid) {
        this.UID = uid;
        this.Permissions = new WorkerPermissions();
        Permissions.setDefault();
    }

    /**
     * Gets the UID of the factory worker.
     *
     * @return The unique identifier of the factory worker.
     */
    public String getUID() {
        return UID;
    }

    /**
     * Gets the permissions associated with the factory worker.
     *
     * @return The WorkerPermissions object containing the worker's permissions.
     */
    public WorkerPermissions getPermissions() {
        return Permissions;
    }
    
    /**
     * The WorkerPermissions class represents the permissions that a factory worker
     * has within a factory. These permissions include the ability to enter and leave
     * plots, teleport in and out, and an optional message to display when entering the plot.
     */
    public class WorkerPermissions {
        
        public boolean AddMember, RemoveMember, ChangeOwner, RenameFactory, ChangePlot, AddWorker, RemoveWorker, 
                EnterPlots, LeavePlots, TeleportOut, TeleportIn, Build;
        public String EnterMessage, LeaveMessage;
        public List<Long> OpenDoors;

        /**
         * Sets the default permissions for a factory worker. By default, workers are allowed
         * to enter and leave plots, and teleport in and out. The default entry message is an empty string.
         */
        private void setDefault() {
            EnterPlots = true;
            LeavePlots = true;
            TeleportIn = true;
            TeleportOut = true;
            EnterMessage = null;
            LeaveMessage = null;
            AddMember = false;
            RemoveMember = false;
            RenameFactory = false;
            ChangeOwner = false;
            ChangePlot = false;
            AddWorker = false;
            RemoveWorker = false;
            Build = false;
            OpenDoors = new ArrayList<>();
        }
    }
}
