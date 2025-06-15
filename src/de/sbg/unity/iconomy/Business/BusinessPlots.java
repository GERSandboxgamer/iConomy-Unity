package de.sbg.unity.iconomy.Business;

import de.sbg.unity.iconomy.Permissions.BusinessPlotPermission;
import de.sbg.unity.iconomy.iConomy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.risingworld.api.Server;
import net.risingworld.api.objects.Area;
import net.risingworld.api.utils.Quaternion;
import net.risingworld.api.utils.Vector3f;

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

    public HashMap<Area, BusinessPlot> getHashPlots() {
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
    
    /**
     * Add the Plot to the Database and to the Server.
     * @param area
     * @return
     * @throws SQLException
     */
    public BusinessPlot addPlot(Area area) throws SQLException {
        BusinessPlot fp = new BusinessPlot(area);
        plots.put(area, fp);
        plugin.Databases.Business.TabelPlots.add(fp);
        Server.addArea(area, true);
        return fp; 
    }
    
    public boolean removePlot(Area area) throws SQLException {
        Server.removeArea(area);
        plugin.Databases.Business.TabelPlots.remove(area.getID());
        return plots.remove(area) != null;
    }
    
    void removeAllBusinessPlots(Business b) throws SQLException{
        plugin.Databases.Business.TabelPlots.removeByBusiness(b.getID());
        for (BusinessPlot plot : getAllPlotsFromBusiness(b)) {
            Server.removeArea(plot.getArea());
            plots.remove(plot.getArea());
        }
    }

    public class BusinessPlot {

        private final Area area;
        private String name;
        private long price;
        private Business business;
        private String LeaveMsg;
        private String EnterMsg;
        private String Titel;
        private Vector3f TeleportPosition;
        private Quaternion TeleportRotation;
        private long inputChest;
        private long outputChest;
        
        public final NoBusinessPlayerPermission noBusinessPlayerPermission;

        public BusinessPlot(Area area) {
            this.area = area;
            this.price = 0;
            this.business = null;
            this.name = "";
            this.noBusinessPlayerPermission = new NoBusinessPlayerPermission();
        }

        public BusinessPlot(Area area, Business f) {
            this.area = area;
            this.price = 0;
            this.business = f;
            this.name = "";
            this.noBusinessPlayerPermission = new NoBusinessPlayerPermission();
        }

        public BusinessPlot(Area area, String name) {
            this.area = area;
            this.price = 0;
            this.business = null;
            this.name = name;
            this.noBusinessPlayerPermission = new NoBusinessPlayerPermission();
        }

        public BusinessPlot(Area area, String name, long price) {
            this.area = area;
            this.price = price;
            this.business = null;
            this.name = name;
            this.noBusinessPlayerPermission = new NoBusinessPlayerPermission();
        }

        public BusinessPlot(Area area, Business f, String name) {
            this.area = area;
            this.price = 0;
            this.business = f;
            this.name = name;
            this.noBusinessPlayerPermission = new NoBusinessPlayerPermission();
        }

        public BusinessPlot(Area area, Business f, String name, long price) {
            this.area = area;
            this.price = price;
            this.business = f;
            this.name = name;
            this.noBusinessPlayerPermission = new NoBusinessPlayerPermission();
        }

        public BusinessPlot(Area area, long price) {
            this.area = area;
            this.price = price;
            this.business = null;
            this.name = "";
            this.noBusinessPlayerPermission = new NoBusinessPlayerPermission();
        }

        public long getOutputChest() {
            return outputChest;
        }

        public void setOutputChest(long outputChest) {
            this.outputChest = outputChest;
        }

        public long getInputChest() {
            return inputChest;
        }

        public Vector3f getTeleportPosition() {
            return TeleportPosition;
        }

        public Quaternion getTeleportRotation() {
            return TeleportRotation;
        }

        public void setInputChest(long inputChest) {
            this.inputChest = inputChest;
        }

        public void setTeleportPosition(Vector3f TeleportPosition) {
            this.TeleportPosition = TeleportPosition;
        }

        public void setTeleportRotation(Quaternion TeleportRotation) {
            this.TeleportRotation = TeleportRotation;
        }

        public String getEnterMsg() {
            return EnterMsg;
        }

        public Business getBusiness() {
            return business;
        }

        public String getLeaveMsg() {
            return LeaveMsg;
        }

        public String getTitel() {
            return Titel;
        }

        public void setEnterMsg(String EnterMsg) {
            this.EnterMsg = EnterMsg;
        }

        public void setLeaveMsg(String LeaveMsg) {
            this.LeaveMsg = LeaveMsg;
        }

        public void setTitel(String Titel) {
            this.Titel = Titel;
        }
        
        public Area getArea() {
            return area;
        }

        public void setBusiness(Business business) {
            this.business = business;
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
    
    public class NoBusinessPlayerPermission {

        private final Set<BusinessPlotPermission> permissions;

        public NoBusinessPlayerPermission() {
            this.permissions = new HashSet<>();
        }

        public boolean hasPermission(BusinessPlotPermission per) {
            return permissions.contains(per);
        }

        public void addPermission(BusinessPlotPermission per) {
            permissions.add(per);
        }

        public boolean removePermission(BusinessPlotPermission per) {
            return permissions.remove(per);
        }
    }
}
