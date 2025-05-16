package de.sbg.unity.iconomy.Business;

import de.sbg.unity.iconomy.Banksystem.BusinessAccount;
import de.sbg.unity.iconomy.Events.Business.AddBusinessMemberEvent;
import de.sbg.unity.iconomy.Events.Business.AddOwnerEvent;
import de.sbg.unity.iconomy.Events.Business.BusinessAddCashEvent;
import de.sbg.unity.iconomy.Events.Business.BusinessRemoveCashEvent;
import de.sbg.unity.iconomy.Events.Business.RemoveBusinessMemberEvent;
import de.sbg.unity.iconomy.Events.Business.RemoveOwnerEvent;
import de.sbg.unity.iconomy.Utils.BusinessPermission;
import de.sbg.unity.iconomy.Utils.TransferResult;
import de.sbg.unity.iconomy.iConomy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.risingworld.api.Server;
import net.risingworld.api.objects.Area;
import net.risingworld.api.objects.Player;
import net.risingworld.api.utils.Quaternion;
import net.risingworld.api.utils.Vector3f;

/**
 * The Business class represents a business within the iConomy system. It
 * handles the management of business attributes such as owners, workers,
 * members, cash balance, teleport points, plots, and item prices. The class
 * also integrates with the event system to trigger events when changes are made
 * to the business, including adding/removing cash, owners, members, workers,
 * and plots.
 */
public class Business {

    private final List<Area> Plots;
    private BusinessAccount BusinessBankAccount;
    private final iConomy plugin;
    private final List<String> Owners, Infos;
    private String Name;
    private long Cash, ChestUID;
    private final List<BusinessMember> Members;
    private final int ID;
    private Vector3f Telepoint;
    private Quaternion TelepointRotation;
    private final String businessBankID;

    public NoBusinessPlayerPermission noBusinessPlayerPermission;

    /**
     * Constructor to create a new business.
     *
     * @param plugin The iConomy plugin instance.
     * @param Name The name of the business.
     * @param ID The unique ID of the business.
     * @deprecated Use: {@link de.sbg.unity.iconomy.Business.BusinessSystem#addNewBusiness}
     */
    @Deprecated
    public Business(iConomy plugin, String Name, int ID) {
        this.plugin = plugin;
        this.Plots = new ArrayList<>();
        this.Owners = new ArrayList<>();
        this.Members = new ArrayList<>();
        this.Infos = new ArrayList<>();
        this.Cash = 0;
        this.Name = Name;
        this.ID = ID;
        this.businessBankID = generateUniqueBusinessBankID();
    }

    /**
     * For internal use only (e.g. loading from the database). 
     * Should not be used directly.
     * @param plugin
     * @param Name
     * @param ID
     * @param businessBankID
     * @deprecated Use: {@link de.sbg.unity.iconomy.Business.BusinessSystem#addNewBusiness}
     */
    @Deprecated
    public Business(iConomy plugin, String Name, int ID, String businessBankID) {
        this.plugin = plugin;
        this.Plots = new ArrayList<>();
        this.Owners = new ArrayList<>();
        this.Members = new ArrayList<>();
        this.Infos = new ArrayList<>();
        this.Cash = 0;
        this.Name = Name;
        this.ID = ID;
        this.businessBankID = businessBankID;
    }

