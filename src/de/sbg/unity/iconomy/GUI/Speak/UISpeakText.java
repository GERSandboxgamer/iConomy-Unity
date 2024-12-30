package de.sbg.unity.iconomy.GUI.Speak;

import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.UIScrollView;
import net.risingworld.api.ui.style.FlexDirection;
import net.risingworld.api.ui.style.Justify;
import net.risingworld.api.ui.style.Pivot;
import net.risingworld.api.ui.style.TextAnchor;
import net.risingworld.api.ui.style.Unit;
import net.risingworld.api.utils.ColorRGBA;


public class UISpeakText extends UIScrollView{

    public UISpeakText() {
        super(ScrollViewMode.VerticalAndHorizontal);
        this.style.flexDirection.set(FlexDirection.Column);
        this.setHorizontalScrollerVisibility(ScrollerVisibility.Hidden);
        this.setVerticalScrollerVisibility(ScrollerVisibility.Auto);
        this.setBackgroundColor(ColorRGBA.Black.r, ColorRGBA.Black.g, ColorRGBA.Black.b,0.75f);
        this.setBorder(2);
        this.setBorderColor(ColorRGBA.White.toIntRGBA());
        this.style.justifyContent.set(Justify.SpaceBetween);
    }
    
    public void setNewText(String[] text) {
        this.removeAllChilds();
        for (String s : text) {
            UILabel lab = new UILabel(s);
            lab.style.width.set(100, Unit.Percent);
            lab.style.height.set(37, Unit.Pixel);
            lab.setTextAlign(TextAnchor.MiddleCenter);
            lab.setPivot(Pivot.UpperLeft);
            lab.setFontSize(30);
            this.addChild(lab);
        }
    }
    
}
