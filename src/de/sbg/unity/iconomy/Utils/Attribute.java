package de.sbg.unity.iconomy.Utils;

import de.sbg.unity.iconomy.Factory.Factory;
import de.sbg.unity.iconomy.iConomy;
import net.risingworld.api.objects.Area;


public class Attribute {
    
    public final AreaAttribute Area;
    
    
    public Attribute(iConomy plugin) {
        this.Area = new AreaAttribute(plugin);
    }
    
    public class AreaAttribute {
        
        private final String AreaFactory;
        
        public AreaAttribute(iConomy plugin) {
            this.AreaFactory = plugin.getDescription("name") + "-AreaAttribute-AreaFactory";
        }
        
        public Factory getFactory(Area area) {
            return (Factory)area.getAttribute(AreaFactory);
        }
        
        public void setFactory(Area area, Factory factory) {
            area.setAttribute(AreaFactory, factory);
        }
        
        public boolean hasFactoryAttribute(Area area) {
            return area.hasAttribute(AreaFactory);
        }
        
    }
    
}
