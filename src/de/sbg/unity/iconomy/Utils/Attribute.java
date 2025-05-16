package de.sbg.unity.iconomy.Utils;

import de.sbg.unity.iconomy.Business.Business;
import de.sbg.unity.iconomy.iConomy;
import net.risingworld.api.objects.Area;


public class Attribute {
    
    public final AreaAttribute Area;
    
    
    public Attribute(iConomy plugin) {
        this.Area = new AreaAttribute(plugin);
    }
    
    public class AreaAttribute {
        
        private final String AreaBusiness;
        
        public AreaAttribute(iConomy plugin) {
            this.AreaBusiness = plugin.getDescription("name") + "-AreaAttribute-AreaBusiness";
        }
        
        public Business getBusiness(Area area) {
            return (Business)area.getAttribute(AreaBusiness);
        }
        
        public void setBusiness(Area area, Business factory) {
            area.setAttribute(AreaBusiness, factory);
        }
        
        public boolean hasBusinessAttribute(Area area) {
            return area.hasAttribute(AreaBusiness);
        }
        
    }
    
}
