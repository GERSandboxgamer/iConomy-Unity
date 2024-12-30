package de.sbg.unity.iconomy.Factory;

import de.sbg.unity.iconomy.Banksystem.FactoryAccount;
import de.sbg.unity.iconomy.Events.Factory.AddFactoryMemberEvent;
import de.sbg.unity.iconomy.Events.Factory.AddFactoryWorkerEvent;
import de.sbg.unity.iconomy.Events.Factory.AddOwnerEvent;
import de.sbg.unity.iconomy.Events.Factory.FactoryAddCashEvent;
import de.sbg.unity.iconomy.Events.Factory.FactoryRemoveCashEvent;
import de.sbg.unity.iconomy.Events.Factory.RemoveFactoryMemberEvent;
import de.sbg.unity.iconomy.Events.Factory.RemoveOwnerEvent;
import de.sbg.unity.iconomy.Utils.TransferResult;
import de.sbg.unity.iconomy.iConomy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.risingworld.api.Server;
import net.risingworld.api.objects.Area;
import net.risingworld.api.objects.Player;
import net.risingworld.api.utils.Quaternion;
import net.risingworld.api.utils.Utils;
import net.risingworld.api.utils.Vector3f;

/**
 * The Factory class represents a factory within the iConomy system. It handles
 * the management of factory attributes such as owners, workers, members, cash
 * balance, teleport points, plots, and item prices. The class also integrates
 * with the event system to trigger events when changes are made to the factory,
 * including adding/removing cash, owners, members, workers, and plots.
 */
public class Factory {

    private final List<Area> Plots;
    private FactoryAccount FactoryBankAccount;
    private final iConomy plugin;
    private final List<String> Owners, Infos;
    private String Name;
    private long Cash, ChestUID;
    private final List<FactoryWorker> Workers;
    private final List<FactoryMember> Members;
    private final int ID;
    private Vector3f Telepoint;
    private Quaternion TelepointRotation;
    private final HashMap<Integer, Long> PriceList;
    private final List<FactoryVisitor> factoryVisitors;
    private final FactoryPlayerPermissions playerPermissions;
    private final String factoryBankID;

    /**
     * Constructor to create a new factory.
     *
     * @param plugin The iConomy plugin instance.
     * @param Name The name of the factory.
     * @param ID The unique ID of the factory.
     */
    public Factory(iConomy plugin, String Name, int ID) {
        this.plugin = plugin;
        this.Plots = new ArrayList<>();
        this.Owners = new ArrayList<>();
        this.Workers = new ArrayList<>();
        this.Members = new ArrayList<>();
        this.Infos = new ArrayList<>();
        this.PriceList = new HashMap<>();
        this.Cash = 0;
        this.Name = Name;
        this.ID = ID;
        this.factoryVisitors = new ArrayList<>();
        this.playerPermissions = new FactoryPlayerPermissions();
        int z = 0;
        String newBankID = "";
        
        while(z <= 10){
            newBankID = newBankID + String.valueOf(Utils.MathUtils.nextRandomInt(0, 9));
        }
        factoryBankID = newBankID;
    }

    /**
     * Constructor to create a new factory with a price list.
     *
     * @param plugin The iConomy plugin instance.
     * @param Name The name of the factory.
     * @param ID The unique ID of the factory.
     * @param priceList The price list for the factory.
     */
    public Factory(iConomy plugin, String Name, int ID, HashMap<Integer, Long> priceList) {
        this.plugin = plugin;
        this.Plots = new ArrayList<>();
        this.Owners = new ArrayList<>();
        this.Workers = new ArrayList<>();
        this.Members = new ArrayList<>();
        this.Infos = new ArrayList<>();
        this.PriceList = priceList;
        this.Cash = 0;
        this.Name = Name;
        this.ID = ID;
        this.factoryVisitors = new ArrayList<>();
        this.playerPermissions = new FactoryPlayerPermissions();
        int z = 1;
        String newBankID = "";
        
        while(z <= 10){
            newBankID = newBankID + String.valueOf(Utils.MathUtils.nextRandomInt(0, 9));
            z = z + 1;
        }
        factoryBankID = newBankID;
    }

