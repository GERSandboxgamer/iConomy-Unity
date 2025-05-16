package de.sbg.unity.iconomy.Business;

import de.sbg.unity.iconomy.Events.Business.AddBusinessEvent;
import de.sbg.unity.iconomy.Events.Business.RemoveBusinessEvent;
import de.sbg.unity.iconomy.Exeptions.BusinessAlreadyExistsExeption;
import de.sbg.unity.iconomy.Utils.Attribute;
import de.sbg.unity.iconomy.iConomy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import net.risingworld.api.objects.Area;
import net.risingworld.api.objects.Player;
import java.sql.SQLException;

/**
 * The BusinessSystem class handles the management of business within the
 * iConomy plugin. It supports the creation, deletion, and retrieval of
 * business, as well as event-based actions when business are added or removed.
 * This class works closely with other system components such as the Attribute
 * utility and the iConomy plugin to manage business data and trigger events.
 */
public class BusinessSystem {

    private final HashMap<Integer, Business> BusinessList;
    private final Attribute att;
    private final iConomy plugin;
    public final BusinessPlots businessPlots;
    protected final List<String> businessBankID;

    /**
     * Constructor for BusinessSystem.
     *
     * @param plugin The main iConomy plugin instance.
     * @param att The Attribute utility used for business-related operations.
     */
    public BusinessSystem(iConomy plugin, Attribute att) {
        this.plugin = plugin;
        this.BusinessList = new HashMap<>();
        this.att = att;
        this.businessPlots = new BusinessPlots(plugin);
        this.businessBankID = new ArrayList<>();
    }

    public Business getBusinessByBankID(String bankID) {
        for (Business f : getBusinesss()) {
            if (f.getBusinessBankID().equals(bankID)) {
                return f;
            }
        }
        return null;
    }

    /**
     * Gets the internal HashMap of business.
     *
     * @return A HashMap containing all business mapped by their ID.
     */
    public HashMap<Integer, Business> getHashBusinessList() {
        return BusinessList;
    }

    /**
     * Retrieves all business.
     *
     * @return A Collection containing all business in the system.
     */
    public Collection<Business> getBusinesss() {
        return BusinessList.values();
    }

    /**
     * Gets a list of all business names.
     *
     * @return A List containing the names of all business.
     */
    public List<String> getAllBusinessNames() {
        List<String> result = new ArrayList<>();
        for (Business f : BusinessList.values()) {
            result.add(f.getName());
        }
        return result;
    }

    /**
     * Adds a new business to the system.
     *
     * @param player The player who is creating the business.
     * @param BusinessName The name of the new business.
     * @return The newly created Business object.
     * @throws BusinessAlreadyExistsExeption If a business with the same name
     * already exists.
     * @throws SQLException If an SQL error occurs.
     */
    public Business addNewBusiness(Player player, String BusinessName) throws BusinessAlreadyExistsExeption, SQLException {
        if (!getAllBusinessNames().contains(BusinessName)) {

            AddBusinessEvent evt = new AddBusinessEvent(player);
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                int id = plugin.Databases.Business.TabBusiness.add(BusinessName);
                Business f = new Business(plugin, BusinessName, id);
                BusinessList.put(id, f);
                return f;
            }

        } else {
            throw new BusinessAlreadyExistsExeption("Business already exists with the name: " + BusinessName);
        }
        return null;
    }

    /**
     * Retrieves a list of business by name.
     *
     * @param name The name of the business (supports regex).
     * @return A List of business matching the provided name.
     */
    public List<Business> getBusinessByName(String name) {
        List<Business> list = new ArrayList<>();
        for (Business f : BusinessList.values()) {
            if (f.getName().matches(name)) {
                list.add(f);
            }
        }
        return list;
    }

    /**
     * Gets the business associated with the player's current area/plot.
     *
     * @param player The player whose plot is checked for a business.
     * @return The Business associated with the player's plot, or null if none
     * is found.
     */
    public Business getBusinessByPlot(Player player) {
        Area a = player.getCurrentArea();
        if (isBusinessPlot(a)) {
            return att.Area.getBusiness(a);
        }

        return null;
    }

    public Business getBusinessByPlot(Area area) {
        return getBusinessByPlot(area.getID());
    }

    public Business getBusinessByPlot(long id) {
        if (isBusinessPlot(id)) {
            for (Business f : getBusinesss()) {
                for (Area a : f.getPlots()) {
                    if (a.getID() == id) {
                        return f;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Checks if a given area is a business plot.
     *
     * @param area The area to check.
     * @return True if the area is a business plot, false otherwise.
     */
    public boolean isBusinessPlot(Area area) {
        return getAllBusinessPlots().stream().anyMatch(a -> (a.getID() == area.getID()));
    }

    public boolean isEmptyBusinessPlot(Area area) {
        return getBusinessByPlot(area) == null;
    }

    /**
     * Checks if an area with a specific ID is a business plot.
     *
     * @param id The ID of the area to check.
     * @return True if the area is a business plot, false otherwise.
     */
    public boolean isBusinessPlot(long id) {
        return getAllBusinessPlots().stream().anyMatch(a -> (a.getID() == id));
    }

    /**
     * Retrieves a business by its ID.
     *
     * @param id The ID of the business.
     * @return The Business associated with the given ID, or null if none is
     * found.
     */
    public Business getBusinessByID(int id) {
        return BusinessList.get(id);
    }

    /**
     * Removes a business by ID.
     *
     * @param player The player requesting the removal.
     * @param id The ID of the business to be removed.
     * @return True if the business was successfully removed, false otherwise.
     * @throws SQLException If an SQL error occurs.
     */
    public boolean removeBusiness(Player player, int id) throws SQLException {
        return removeBusiness(player, getBusinessByID(id));
    }

    /**
     * Removes a business by its instance.
     *
     * @param player The player requesting the removal.
     * @param f The Business to be removed.
     * @return True if the business was successfully removed, false otherwise.
     * @throws SQLException If an SQL error occurs.
     */
    public boolean removeBusiness(Player player, Business f) throws SQLException {
        RemoveBusinessEvent evt = new RemoveBusinessEvent(player, f);
        plugin.triggerEvent(evt);
        if (!evt.isCancelled()) {
            businessBankID.remove(f.getBusinessBankID());
            plugin.Databases.Business.TabBusiness.remove(f);
            return BusinessList.remove(f.getID()) != null;
        }
        return false;
    }

    /**
     * Retrieves a list of business owned or managed by the player.
     *
     * @param player The player whose business are being retrieved.
     * @return A List of business owned or managed by the player.
     */
    public List<Business> getBusinessListByPlayer(Player player) {
        List<Business> result = new ArrayList<>();
        for (Business f : BusinessList.values()) {
            if (f.isOwner(player)) {
                result.add(f);
            }
            if (f.isMember(player)) {
                result.add(f);
            }
        }
        return result;
    }

    /**
     * Gets a list of all business plots.
     *
     * @return A List of areas that are business plots.
     */
    public List<Area> getAllBusinessPlots() {
        List<Area> l = new ArrayList<>();
        BusinessList.values().stream().filter(f -> (f.hasBusinessPlots())).forEachOrdered(f -> {
            f.getPlots().forEach(fa -> {
                l.add(fa);
            });
        });
        return l;
    }
    
    

}
