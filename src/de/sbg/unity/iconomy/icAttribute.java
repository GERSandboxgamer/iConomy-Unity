
package de.sbg.unity.iconomy;

import de.chaoswg.model3d.Model3DPlace;
import net.risingworld.api.objects.Area;
import net.risingworld.api.objects.Npc;
import net.risingworld.api.objects.Player;
import net.risingworld.api.utils.ColorRGBA;
import net.risingworld.api.worldelements.Area3D;


public class icAttribute {
    
    public final icPlayer player;
    public final icNpc npc;
    public final icArea area;
    
    public icAttribute() {
        this.player = new icPlayer();
        this.npc = new icNpc();
        this.area = new icArea();
    }
    
    public class icPlayer {
        private final String icAtmPlacer;
        private final String icDummyMode;
        private final String icSelectNpc;
        private final String icSelectNpcMode;
        private final String icBusinessPlotSelection;
        
        public icPlayer() {
            icAtmPlacer = "sbg-iConomy-icAtmPlacer";
            icDummyMode = "sbg-iConomy-icDummyMode";
            icSelectNpc = "sbg-iConomy-icSelectNpc";
            icSelectNpcMode = "sbg-iConomy-icSelectNpcMode";
            icBusinessPlotSelection = "sbg-iConomy-icBusinessPlotSelection";
        }
        
        public Model3DPlace getPlacer(Player player) {
            return (Model3DPlace)player.getAttribute(icAtmPlacer);
        }
        public void setPlacer(Player player, Model3DPlace placer) {
            player.setAttribute(icAtmPlacer, placer);
        }
        
        public boolean isDummyMode(Player player){
            return (boolean)player.getAttribute(icDummyMode);
        }
        
        public void setDummyMode(Player player, boolean b) {
            player.setAttribute(icDummyMode, b);
        }
        
        public Npc getSelectNpc(Player player) {
            return (Npc)player.getAttribute(icSelectNpc);
        }
        
        public void setSelectNpc(Player player, Npc npc) {
            player.setAttribute(icSelectNpc, npc);
        }
        
        public boolean getNpcSelectMode(Player player) {
            return (boolean)player.getAttribute(icSelectNpcMode);
        }
        
        public void setNpcSelectMode(Player player, boolean b) {
            player.setAttribute(icSelectNpcMode, b);
        }
        
        public boolean getBusinessPlotSelection(Player player) {
            return (boolean)player.getAttribute(icBusinessPlotSelection);
        }
        
        public void setBusinessPlotSelection(Player player, boolean b){
            player.setAttribute(icBusinessPlotSelection, b);
        }
        
    }
    
    public class icNpc {
        
        private final String icNpcMode, icSpeakQuestion;
        
        public icNpc() {
            icNpcMode = "sbg-iConomy-icNpcMode";
            icSpeakQuestion = "sbg-iConomy-icSpeakQuestion";
        }
        
        public void setNpcMode(Npc npc, int mode) {
            npc.setAttribute(icNpcMode, mode);
        }
        
        public int getNpcMode(Npc npc) {
            return (int)npc.getAttribute(icNpcMode);
        }
        
        public void setSpeakQuestion(Npc npc, int sq) {
            npc.setAttribute(icSpeakQuestion, sq);
        }
        
        public int getSpeakQuestion(Npc npc) {
            return (int)npc.getAttribute(icSpeakQuestion);
        }
        
    }
    
    public class icArea {
        private final String areaShow, area3Dst;
        
        public icArea() {
            areaShow = "sbg-iConomy-areaShow";
            area3Dst = "sbg-iConomy-area3Dst";
        }
        
        public boolean getAreaShow(Area area){
            return (boolean)area.getAttribute(areaShow);
        }
        
        public void setAreaShow(Area area, boolean b){
            area.setAttribute(areaShow, b);
        }
        
        public void setArea3D(Area area) {
            Area3D area3D = new Area3D(area);
            area3D.setAlwaysVisible(true);
            area3D.setFrameColor(ColorRGBA.Red);
            area3D.setColor(ColorRGBA.Red);
            area.setAttribute(area3Dst, area3D);
        }
        
        public Area3D getArea3D(Area area) {
            if (area.hasAttribute(area3Dst)) {
                return (Area3D)area.getAttribute(area3Dst);
            }
            Area3D area3D = new Area3D(area);
            area3D.setAlwaysVisible(true);
            area3D.setFrameColor(ColorRGBA.Red);
            area3D.setColor(ColorRGBA.Red);
            area.setAttribute(area3Dst, area3D);
            return area3D;
        }
        
    }
   
    
}
