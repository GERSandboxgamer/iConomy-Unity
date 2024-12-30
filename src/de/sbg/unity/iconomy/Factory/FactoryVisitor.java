package de.sbg.unity.iconomy.Factory;

import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.objects.Player;


public class FactoryVisitor {
    
    private final String UID;
    private final FactoryVisitorPermission permission;
    
    public FactoryVisitor(Player player) {
        this.UID = player.getUID();
        this.permission = new FactoryVisitorPermission();
        permission.setDefault();    
    }
    
    public FactoryVisitor(String uid) {
        this.UID = uid;
        this.permission = new FactoryVisitorPermission();
        permission.setDefault();
    }

    public String getUID() {
        return UID;
    }

    public FactoryVisitorPermission getPermission() {
        return permission;
    }
    
    public class FactoryVisitorPermission {
        
        public boolean AddMember, RemoveMember, ChangeOwner, RenameFactory, ChangePlot, AddWorker, RemoveWorker, 
                EnterPlots, LeavePlots, TeleportOut, TeleportIn, Build;
        public String EnterMessage, LeaveMessage;
        public List<Long> OpenDoors;
        
        
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
            TeleportIn = false;
            TeleportOut = false;
            Build = false;
            EnterMessage = "";
            LeaveMessage = "";
            OpenDoors = new ArrayList<>();
        }
    }
}
