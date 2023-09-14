package de.sbg.unity.iconomy.Factory;

/**
 * 
 * @hidden
 */
public class FactoryMember {

    private final String UID;
    private final FactoryMemberPermissions Permissions;

    public FactoryMember(String UID) {
        this.UID = UID;
        this.Permissions = new FactoryMemberPermissions();
        Permissions.setDefault();
    }

    public String getUID() {
        return UID;
    }

    public FactoryMemberPermissions getPermissions() {
        return Permissions;
    }

    public class FactoryMemberPermissions {

        public boolean AddMember, RemoveMember, ChangeOwner, RenameFactory, ChangePlot, AddWorker, RemoveWorker;

        private void setDefault() {
            AddMember = false;
            RemoveMember = false;
            RenameFactory = false;
            ChangeOwner = false;
            ChangePlot = false;
            AddWorker = false;
            RemoveWorker = false;
        }

    }

}
