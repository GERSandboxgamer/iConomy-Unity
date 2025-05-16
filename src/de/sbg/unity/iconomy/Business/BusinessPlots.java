package de.sbg.unity.iconomy.Business;

import de.sbg.unity.iconomy.iConomy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import net.risingworld.api.objects.Area;

public class BusinessPlots {

    private final HashMap<Area, BusinessPlot> plots;
    private final iConomy plugin;
    

    public BusinessPlots(iConomy plugin) {
        plots = new HashMap<>();
        this.plugin = plugin;
    }

    public Collection<BusinessPlot> getAllPlots() {
        return plots.values();
    }

    public HashMap<Area, BusinessPlot> getPlots() {
        return plots;
    }

    public List<BusinessPlot> getAllPlotsFromBusiness(Business f) {
        return getAllPlotsFromBusiness(f.getID());
    }

    public List<BusinessPlot> getAllPlotsFromBusiness(int factoryID) {
        return getAllPlots().stream().filter((t) -> t.getBusiness().getID() == factoryID).toList();
    }

    public Collection<Area> getAllAreas() {
        return plots.keySet();
    }

    public BusinessPlot getPlot(Area area) {
        return plots.get(area);
    }
    
    public boolean isPlot(Area area) {
        return getAllAreas().contains(area);
    }

    public List<Area> getAreasByBusiness(Business f) {
        List<Area> list = new ArrayList<>();
        getAllPlots().stream().filter((t) -> t.getBusiness() == f).forEach((p) -> {
            list.add(p.getArea());
        });
                
      return list;
    }
    
    public Business getBusinessByArea(Area area) {
        List<Business> fs = new ArrayList<>();
        getAllPlots().stream().filter((t) -> t.getArea() == area).forEach((p) -> {
            fs.add(p.getBusiness());
        });
        return fs.get(0);
    }
    public BusinessPlot addPlot(Area area) throws SQLException {
        BusinessPlot fp = new BusinessPlot(area);
        plots.put(area, fp);
        plugin.Databases.Business.TabelPlots.add(fp);
        return fp; 
    }

    public class BusinessPlot {

        private final Area area;
        private String name;
        private long price;
        private Business factory;

        public BusinessPlot(Area area) {
            this.area = area;
            this.price = 0;
            this.factory = null;
            this.name = "";
        }

        public BusinessPlot(Area area, Business f) {
            this.area = area;
            this.price = 0;
            this.factory = f;
            this.name = "";
        }

        public BusinessPlot(Area area, String name) {
            this.area = area;
            this.price = 0;
            this.factory = null;
            this.name = name;
        }

        public BusinessPlot(Area area, String name, long price) {
            this.area = area;
            this.price = price;
            this.factory = null;
            this.name = name;
        }

        public BusinessPlot(Area area, Business f, String name) {
            this.area = area;
            this.price = 0;
            this.factory = f;
            this.name = name;
        }

        public BusinessPlot(Area area, Business f, String name, long price) {
            this.area = area;
            this.price = price;
            this.factory = f;
            this.name = name;
        }

        public BusinessPlot(Area area, long price) {
            this.area = area;
            this.price = price;
            this.factory = null;
            this.name = "";
        }

        public Area getArea() {
            return area;
        }

        public Business getBusiness() {
            return factory;
        }

        public void setBusiness(Business factory) {
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
