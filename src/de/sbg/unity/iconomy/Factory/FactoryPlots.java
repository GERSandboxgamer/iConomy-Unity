package de.sbg.unity.iconomy.Factory;

import de.sbg.unity.iconomy.iConomy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import net.risingworld.api.objects.Area;

public class FactoryPlots {

    private final HashMap<Area, FactoryPlot> plots;
    private final iConomy plugin;
    

    public FactoryPlots(iConomy plugin) {
        plots = new HashMap<>();
        this.plugin = plugin;
    }

    public Collection<FactoryPlot> getAllPlots() {
        return plots.values();
    }

    public HashMap<Area, FactoryPlot> getPlots() {
        return plots;
    }

    public List<FactoryPlot> getAllPlotsFromFactory(Factory f) {
        return getAllPlotsFromFactory(f.getID());
    }

    public List<FactoryPlot> getAllPlotsFromFactory(int factoryID) {
        return getAllPlots().stream().filter((t) -> t.getFactory().getID() == factoryID).toList();
    }

    public Collection<Area> getAllAreas() {
        return plots.keySet();
    }

    public FactoryPlot getPlot(Area area) {
        return plots.get(area);
    }
    
    public boolean isPlot(Area area) {
        return getAllAreas().contains(area);
    }

    public List<Area> getAreasByFactory(Factory f) {
        List<Area> list = new ArrayList<>();
        getAllPlots().stream().filter((t) -> t.getFactory() == f).forEach((p) -> {
            list.add(p.getArea());
        });
                
      return list;
    }
    
    public Factory getFactoryByArea(Area area) {
        List<Factory> fs = new ArrayList<>();
        getAllPlots().stream().filter((t) -> t.getArea() == area).forEach((p) -> {
            fs.add(p.getFactory());
        });
        return fs.get(0);
    }
    public FactoryPlot addPlot(Area area) throws SQLException {
        FactoryPlot fp = new FactoryPlot(area);
        plots.put(area, fp);
        plugin.Databases.Factory.TabelPlots.add(fp);
        return fp; 
    }

    public class FactoryPlot {

        private final Area area;
        private String name;
        private long price;
        private Factory factory;

        public FactoryPlot(Area area) {
            this.area = area;
            this.price = 0;
            this.factory = null;
            this.name = "";
        }

        public FactoryPlot(Area area, Factory f) {
            this.area = area;
            this.price = 0;
            this.factory = f;
            this.name = "";
        }

        public FactoryPlot(Area area, String name) {
            this.area = area;
            this.price = 0;
            this.factory = null;
            this.name = name;
        }

        public FactoryPlot(Area area, String name, long price) {
            this.area = area;
            this.price = price;
            this.factory = null;
            this.name = name;
        }

        public FactoryPlot(Area area, Factory f, String name) {
            this.area = area;
            this.price = 0;
            this.factory = f;
            this.name = name;
        }

        public FactoryPlot(Area area, Factory f, String name, long price) {
            this.area = area;
            this.price = price;
            this.factory = f;
            this.name = name;
        }

        public FactoryPlot(Area area, long price) {
            this.area = area;
            this.price = price;
            this.factory = null;
            this.name = "";
        }

        public Area getArea() {
            return area;
        }

        public Factory getFactory() {
            return factory;
        }

        public void setFactory(Factory factory) {
            this.factory = factory;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(long price) {
            this.price = price;
        }

        public long getPrice() {
            return price;
        }

        public String getName() {
            return name;
        }

    }
}
