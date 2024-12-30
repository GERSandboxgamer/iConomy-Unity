package de.sbg.unity.iconomy.GUI.Speak;

import de.sbg.unity.iconomy.Npc.SpeakSystem.SpeakObject.SpeakAnswer;
import java.util.List;
import net.risingworld.api.ui.UIScrollView;
import net.risingworld.api.ui.style.FlexDirection;

public class UIAnswerList extends UIScrollView{

    public UIAnswerList() {
        super(ScrollViewMode.VerticalAndHorizontal);
        this.setHorizontalScrollerVisibility(ScrollerVisibility.Hidden);
        this.setVerticalScrollerVisibility(ScrollerVisibility.AlwaysVisible);
        this.style.flexDirection.set(FlexDirection.Column);
    }

    public void setNewText(List<SpeakAnswer> answers) {
        this.removeAllChilds();
        for (SpeakAnswer s : answers) {
            this.addChild(new AnswerObject(s));
        }
    }
    
    

}
