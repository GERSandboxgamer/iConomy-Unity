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
import net.risingworld.api.objects.Area;
import net.risingworld.api.objects.Player;
import net.risingworld.api.utils.Quaternion;
import net.risingworld.api.utils.Vector3f;

/**
 * The Factory Object
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
    }

    /**
     * Create new Factory with a price list
     * <b>Only for intern!</b>
     *
     * @param plugin
     * @param Name
     * @param ID
     * @param priceList
     * @hidden Only for intern!
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
    }

    public int getID() {
        return ID;
    }

    public List<FactoryMember> getMembers() {
        return Members;
    }

    public List<Area> getPlots() {
        return Plots;
    }

    public boolean hasFactoryPlots() {
        return !Plots.isEmpty();
    }

    public long getCash() {
        return Cash;
    }

    /**
     * Set the Cash of the factory
     * @param Cash As Long
     */
    public void setCash(long Cash) {
        this.Cash = Cash;
    }

    /**
     * Add cash to the factory.
     * @param cash As Long
     * @param reason As FactoryAddCashEvent.Reason
     * @return TransferResult: Successful or EventCancel
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
     * Add cash to the factory
     * @param player As Player
     * @param cash As long
     * @param reason As FactoryAddCashEvent.Reason
     * @return TransferResult: Successful or EventCancel
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
     * Remove cash from the factory
     * @param cash As Long
     * @param reason FactoryRemoveCashEvent.Reason
     * @return TransferResult: Successful, EventCancel or NotAnouthMoney
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
     * Remove cash from the factory
     * @param player As Player
     * @param cash As Long
     * @param reason FactoryRemoveCashEvent.Reason
     * @return TransferResult: Successful, EventCancel or NotAnouthMoney
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

    /**
     * Set a new name for the factory
     * @param Name As String
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    public String getName() {
        return Name;
    }

    public List<String> getOwners() {
        return Owners;
    }

    public List<FactoryWorker> getWorkers() {
        return Workers;
    }

    /**
     * Is the Player a owner?
     * @param player As Player
     * @return true or false
     */
    public boolean isOwner(Player player) {
        return isOwner(player.getUID());
    }

    /**
     * Is the Player a owner?
     * @param UID As String
     * @return true or false
     */
    public boolean isOwner(String UID) {
        return getOwners().contains(UID);
    }

    /**
     * Add a owner to the factory
     * @param player As Player
     * @param newowner As Player
     * @return true or false
     */
    public boolean addOwner(Player player, Player newowner) {
        return addOwner(player, newowner.getUID());
    }

    /**
     * Add a owner to the factory
     * @param player As Player
     * @param uid As String
     * @return true or false
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
     * Remove Owner from the factory
     * @param player As Player
     * @param owner As Player
     * @return true or false
     */
    public boolean removeOwner(Player player, Player owner) {
        return removeOwner(player, owner.getUID());
    }

    /**
     * Remove Owner from the factory
     * @param player As Player
     * @param ownerUID As String
     * @return true or false
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

    /**
     * Is player a member of the factory?
     * @param player As Player
     * @return true or false
     */
    public boolean isMember(Player player) {
        return isMember(player.getUID());
    }

    /**
     * Is player a member of the factory?
     * @param UID As String
     * @return true or false
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
     * Add a member to the factory
     * @param player As Player
     * @param m As FactoryMember
     * @return
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
     * Add a member to the factory
     * @param player As Player
     * @param member As Player
     * @return
     */
    public boolean addMember(Player player, Player member) {
        return addMember(player, member.getUID());
    }

    /**
     * Add a member to the factory
     * @param player As Player
     * @param uid As String
     * @return
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
     * Remove a member from the factory
     * @param player As Player
     * @param member As Player
     * @return
     */
    public boolean removeMember(Player player, Player member) {
        return removeMember(player, member.getUID());
    }

    /**
     * Remove a member from the factory
     * @param player As Player
     * @param uid As String
     * @return
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
     * Get the member
     * @param player As Player
     * @return The member as FactoryMember
     */
    public FactoryMember getMember(Player player) {
        return getMember(player.getUID());
    }

    /**
     * Get the member
     * @param uid As String
     * @return The member as FactoryMember
     */
    public FactoryMember getMember(String uid) {
        for (FactoryMember m : Members) {
            if (m.getUID().matches(uid)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Set the factory bank account
     * @param FactoryBankAccount As FactoryAccount
     */
    public void setFactoryBankAccount(FactoryAccount FactoryBankAccount) {
        this.FactoryBankAccount = FactoryBankAccount;
    }

    /**
     * Get the facotry bank account
     * @return The factory bank account as FactoryAccount
     */
    public FactoryAccount getFactoryBankAccount() {
        return FactoryBankAccount;
    }

    public boolean hasFactoryBankAccount() {
        return FactoryBankAccount != null;
    }

    /**
     * Get the telepoint
     * Warning: Can return null
     * @return The telepoint as Vector3f (or null)
     */
    public Vector3f getTelepoint() {
        return Telepoint;
    }

    /**
     * Get the telepoint rotation
     * Warning: Can return null
     * @return The telepoint rotation as Quaternion (or null)
     */
    public Quaternion getTelepointRotation() {
        return TelepointRotation;
    }

    /**
     * Set the telepoint of the factory
     * @param x As flaot
     * @param y As flaot
     * @param z As flaot
     */
    public void setTelepoint(float x, float y, float z) {
        setTelepoint(new Vector3f(x, y, z));
    }

    /**
     * Set the telepoint of the factory
     * @param Telepoint As Vector3f
     */
    public void setTelepoint(Vector3f Telepoint) {
        this.Telepoint = Telepoint;
    }

    /**
     * Set the rotation of the telepoint from the factory
     * @param x As float
     * @param y As float
     * @param z As float
     * @param w As float
     */
    public void setTelepointRotation(float x, float y, float z, float w) {
        setTelepointRotation(new Quaternion(x, y, z, w));
    }

    /**
     * Set the rotation of the telepoint from the factory
     * @param TelepointRotation As Quaternion
     */
    public void setTelepointRotation(Quaternion TelepointRotation) {
        this.TelepointRotation = TelepointRotation;
    }

    public List<String> getInfos() {
        return Infos;
    }

    /**
     * Add a info to the info-list
     * @param info As String
     */
    public void addInfo(String info) {
        Infos.add(info);
    }

    public void clearInfos() {
        Infos.clear();
    }

    /**
     *
     * @param w
     * @return
     */
    public boolean addWorker(FactoryWorker w) {
        return Workers.add(w);
    }

    /**
     *
     * @param player
     * @param member
     * @return
     */
    public boolean addWorker(Player player, Player member) {
        return addWorker(player, member.getUID());
    }

    /**
     *
     * @param player
     * @param uid
     * @return
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
     *
     * @param player
     * @return
     */
    public boolean isWorker(Player player) {
        return isWorker(player.getUID());
    }

    /**
     *
     * @param uid
     * @return
     */
    public boolean isWorker(String uid) {
        return Workers.stream().anyMatch(fw -> (fw.getUID().matches(uid)));
    }

    /**
     *
     * @param player
     * @return
     */
    public boolean removeWorker(Player player) {
        return removeWorker(player.getUID());
    }

    /**
     *
     * @param uid
     * @return
     */
    public boolean removeWorker(String uid) {
        if (isWorker(uid)) {
            return Workers.remove(getWorker(uid));
        }
        return false;
    }

    /**
     *
     * @param player
     * @return
     */
    public FactoryWorker getWorker(Player player) {
        return getWorker(player.getUID());
    }

    /**
     *
     * @param uid
     * @return
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

    public boolean addPlot(Area area) {
        return Plots.add(area);
    }

    public boolean removePlot(Area area) {
        return Plots.remove(area);
    }

    public Area getPlot(int areaID) {
        for (Area a : Plots) {
            if (a.getID() == areaID) {
                return a;
            }
        }
        return null;
    }

    /**
     * Get the uid of the chest, were workers can to the founding items
     * @return The UID of the chest as long
     */
    public long getChestUID() {
        return ChestUID;
    }

    /**
     * Set the chest UID
     * @param ChestUID As long
     */
    public void setChestUID(long ChestUID) {
        this.ChestUID = ChestUID;
    }

    public HashMap<Integer, Long> getPriceList() {
        return PriceList;
    }

    /**
     * Set a price for an item
     * @param itemID As int
     * @param price As long
     */
    public void setPrice(int itemID, long price) {
        PriceList.put(itemID, price);
    }

    /**
     *
     * @param itemID As int
     * @return The price as long
     */
    public long getPrice(int itemID) {
        return (long) PriceList.get(itemID);
    }
    
}
