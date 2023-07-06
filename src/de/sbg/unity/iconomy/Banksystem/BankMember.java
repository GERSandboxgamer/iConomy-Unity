package de.sbg.unity.iconomy.Banksystem;

import net.risingworld.api.Server;

public class BankMember {

    private final String UID;
    private final Permissions Permissions;

    public BankMember(String uid) {
        UID = uid;
        this.Permissions = new Permissions();
        Permissions.setDefault();
                
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

        public boolean AddMember, RemoveMember, CreateAccount, SendMoney, ChangeOwner, DeleteAccount, UseAccount;

        private void setDefault() {
            AddMember = false;
            RemoveMember = false;
            CreateAccount = false;
            DeleteAccount = false;
            ChangeOwner = false;
            SendMoney = true;
            UseAccount = true;

        }

    }

}
