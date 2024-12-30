package de.sbg.unity.iconomy.Factory;

import de.sbg.unity.iconomy.Events.Factory.AddFactoryEvent;
import de.sbg.unity.iconomy.Events.Factory.RemoveFactoryEvent;
import de.sbg.unity.iconomy.Exeptions.FactoryAlreadyExistsExeption;
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
 * The FactorySystem class handles the management of factories within the iConomy plugin.
 * It supports the creation, deletion, and retrieval of factories, as well as event-based actions 
 * when factories are added or removed. This class works closely with other system components such as 
 * the Attribute utility and the iConomy plugin to manage factory data and trigger events.
 */
public class FactorySystem {

    private final HashMap<Integer, Factory> Factories;
    private final Attribute att;
    private final iConomy plugin;
    public final FactoryPlots factoryPlots;

    /**
     * Constructor for FactorySystem.
     * 
     * @param plugin The main iConomy plugin instance.
     * @param att    The Attribute utility used for factory-related operations.
     */
    public FactorySystem(iConomy plugin, Attribute att) {
        this.plugin = plugin;
        this.Factories = new HashMap<>();
        this.att = att;
        this.factoryPlots = new FactoryPlots(plugin);
    }
    
    public Factory getFactoryByBankID(String bankID){
       for (Factory f : getFactorys()) {
           if (f.getFactoryBankID().equals(bankID)) {
               return f;
           }
       }
       return null;
    }

    /**
     * Gets the internal HashMap of factories.
     * 
     * @return A HashMap containing all factories mapped by their ID.
     */
    public HashMap<Integer, Factory> getHashFactories() {
        return Factories;
    }

    /**
     * Retrieves all factories.
     * 
     * @return A Collection containing all factories in the system.
     */
    public Collection<Factory> getFactorys() {
        return Factories.values();
    }

    /**
     * Gets a list of all factory names.
     * 
     * @return A List containing the names of all factories.
     */
    public List<String> getAllFactoryNames() {
        List<String> result = new ArrayList<>();
        for (Factory f : Factories.values()) {
            result.add(f.getName());
        }
        return result;
    }

    /**
     * Adds a new factory to the system.
     * 
     * @param player      The player who is creating the factory.
     * @param FactoryName The name of the new factory.
     * @return The newly created Factory object.
     * @throws FactoryAlreadyExistsExeption If a factory with the same name already exists.
     * @throws SQLException If an SQL error occurs.
     */
    public Factory addNewFactory(Player player, String FactoryName) throws FactoryAlreadyExistsExeption, SQLException {
        if (!getAllFactoryNames().contains(FactoryName)) {

            AddFactoryEvent evt = new AddFactoryEvent(player);
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                int id = plugin.Databases.Factory.TabFactory.add(FactoryName);
                Factory f = new Factory(plugin, FactoryName, id);
                Factories.put(id, f);
                return f;
            }

        } else {
            throw new FactoryAlreadyExistsExeption("Factory already exists with the name: " + FactoryName);
        }
        return null;
    }

    /**
     * Retrieves a list of factories by name.
     * 
     * @param name The name of the factory (supports regex).
     * @return A List of factories matching the provided name.
     */
    public List<Factory> getFactoryByName(String name) {
        List<Factory> list = new ArrayList<>();
        for (Factory f : Factories.values()) {
            if (f.getName().matches(name)) {
                list.add(f);
            }
        }
        return list;
    }

    /**
     * Gets the factory associated with the player's current area/plot.
     * 
     * @param player The player whose plot is checked for a factory.
     * @return The Factory associated with the player's plot, or null if none is found.
     */
    public Factory getFactoryByPlot(Player player) {
        Area a = player.getCurrentArea();
        if (isFactoryPlot(a)) {
            return att.Area.getFactory(a);
        }

        return null;
    }
    
    public Factory getFactoryByPlot(Area area) {
        return getFactoryByPlot(area.getID());
    }
    
    public Factory getFactoryByPlot(long id) {
        if (isFactoryPlot(id)) {
            for (Factory f : getFactorys()) {
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
     * Checks if a given area is a factory plot.
     * 
     * @param area The area to check.
     * @return True if the area is a factory plot, false otherwise.
     */
    public boolean isFactoryPlot(Area area) {
        return getAllFactoryPlots().stream().anyMatch(a -> (a.getID() == area.getID()));
    }
    
    public boolean isEmptyFactoryPlot(Area area) {
        return getFactoryByPlot(area) == null;
    }

    /**
     * Checks if an area with a specific ID is a factory plot.
     * 
     * @param id The ID of the area to check.
     * @return True if the area is a factory plot, false otherwise.
     */
    public boolean isFactoryPlot(long id) {
        return getAllFactoryPlots().stream().anyMatch(a -> (a.getID() == id));
    }

    /**
     * Retrieves a factory by its ID.
     * 
     * @param id The ID of the factory.
     * @return The Factory associated with the given ID, or null if none is found.
     */
    public Factory getFactoryByID(int id) {
        return Factories.get(id);
    }

    /**
     * Removes a factory by ID.
     * 
     * @param player The player requesting the removal.
     * @param id     The ID of the factory to be removed.
     * @return True if the factory was successfully removed, false otherwise.
     * @throws SQLException If an SQL error occurs.
     */
    public boolean removeFactory(Player player, int id) throws SQLException {
        return removeFactory(player, getFactoryByID(id));
    }

    /**
     * Removes a factory by its instance.
     * 
     * @param player The player requesting the removal.
     * @param f      The Factory to be removed.
     * @return True if the factory was successfully removed, false otherwise.
     * @throws SQLException If an SQL error occurs.
     */
    public boolean removeFactory(Player player, Factory f) throws SQLException {
        RemoveFactoryEvent evt = new RemoveFactoryEvent(player, f);
        plugin.triggerEvent(evt);
        if (!evt.isCancelled()) {
            plugin.Databases.Factory.TabFactory.remove(f);
            return Factories.remove(f.getID()) != null;
        }
        return false;
    }

    /**
     * Retrieves a list of factories owned or managed by the player.
     * 
     * @param player The player whose factories are being retrieved.
     * @return A List of factories owned or managed by the player.
     */
    public List<Factory> getFactoriesByPlayer(Player player) {
        List<Factory> result = new ArrayList<>();
        for (Factory f : Factories.values()) {
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
     * Gets a list of all factory plots.
     * 
     * @return A List of areas that are factory plots.
     */
    public List<Area> getAllFactoryPlots() {
        List<Area> l = new ArrayList<>();
        Factories.values().stream().filter(f -> (f.hasFactoryPlots())).forEachOrdered(f -> {
            f.getPlots().forEach(fa -> {
                l.add(fa);
            });
        });
        return l;
    }
}
