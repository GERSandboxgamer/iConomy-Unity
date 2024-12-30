package de.sbg.unity.iconomy.GUI.Speak;

import de.sbg.unity.iconomy.Npc.SpeakSystem.SpeakObject.SpeakAnswer;
import net.risingworld.api.ui.UIElement;
import net.risingworld.api.ui.UILabel;
import net.risingworld.api.ui.style.TextAnchor;
import net.risingworld.api.ui.style.Unit;
import net.risingworld.api.utils.ColorRGBA;

public class AnswerObject extends UIElement {

    private final SpeakAnswer answer;

    public AnswerObject(SpeakAnswer answ) {
        this.answer = answ;
        this.style.width.set(100, Unit.Percent);
        this.style.height.set(30, Unit.Pixel);
        this.setBorder(1);
        this.setBorderColor(ColorRGBA.White.toIntRGBA());
        this.setClickable(true);

        UILabel lab = new UILabel(answ.getText());
        lab.setText(answ.getText());
        lab.setSize(100, 100, true);
        lab.setFontSize(23);
        lab.setTextAlign(TextAnchor.MiddleCenter);
        this.addChild(lab);
    }

    public SpeakAnswer getAnswer() {
        return answer;
    }
}