    public String getFactoryBankID() {
        return factoryBankID;
    }
    
    public void addAllWorkers(List<FactoryWorker> workers){
        Workers.addAll(workers);
    }
    
    public void addAllMembers(List<FactoryMember> members) {
        Members.addAll(members);
    }
    
    public void addAllOwners(List<String> owners){
        Owners.addAll(owners);
    }
    
    public void addAllInfos(List<String> infos){
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

    // Factory Identification
    /**
     * Gets the factory ID.
     *
     * @return The ID of the factory.
     */
    public int getID() {
        return ID;
    }

    // Cash Management
    /**
     * Gets the factory's cash balance.
     *
     * @return The cash balance of the factory.
     */
    public long getCash() {
        return Cash;
    }

    /**
     * Sets the factory's cash balance.
     *
     * @param Cash The new cash balance.
     */
    public void setCash(long Cash) {
        this.Cash = Cash;
    }

    /**
     * Adds cash to the factory.
     *
     * @param cash The amount of cash to add.
     * @param reason The reason for adding cash.
     * @return TransferResult indicating the result of the cash addition.
     */
    public TransferResult addCash(long cash, FactoryAddCashEvent.Reason reason) {
        FactoryAddCashEvent evt = new FactoryAddCashEvent(cash, reason);
        plugin.triggerEvent(evt);
        if (!evt.isCancelled()) {
            this.Cash += evt.getAmount();
            return TransferResult.Successful;
        }
        return TransferResult.EventCancel;
    }

    /**
     * Adds cash to the factory by a player.
     *
     * @param player The player adding the cash.
     * @param cash The amount of cash to add.
     * @param reason The reason for adding cash.
     * @return TransferResult indicating the result of the cash addition.
     */
    public TransferResult addCash(Player player, long cash, FactoryAddCashEvent.Reason reason) {
        FactoryAddCashEvent evt = new FactoryAddCashEvent(player, cash, reason);
        plugin.triggerEvent(evt);
        if (!evt.isCancelled()) {
            this.Cash += evt.getAmount();
            return TransferResult.Successful;
        }
        return TransferResult.EventCancel;
    }

    /**
     * Removes cash from the factory.
     *
     * @param cash The amount of cash to remove.
     * @param reason The reason for removing cash.
     * @return TransferResult indicating the result of the cash removal.
     */
    public TransferResult removeCash(long cash, FactoryRemoveCashEvent.Reason reason) {
        if (getCash() >= cash) {
            FactoryRemoveCashEvent evt = new FactoryRemoveCashEvent(cash, reason);
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
     * Removes cash from the factory by a player.
     *
     * @param player The player removing the cash.
     * @param cash The amount of cash to remove.
     * @param reason The reason for removing cash.
     * @return TransferResult indicating the result of the cash removal.
     */
    public TransferResult removeCash(Player player, long cash, FactoryRemoveCashEvent.Reason reason) {
        if (getCash() >= cash) {
            FactoryRemoveCashEvent evt = new FactoryRemoveCashEvent(player, cash, reason);
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
     * Gets the list of factory owners.
     *
     * @return A list of owner UIDs.
     */
    public List<String> getOwners() {
        return Owners;
    }

    /**
     * Checks if a player is an owner of the factory.
     *
     * @param player The player to check.
     * @return True if the player is an owner, false otherwise.
     */
    public boolean isOwner(Player player) {
        return isOwner(player.getUID());
    }

    /**
     * Checks if a player is an owner of the factory by UID.
     *
     * @param UID The UID of the player to check.
     * @return True if the player is an owner, false otherwise.
     */
    public boolean isOwner(String UID) {
        return getOwners().contains(UID);
    }

    /**
     * Adds a new owner to the factory.
     *
     * @param player The player adding the owner.
     * @param newowner The new owner to be added.
     * @return True if the owner was added, false otherwise.
     */
    public boolean addOwner(Player player, Player newowner) {
        return addOwner(player, newowner.getUID());
    }

    /**
     * Adds a new owner to the factory by UID.
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
     * Removes an owner from the factory.
     *
     * @param player The player removing the owner.
     * @param owner The owner to be removed.
     * @return True if the owner was removed, false otherwise.
     */
    public boolean removeOwner(Player player, Player owner) {
        return removeOwner(player, owner.getUID());
    }

    /**
     * Removes an owner from the factory by UID.
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
     * Gets the list of factory members.
     *
     * @return A list of FactoryMember objects.
     */
    public List<FactoryMember> getMembers() {
        return Members;
    }

    /**
     * Checks if a player is a member of the factory.
     *
     * @param player The player to check.
     * @return True if the player is a member, false otherwise.
     */
    public boolean isMember(Player player) {
        return isMember(player.getUID());
    }

    /**
     * Checks if a player is a member of the factory by UID.
     *
     * @param UID The UID of the player to check.
     * @return True if the player is a member, false otherwise.
     */
    public boolean isMember(String UID) {
        for (FactoryMember m : getMembers()) {
            if (m.getUID().matches(UID)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a member to the factory.
     *
     * @param player The player adding the member.
     * @param m The new member to be added.
     * @return True if the member was added, false otherwise.
     */
    public boolean addMember(Player player, FactoryMember m) {
        AddFactoryMemberEvent evt = new AddFactoryMemberEvent(player, this, m);
        plugin.triggerEvent(evt);
        if (!evt.isCancelled()) {
            return Members.add(m);
        }
        return false;
    }

    /**
     * Adds a member to the factory by a player.
     *
     * @param player The player adding the member.
     * @param member The player to be added as a member.
     * @return True if the member was added, false otherwise.
     */
    public boolean addMember(Player player, Player member) {
        return addMember(player, member.getUID());
    }

    /**
     * Adds a member to the factory by UID.
     *
     * @param player The player adding the member.
     * @param uid The UID of the new member.
     * @return True if the member was added, false otherwise.
     */
    public boolean addMember(Player player, String uid) {
        if (!isMember(uid)) {
            FactoryMember m = new FactoryMember(uid);
            AddFactoryMemberEvent evt = new AddFactoryMemberEvent(player, this, m);
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                return Members.add(m);
            }
        }
        return false;
    }

    /**
     * Removes a member from the factory by a player.
     *
     * @param player The player removing the member.
     * @param member The player to be removed.
     * @return True if the member was removed, false otherwise.
     */
    public boolean removeMember(Player player, Player member) {
        return removeMember(player, member.getUID());
    }

    /**
     * Removes a member from the factory by UID.
     *
     * @param player The player removing the member.
     * @param uid The UID of the member to be removed.
     * @return True if the member was removed, false otherwise.
     */
    public boolean removeMember(Player player, String uid) {
        if (isMember(uid)) {
            FactoryMember m = getMember(uid);
            RemoveFactoryMemberEvent evt = new RemoveFactoryMemberEvent(player, this, m);
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                return Members.remove(m);
            }
        }
        return false;
    }

    /**
     * Gets a member of the factory by a player.
     *
     * @param player The player to get the member for.
     * @return The FactoryMember object.
     */
    public FactoryMember getMember(Player player) {
        return getMember(player.getUID());
    }

    /**
     * Gets a member of the factory by UID.
     *
     * @param uid The UID of the member to get.
     * @return The FactoryMember object.
     */
    public FactoryMember getMember(String uid) {
        for (FactoryMember m : Members) {
            if (m.getUID().matches(uid)) {
                return m;
            }
        }
        return null;
    }

    // Workers Management
    /**
     * Gets the list of factory workers.
     *
     * @return A list of FactoryWorker objects.
     */
    public List<FactoryWorker> getWorkers() {
        return Workers;
    }

    /**
     * Adds a worker to the factory.
     *
     * @param w The worker to add.
     * @return True if the worker was added, false otherwise.
     */
    public boolean addWorker(FactoryWorker w) {
        return Workers.add(w);
    }

    /**
     * Adds a worker to the factory by a player.
     *
     * @param player The player adding the worker.
     * @param member The player to be added as a worker.
     * @return True if the worker was added, false otherwise.
     */
    public boolean addWorker(Player player, Player member) {
        return addWorker(player, member.getUID());
    }

    /**
     * Adds a worker to the factory by UID.
     *
     * @param player The player adding the worker.
     * @param uid The UID of the new worker.
     * @return True if the worker was added, false otherwise.
     */
    public boolean addWorker(Player player, String uid) {
        if (!isWorker(uid)) {
            FactoryWorker fw = new FactoryWorker(uid);
            AddFactoryWorkerEvent evt = new AddFactoryWorkerEvent(player, this, fw);
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                return addWorker(fw);
            }
        }
        return false;
    }

    /**
     * Checks if a player is a worker in the factory.
     *
     * @param player The player to check.
     * @return True if the player is a worker, false otherwise.
     */
    public boolean isWorker(Player player) {
        return isWorker(player.getUID());
    }

    /**
     * Checks if a player is a worker in the factory by UID.
     *
     * @param uid The UID of the worker to check.
     * @return True if the player is a worker, false otherwise.
     */
    public boolean isWorker(String uid) {
        return Workers.stream().anyMatch(fw -> (fw.getUID().matches(uid)));
    }

    /**
     * Removes a worker from the factory by a player.
     *
     * @param player The player removing the worker.
     * @return True if the worker was removed, false otherwise.
     */
    public boolean removeWorker(Player player) {
        return removeWorker(player.getUID());
    }

    /**
     * Removes a worker from the factory by UID.
     *
     * @param uid The UID of the worker to remove.
     * @return True if the worker was removed, false otherwise.
     */
    public boolean removeWorker(String uid) {
        if (isWorker(uid)) {
            return Workers.remove(getWorker(uid));
        }
        return false;
    }

    /**
     * Gets a worker of the factory by a player.
     *
     * @param player The player to get the worker for.
     * @return The FactoryWorker object.
     */
    public FactoryWorker getWorker(Player player) {
        return getWorker(player.getUID());
    }

    /**
     * Gets a worker of the factory by UID.
     *
     * @param uid The UID of the worker to get.
     * @return The FactoryWorker object.
     */
    public FactoryWorker getWorker(String uid) {
        if (isWorker(uid)) {
            for (FactoryWorker fw : Workers) {
                if (fw.getUID().matches(uid)) {
                    return fw;
                }
            }
        }
        return null;
    }

    // Plot Management
    /**
     * Adds a plot to the factory.
     *
     * @param area The plot (Area) to add.
     * @return True if the plot was added, false otherwise.
     */
    public boolean addPlot(Area area) {
        return Plots.add(area);
    }

    /**
     * Removes a plot from the factory.
     *
     * @param area The plot (Area) to remove.
     * @return True if the plot was removed, false otherwise.
     */
    public boolean removePlot(Area area) {
        return Plots.remove(area);
    }

    /**
     * Gets a plot of the factory by area ID.
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
     * Gets the teleport point of the factory. Warning: Can return null.
     *
     * @return The teleport point as a Vector3f (or null).
     */
    public Vector3f getTelepoint() {
        return Telepoint;
    }

    /**
     * Sets the teleport point of the factory.
     *
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @param z The Z coordinate.
     */
    public void setTelepoint(float x, float y, float z) {
        setTelepoint(new Vector3f(x, y, z));
    }

    /**
     * Sets the teleport point of the factory.
     *
     * @param Telepoint The teleport point as a Vector3f.
     */
    public void setTelepoint(Vector3f Telepoint) {
        this.Telepoint = Telepoint;
    }

    /**
     * Gets the teleport rotation of the factory. Warning: Can return null.
     *
     * @return The teleport rotation as a Quaternion (or null).
     */
    public Quaternion getTelepointRotation() {
        return TelepointRotation;
    }

    /**
     * Sets the teleport rotation of the factory.
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
     * Sets the teleport rotation of the factory.
     *
     * @param TelepointRotation The teleport rotation as a Quaternion.
     */
    public void setTelepointRotation(Quaternion TelepointRotation) {
        this.TelepointRotation = TelepointRotation;
    }

    // Information Management
    /**
     * Gets the list of factory information.
     *
     * @return A list of FactoryInfoMessage.
     */
    public List<String> getInfos() {
        return Infos;
    }

    /**
     * Adds an information string to the factory's info list.
     *
     * @param info The information to add as FactoryInfoMessage.
     */
    public void addInfo(String info) {
        Infos.add(info);
    }

    /**
     * Clears all information from the factory's info list.
     */
    public void clearInfos() {
        Infos.clear();
    }

    // Price Management
    /**
     * Gets the price list of the factory.
     *
     * @return A HashMap containing item IDs and their prices.
     */
    public HashMap<Integer, Long> getPriceList() {
        return PriceList;
    }

    /**
     * Sets the price for a specific item.
     *
     * @param itemID The ID of the item.
     * @param price The price of the item.
     */
    public void setPrice(int itemID, long price) {
        PriceList.put(itemID, price);
    }

    /**
     * Gets the price of a specific item by its ID.
     *
     * @param itemID The ID of the item.
     * @return The price of the item.
     */
    public long getPrice(int itemID) {
        return PriceList.get(itemID);
    }

    // Chest Management
    /**
     * Gets the UID of the chest associated with the factory, where workers can
     * deposit items.
     *
     * @return The chest UID.
     */
    public long getChestUID() {
        return ChestUID;
    }

    /**
     * Sets the chest UID for the factory.
     *
     * @param ChestUID The new chest UID.
     */
    public void setChestUID(long ChestUID) {
        this.ChestUID = ChestUID;
    }

    /**
     * Gets the list of plots associated with the factory.
     *
     * @return A List of Area objects representing the plots of the factory.
     */
    public List<Area> getPlots() {
        return Plots;
    }
    
    public List<Long> getPlotsID(){
        List<Long> ids = new ArrayList<>();
        Plots.forEach(a -> {
            ids.add(a.getID());
        });
        return ids;
    }

    /**
     * Gets the name of the factory.
     *
     * @return The name of the factory as a String.
     */
    public String getName() {
        return Name;
    }

    /**
     * Sets the name of the factory.
     *
     * @param Name The new name for the factory as a String.
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * Sets the bank account for the factory.
     *
     * @param FactoryBankAccount The FactoryAccount object to be associated with
     * the factory.
     */
    public void setFactoryBankAccount(FactoryAccount FactoryBankAccount) {
        this.FactoryBankAccount = FactoryBankAccount;
    }

    /**
     * Gets the bank account associated with the factory.
     *
     * @return The FactoryAccount object linked to the factory, or null if no
     * account is set.
     */
    public FactoryAccount getFactoryBankAccount() {
        return FactoryBankAccount;
    }

    /**
     * Checks whether the factory has associated plots.
     *
     * @return True if the factory has at least one plot, false otherwise.
     */
    public boolean hasFactoryPlots() {
        return !getPlots().isEmpty();
    }

    public List<FactoryVisitor> getFactoryVisitors() {
        return factoryVisitors;
    }

    public FactoryPlayerPermissions getPlayerPermissions() {
        return playerPermissions;
    }
    
    public static class FactoryInfoMessage {
        //TODO Lang
        public final String PlayerEnterPlot;

        public FactoryInfoMessage(Player player) {
            PlayerEnterPlot = "Test";
        }
        
        
        
    }

}
