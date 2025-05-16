package de.sbg.unity.iconomy.GUI;

import de.sbg.unity.iconomy.Events.Objects.Suitcase.SuitcaseRemoveEvent;
import de.sbg.unity.iconomy.Objects.SuitcaseObject;
import de.sbg.unity.iconomy.iConomy;
import java.util.Arrays;
import net.risingworld.api.Server;
import net.risingworld.api.Timer;
import net.risingworld.api.objects.Player;
import net.risingworld.api.worldelements.Text3D;

public class SuitcaseTimer extends Text3D {

    private final Timer showTimer;
    private float time;

    public SuitcaseTimer(iConomy plugin, SuitcaseObject suitcase, String playername, float time) {
        super("");
        this.time = time;
        this.setBillboard(true);
        this.setLocalPosition(suitcase.getLocalPosition().x, suitcase.getLocalPosition().y + 2, suitcase.getLocalPosition().z);
        this.setText(playername + ": " + formatTime(this.time));
        this.setFontSize(1);
        this.showTimer = new Timer(1.0f, 0, -1, () -> {
            this.time--;
            if (time > 0) {
                this.setText(playername + ": " + formatTime(this.time));
            } else {
                plugin.GameObject.suitcase.remove(suitcase, SuitcaseRemoveEvent.Cause.Time);
                removeTimer();
            }
        });
        Arrays.asList(Server.getAllPlayers()).forEach((p2) -> {
            p2.addGameObject(this);
        });
        showTimer.start();
    }

    public Timer getShowTimer() {
        return showTimer;
    }

    private String formatTime(float totalSecondsFloat) {
        int totalSeconds = (int) totalSecondsFloat;
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
    
    /**
     * Removed the timer for all players
     */
    public void removeTimer() {
        showTimer.kill();
        Arrays.asList(Server.getAllPlayers()).forEach((p2) -> {
            p2.removeGameObject(this);
        });
    }

    public void removeTimer(Player p2) {
        p2.removeGameObject(this);
    }
}
