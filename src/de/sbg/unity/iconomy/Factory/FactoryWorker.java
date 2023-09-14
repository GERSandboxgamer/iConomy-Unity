package de.sbg.unity.iconomy.Factory;

/**
 * 
 * @hidden
 */
public class FactoryWorker {
    
    private final String UID;
    private final WorkerPermissions Permissions;
            
    public FactoryWorker(String uid) {
        this.UID = uid;
        this.Permissions = new WorkerPermissions();
        Permissions.setDefault();
    }

    public String getUID() {
        return UID;
    }

    public WorkerPermissions getPermissions() {
        return Permissions;
    }
    
    public class WorkerPermissions {
        
        
        public boolean EnterPlot, LeavePlot, TeleportOut, TeleportIn;
        public String EnderMessage;

        private void setDefault() {
            EnterPlot = true;
            LeavePlot = true;
            TeleportIn = true;
            TeleportOut = true;
            EnderMessage = "";
        }
        
    }
    
}
