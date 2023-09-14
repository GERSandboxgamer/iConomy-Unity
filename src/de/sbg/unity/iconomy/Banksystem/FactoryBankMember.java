package de.sbg.unity.iconomy.Banksystem;

import net.risingworld.api.Server;

/**
 *
 * @hidden  
 */
public class FactoryBankMember {
    
    private final String UID;
    private final Permissions Permissions;

    public FactoryBankMember(String uid) {
        UID = uid;
        this.Permissions = new Permissions();
    }

    public String getUID() {
        return UID;
    }

    public Permissions getPermissions() {
        return Permissions;
    }

    public String getLastKnownMemberName() {
        return Server.getLastKnownPlayerName(UID);
    }
    
    public class Permissions {
        
        public boolean AddMember, RemoveMember, UseMoney, ChangeOwner;

            private void setDefault() {
                AddMember = false;
                RemoveMember = false;
                UseMoney = false;
                ChangeOwner = false;
            }

        
    }
    
}
