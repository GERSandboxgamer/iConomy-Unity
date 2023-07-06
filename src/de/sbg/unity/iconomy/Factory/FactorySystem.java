package de.sbg.unity.iconomy.Factory;

import de.sbg.unity.iconomy.Events.Factory.AddFactoryEvent;
import de.sbg.unity.iconomy.Events.Factory.RemoveFactoryEvent;
import de.sbg.unity.iconomy.Exeptions.FactoryAlreadyExistsExeption;
import de.sbg.unity.iconomy.Utils.Attribute;
import de.sbg.unity.iconomy.iConomy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import net.risingworld.api.objects.Area;
import net.risingworld.api.objects.Player;

public class FactorySystem {

    private final HashMap<Integer, Factory> Factories;
    private final Attribute att;
    private final iConomy plugin;

    public FactorySystem(iConomy plugin, Attribute att) {
        this.plugin = plugin;
        this.Factories = new HashMap<>();
        this.att = att;
    }

    public HashMap<Integer, Factory> getHashFactories() {
        return Factories;
    }

    public Collection<Factory> getFactorys() {
        return Factories.values();
    }

    public List<String> getAllFactoryNames() {
        List<String> result = new ArrayList<>();
        for (Factory f : Factories.values()) {
            result.add(f.getName());
        }
        return result;
    }

    public Factory addNewFactory(Player player, String FactoryName) throws FactoryAlreadyExistsExeption, SQLException {
        if (!getAllFactoryNames().contains(FactoryName)) {

            AddFactoryEvent evt = new AddFactoryEvent(player);
            plugin.triggerEvent(evt);
            if (!evt.isCancelled()) {
                int id = plugin.Databases.Factory.TabFactory.add(FactoryName);
                Factory f = new Factory(plugin, FactoryName, id);
            }

        } else {
            throw new FactoryAlreadyExistsExeption("Factory already exists with the name: " + FactoryName);
        }
        return null;
    }

    public List<Factory> getFactoryByName(String name) {
        List<Factory> list = new ArrayList<>();
        for (Factory f : Factories.values()) {
            if (f.getName().matches(name)) {
                list.add(f);
            }
        }
        return list;
    }

    public Factory getFactoryByPlot(Player player) {
        Area a = player.getCurrentArea();
        if (isFactoryPlot(a)) {
            return att.Area.getFactory(a);
        }

        return null;
    }

    public boolean isFactoryPlot(Area area) {
        return getAllFactoryPlots().stream().anyMatch(a -> (a.getID() == area.getID()));
    }

    public boolean isFactoryPlot(int id) {
        return getAllFactoryPlots().stream().anyMatch(a -> (a.getID() == id));
    }

    public Factory getFactoryByID(int id) {
        return Factories.get(id);
    }

    public boolean removeFactory(Player player, int id) throws SQLException {
        return removeFactory(player, getFactoryByID(id));

    }

    public boolean removeFactory(Player player, Factory f) throws SQLException {
        RemoveFactoryEvent evt = new RemoveFactoryEvent(player, f);
        plugin.triggerEvent(evt);
        if (!evt.isCancelled()) {
            plugin.Databases.Factory.TabFactory.remove(f);
            return Factories.remove(f.getID()) != null;
        }
        return false;
    }

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