    private String generateUniqueBusinessBankID() {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final int length = 8;
        String code;
        Random random = new Random();

        while (true) {
            // 1. Zufälligen Code generieren
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                sb.append(characters.charAt(random.nextInt(characters.length())));
            }
            code = sb.toString();

            if (!plugin.Business.businessBankID.contains(code)) {
                plugin.Business.businessBankID.add(code);
                return code;
            }
        }
    }

    public String getBusinessBankID() {
        return businessBankID;
    }

    public void addAllMembers(List<BusinessMember> members) {
        Members.addAll(members);
    }

    public void addAllOwners(List<String> owners) {
        Owners.addAll(owners);
    }

    public void addAllInfos(List<String> infos) {
        Infos.addAll(infos);
    }

    public void addAllPlots(List<Long> plots) {
        for (long l : plots) {
            Area a = Server.getArea(l);
            if (a != null) {
                Plots.add(a);
            }
        }
    }

    // Business Identification
    /**
     * Gets the business ID.
     *
     * @return The ID of the business.
     */
    public int getID() {
        return ID;
    }

    // Cash Management
    /**
     * Gets the business's cash balance.
     *
     * @return The cash balance of the business.
     */
    public long getCash() {
        return Cash;
    }

    /**
     * Sets the business's cash balance.
     *
     * @param Cash The new cash balance.
     */
    public void setCash(long Cash) {
        this.Cash = Cash;
    }

    /**
     * Adds cash to the business.
     *
     * @param cash The amount of cash to add.
     * @param reason The reason for adding cash.
     * @return TransferResult indicating the result of the cash addition.
     */
    public TransferResult addCash(long cash, BusinessAddCashEvent.Reason reason) {
        BusinessAddCashEvent evt = new BusinessAddCashEvent(cash, reason);
        plugin.triggerEvent(evt);
        if (!evt.isCancelled()) {
            this.Cash += evt.getAmount();
            return TransferResult.Successful;
        }
        return TransferResult.EventCancel;
    }

    /**
     * Adds cash to the business by a player.
     *
     * @param player The player adding the cash.
     * @param cash The amount of cash to add.
     * @param reason The reason for adding cash.
     * @return TransferResult indicating the result of the cash addition.
     */
    public TransferResult addCash(Player player, long cash, BusinessAddCashEvent.Reason reason) {
        BusinessAddCashEvent evt = new BusinessAddCashEvent(player, cash, reason);
        plugin.triggerEvent(evt);
        if (!evt.isCancelled()) {
            this.Cash += evt.getAmount();
            return TransferResult.Successful;
        }
        return TransferResult.EventCancel;
    }

    /**
     * Removes cash from the business.
     *
     * @param cash The amount of cash to remove.
     * @param reason The reason for removing cash.
     * @return TransferResult indicating the result of the cash removal.
     */
    public TransferResult removeCash(long cash, BusinessRemoveCashEvent.Reason reason) {
        if (getCash() >= cash) {
            BusinessRemoveCashEvent evt = new BusinessRemoveCashEvent(cash, reason);
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                this.Cash -= evt.getAmount();
                return TransferResult.Successful;
            }
        } else {
            return TransferResult.NotEnoughMoney;
        }
        return TransferResult.EventCancel;
    }

    /**
     * Removes cash from the business by a player.
     *
     * @param player The player removing the cash.
     * @param cash The amount of cash to remove.
     * @param reason The reason for removing cash.
     * @return TransferResult indicating the result of the cash removal.
     */
    public TransferResult removeCash(Player player, long cash, BusinessRemoveCashEvent.Reason reason) {
        if (getCash() >= cash) {
            BusinessRemoveCashEvent evt = new BusinessRemoveCashEvent(player, cash, reason);
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                this.Cash -= evt.getAmount();
                return TransferResult.Successful;
            }

        } else {
            return TransferResult.NotEnoughMoney;
        }
        return TransferResult.EventCancel;
    }

    // Owners Management
    /**
     * Gets the list of business owners.
     *
     * @return A list of owner UIDs.
     */
    public List<String> getOwners() {
        return Owners;
    }

    /**
     * Checks if a player is an owner of the business.
     *
     * @param player The player to check.
     * @return True if the player is an owner, false otherwise.
     */
    public boolean isOwner(Player player) {
        return isOwner(player.getUID());
    }

    /**
     * Checks if a player is an owner of the business by UID.
     *
     * @param UID The UID of the player to check.
     * @return True if the player is an owner, false otherwise.
     */
    public boolean isOwner(String UID) {
        return getOwners().contains(UID);
    }

    /**
     * Adds a new owner to the business.
     *
     * @param player The player adding the owner.
     * @param newowner The new owner to be added.
     * @return True if the owner was added, false otherwise.
     */
    public boolean addOwner(Player player, Player newowner) {
        return addOwner(player, newowner.getUID());
    }

    /**
     * Adds a new owner to the business by UID.
     *
     * @param player The player adding the owner.
     * @param uid The UID of the new owner.
     * @return True if the owner was added, false otherwise.
     */
    public boolean addOwner(Player player, String uid) {
        if (!isOwner(uid)) {
            AddOwnerEvent evt = new AddOwnerEvent(player, this, uid);
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                return Owners.add(uid);
            }
        }
        return false;
    }

    /**
     * Removes an owner from the business.
     *
     * @param player The player removing the owner.
     * @param owner The owner to be removed.
     * @return True if the owner was removed, false otherwise.
     */
    public boolean removeOwner(Player player, Player owner) {
        return removeOwner(player, owner.getUID());
    }

    /**
     * Removes an owner from the business by UID.
     *
     * @param player The player removing the owner.
     * @param ownerUID The UID of the owner to be removed.
     * @return True if the owner was removed, false otherwise.
     */
    public boolean removeOwner(Player player, String ownerUID) {
        if (isOwner(ownerUID)) {
            RemoveOwnerEvent evt = new RemoveOwnerEvent(player, this, ownerUID);
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                return Owners.remove(ownerUID);
            }
        }
        return false;
    }

    // Members Management
    /**
     * Gets the list of business members.
     *
     * @return A list of BusinessMember objects.
     */
    public List<BusinessMember> getMembers() {
        return Members;
    }

    /**
     * Checks if a player is a member of the business.
     *
     * @param player The player to check.
     * @return True if the player is a member, false otherwise.
     */
    public boolean isMember(Player player) {
        return isMember(player.getUID());
    }

    /**
     * Checks if a player is a member of the business by UID.
     *
     * @param UID The UID of the player to check.
     * @return True if the player is a member, false otherwise.
     */
    public boolean isMember(String UID) {
        for (BusinessMember m : getMembers()) {
            if (m.getUID().matches(UID)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a member to the business.
     *
     * @param player The player adding the member.
     * @param m The new member to be added.
     * @return True if the member was added, false otherwise.
     */
    public boolean addMember(Player player, BusinessMember m) {
        AddBusinessMemberEvent evt = new AddBusinessMemberEvent(player, this, m);
        plugin.triggerEvent(evt);
        if (!evt.isCancelled()) {
            return Members.add(m);
        }
        return false;
    }

    /**
     * Adds a member to the business by a player.
     *
     * @param player The player adding the member.
     * @param member The player to be added as a member.
     * @return True if the member was added, false otherwise.
     */
    public boolean addMember(Player player, Player member) {
        return addMember(player, member.getUID());
    }

    /**
     * Adds a member to the business by UID.
     *
     * @param player The player adding the member.
     * @param uid The UID of the new member.
     * @return True if the member was added, false otherwise.
     */
    public boolean addMember(Player player, String uid) {
        if (!isMember(uid)) {
            BusinessMember m = new BusinessMember(uid);
            AddBusinessMemberEvent evt = new AddBusinessMemberEvent(player, this, m);
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                return Members.add(m);
            }
        }
        return false;
    }

    /**
     * Removes a member from the business by a player.
     *
     * @param player The player removing the member.
     * @param member The player to be removed.
     * @return True if the member was removed, false otherwise.
     */
    public boolean removeMember(Player player, Player member) {
        return removeMember(player, member.getUID());
    }

    /**
     * Removes a member from the business by UID.
     *
     * @param player The player removing the member.
     * @param uid The UID of the member to be removed.
     * @return True if the member was removed, false otherwise.
     */
    public boolean removeMember(Player player, String uid) {
        if (isMember(uid)) {
            BusinessMember m = getMember(uid);
            RemoveBusinessMemberEvent evt = new RemoveBusinessMemberEvent(player, this, m);
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                return Members.remove(m);
            }
        }
        return false;
    }

    /**
     * Gets a member of the business by a player.
     *
     * @param player The player to get the member for.
     * @return The BusinessMember object.
     */
    public BusinessMember getMember(Player player) {
        return getMember(player.getUID());
    }

    /**
     * Gets a member of the business by UID.
     *
     * @param uid The UID of the member to get.
     * @return The BusinessMember object.
     */
    public BusinessMember getMember(String uid) {
        for (BusinessMember m : Members) {
            if (m.getUID().matches(uid)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Checks if a player is a worker in the business.
     *
     * @param player The player to check.
     * @return True if the player is a worker, false otherwise.
     */
    public boolean isWorker(Player player) {
        return isWorker(player.getUID());
    }

    /**
     * Checks if a player is a worker in the business by UID.
     *
     * @param uid The UID of the worker to check.
     * @return True if the player is a worker, false otherwise.
     */
    public boolean isWorker(String uid) {
        return Members.stream().anyMatch(fw -> (fw.hasPermission(BusinessPermission.WORK)));
    }

    // Plot Management
    /**
     * Adds a plot to the business.
     *
     * @param area The plot (Area) to add.
     * @return True if the plot was added, false otherwise.
     */
    public boolean addPlot(Area area) {
        return Plots.add(area);
    }

    /**
     * Removes a plot from the business.
     *
     * @param area The plot (Area) to remove.
     * @return True if the plot was removed, false otherwise.
     */
    public boolean removePlot(Area area) {
        return Plots.remove(area);
    }

    /**
     * Gets a plot of the business by area ID.
     *
     * @param areaID The ID of the area (plot).
     * @return The Area object representing the plot.
     */
    public Area getPlot(int areaID) {
        for (Area a : Plots) {
            if (a.getID() == areaID) {
                return a;
            }
        }
        return null;
    }

    // Teleport Management
    /**
     * Gets the teleport point of the business. Warning: Can return null.
     *
     * @return The teleport point as a Vector3f (or null).
     */
    public Vector3f getTelepoint() {
        return Telepoint;
    }

    /**
     * Sets the teleport point of the business.
     *
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @param z The Z coordinate.
     */
    public void setTelepoint(float x, float y, float z) {
        setTelepoint(new Vector3f(x, y, z));
    }

    /**
     * Sets the teleport point of the business.
     *
     * @param Telepoint The teleport point as a Vector3f.
     */
    public void setTelepoint(Vector3f Telepoint) {
        this.Telepoint = Telepoint;
    }

    /**
     * Gets the teleport rotation of the business. Warning: Can return null.
     *
     * @return The teleport rotation as a Quaternion (or null).
     */
    public Quaternion getTelepointRotation() {
        return TelepointRotation;
    }

    /**
     * Sets the teleport rotation of the business.
     *
     * @param x The X rotation.
     * @param y The Y rotation.
     * @param z The Z rotation.
     * @param w The W rotation.
     */
    public void setTelepointRotation(float x, float y, float z, float w) {
        setTelepointRotation(new Quaternion(x, y, z, w));
    }

    /**
     * Sets the teleport rotation of the business.
     *
     * @param TelepointRotation The teleport rotation as a Quaternion.
     */
    public void setTelepointRotation(Quaternion TelepointRotation) {
        this.TelepointRotation = TelepointRotation;
    }

    // Information Management
    /**
     * Gets the list of business information.
     *
     * @return A list of BusinessInfoMessage.
     */
    public List<String> getInfos() {
        return Infos;
    }

    /**
     * Adds an information string to the business's info list.
     *
     * @param info The information to add as BusinessInfoMessage.
     */
    public void addInfo(String info) {
        Infos.add(info);
    }

    /**
     * Clears all information from the business's info list.
     */
    public void clearInfos() {
        Infos.clear();
    }

    // Chest Management
    /**
     * Gets the UID of the chest associated with the business, where workers can
     * deposit items.
     *
     * @return The chest UID.
     */
    public long getChestUID() {
        return ChestUID;
    }

    /**
     * Sets the chest UID for the business.
     *
     * @param ChestUID The new chest UID.
     */
    public void setChestUID(long ChestUID) {
        this.ChestUID = ChestUID;
    }

    /**
     * Gets the list of plots associated with the business.
     *
     * @return A List of Area objects representing the plots of the business.
     */
    public List<Area> getPlots() {
        return Plots;
    }

    public List<Long> getPlotsID() {
        List<Long> ids = new ArrayList<>();
        Plots.forEach(a -> {
            ids.add(a.getID());
        });
        return ids;
    }

    /**
     * Gets the name of the business.
     *
     * @return The name of the business as a String.
     */
    public String getName() {
        return Name;
    }

    /**
     * Sets the name of the business.
     *
     * @param Name The new name for the business as a String.
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * Sets the bank account for the business.
     *
     * @param BusinessBankAccount The BusinessAccount object to be associated
     * with the business.
     */
    public void setBusinessBankAccount(BusinessAccount BusinessBankAccount) {
        this.BusinessBankAccount = BusinessBankAccount;
    }

    /**
     * Gets the bank account associated with the business.
     *
     * @return The BusinessAccount object linked to the business, or null if no
     * account is set.
     */
    public BusinessAccount getBusinessBankAccount() {
        return BusinessBankAccount;
    }

    /**
     * Checks whether the business has associated plots.
     *
     * @return True if the business has at least one plot, false otherwise.
     */
    public boolean hasBusinessPlots() {
        return !getPlots().isEmpty();
    }

    public static class BusinessInfoMessage {

        //TODO Lang
        public final String PlayerEnterPlot;

        public BusinessInfoMessage(Player player) {
            PlayerEnterPlot = "Test";
        }

    }

    public class NoBusinessPlayerPermission {

        private final Set<BusinessPermission> permissions;

        public NoBusinessPlayerPermission() {
            this.permissions = new HashSet<>();
        }

        public boolean hasPermission(BusinessPermission per) {
            return permissions.contains(per);
        }

        public void addPermission(BusinessPermission per) {
            permissions.add(per);
        }

        public boolean removePermission(BusinessPermission per) {
            return permissions.remove(per);
        }
    }

}
