package de.sbg.unity.iconomy.Factory;

import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.Server;
import net.risingworld.api.objects.Area;


public class FactoryPlayerPermissions {
    
    public FactoryPlayerPermissions() {
        setDefault();
        Area area = Server.getArea(1);
    }
    
    public boolean AddMember, RemoveMember, ChangeOwner, RenameFactory, ChangePlot, AddWorker, RemoveWorker, 
                EnterPlots, LeavePlots, TeleportOut, TeleportIn, Build;
        public String EnterMessage, LeaveMessage;
        public List<Long> OpenDoors;

        
        private void setDefault() {
            EnterPlots = false;
            LeavePlots = false;
            TeleportIn = false;
            TeleportOut = false;
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
