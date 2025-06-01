package de.sbg.unity.iconomy.Npc;

import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.Timer;
import net.risingworld.api.objects.Npc;
import net.risingworld.api.objects.Player;
import net.risingworld.api.utils.Vector3f;

public class FollowSystem {

    private final Player player;
    private Npc npc;
    private final List<Vector3f> positionList;
    private boolean writePosition;
    private int distance;
    private boolean writeStandby;
    private Timer walkTmer;

    private static final int MAX_POSITION_LIST_SIZE = 40;
    private static final float TELEPORT_DISTANCE_THRESHOLD = 30.0f;
    private static final float ARRIVED_THRESHOLD = 0.5f;
    private static final float STUCK_DISTANCE_THRESHOLD = 0.1f;
    private int stuckCounter;
    private Vector3f lastNpcPositionCheck;

    private boolean isNpcLocked;
    private final icConsole Console;
    private final iConomy plugin;

    public FollowSystem(iConomy plugin, Player player) {
        this.player = player;
        this.positionList = new ArrayList<>();
        this.writePosition = false;
        this.distance = 5;
        this.writeStandby = false;
        this.stuckCounter = 0;
        this.lastNpcPositionCheck = null;
        this.isNpcLocked = false;
        this.Console = new icConsole(plugin);
        this.plugin = plugin;
    }

    //region Getter und Setter (unverändert)
    public int getDistance() {
        return distance;
    }

    public Npc getNpc() {
        return npc;
    }

    public void setNpc(Npc npc) {
        this.npc = npc;
    }

    public void setDistance(int distance) {
        this.distance = Math.max(1, distance);
    }

    public boolean isWritePosition() {
        return writePosition;
    }

    public void setWritePosition(boolean writePosition) {
        this.writePosition = writePosition;
        if (writePosition) {
            positionList.clear();
            stuckCounter = 0;
            lastNpcPositionCheck = null;
            // Wenn das Schreiben wieder beginnt, den NPC entsperren, falls er gelockt war
            if (npc != null && isNpcLocked) {
                npc.setLocked(false);
                isNpcLocked = false;
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public List<Vector3f> getPositionList() {
        return positionList;
    }

    public void addPosition(Vector3f pos) {
        if (writePosition || !writeStandby) {
            if (positionList.size() >= MAX_POSITION_LIST_SIZE) {
                positionList.remove(0);
            }
            Console.sendDebug("NPC-AddPos", "Add Position:  " + pos.toString());
            positionList.add(pos);
        }
    }

    public boolean hasPosition() {
        return positionList.size() >= getDistance();
    }

    public boolean hasPosition(int distance) {
        return positionList.size() >= distance;
    }

    public void setWriteStandby(boolean writeStandby) {
        this.writeStandby = writeStandby;
    }

    //endregion
    public void startFollow() {
        if (walkTmer != null) {
            walkTmer.kill();
        }
        this.walkTmer = new Timer(0.1f, 0f, -1, () -> {
            follow();
        });
        walkTmer.start();
        stuckCounter = 0;
        if (npc != null) {
            lastNpcPositionCheck = npc.getPosition();
            // Beim Start des Folgens sicherstellen, dass NPC entsperrt ist
            if (isNpcLocked) {
                npc.setLocked(false);
                isNpcLocked = false;
            }
        }
    }

    /**
     * Stoppt das Folgen des NPC und friert ihn an seiner aktuellen Position
     * ein.
     */
    public void stopFollow() {
        if (walkTmer != null) {
            walkTmer.kill();
            walkTmer = null;
        }
        if (this.npc != null) {
            this.npc.setLocked(true); // NPC einfrieren, wie gewünscht
            this.isNpcLocked = true; // Internen Zustand aktualisieren
        }
        this.npc = null; // NPC-Referenz löschen
        positionList.clear();
        stuckCounter = 0;
        lastNpcPositionCheck = null;
        // isNpcLocked bleibt auf true, wenn NPC gesetzt war, sonst false
    }

    private void follow() {
        if (this.npc == null) {
            return;
        }

        Vector3f currentNpcPos = npc.getPosition();

        // --- Prüfung auf Feststecken oder zu große Entfernung (Teleportationslogik) ---
        // Fall 1: NPC ist zu weit vom Spieler entfernt -> Teleportieren und sperren
        if (player.getPosition().distance(currentNpcPos) > TELEPORT_DISTANCE_THRESHOLD) {
            npc.setPosition(player.getPosition().add(new Vector3f(1, 0, 1))); // Leicht versetzt zum Spieler
            npc.setLocked(true); // NEU: NPC nach Teleportation sperren
            isNpcLocked = true;   // Internen Zustand aktualisieren
            positionList.clear(); // Liste leeren, um von neuem zu beginnen
            stuckCounter = 0;
            lastNpcPositionCheck = npc.getPosition();
            return;
        }

        // Wenn der NPC **gesperrt** ist, prüfen, ob er entsperrt werden muss
        // Dies ist die Hauptlogik zum "Wiederbeleben" eines gesperrten NPC
        if (isNpcLocked) {
            // Entsperren, wenn genügend neue Positionen da sind (Spieler läuft wieder)
            if (positionList.size() > this.distance) {
                npc.setLocked(false);
                isNpcLocked = false;
                // Dann den Rest der Logik ausführen, um sofort loszulaufen
            } else {
                // Immer noch gesperrt, da Spieler sich nicht genug bewegt hat, oder Liste zu klein.
                return; // Nichts weiter tun, solange er gesperrt ist und keine Freigabe-Bedingung erfüllt ist
            }
        }

        // --- Logik für das Halten der Distanz und das Bewegen ---
        // NPC wartet, bis genügend Positionen für seinen Abstand gesammelt wurden.
        // Oder wenn er seine letzte "Abstandsposition" erreicht hat.
        if (positionList.size() <= this.distance) {
            // NPC hat seinen Abstand erreicht oder ist noch nicht genug zurückgeblieben.
            // Er soll stehen bleiben und gesperrt werden, um Trippeln zu vermeiden.
            if (!isNpcLocked) { // Sperren, falls er nicht schon gesperrt ist
                npc.setLocked(true);
                isNpcLocked = true;
            }
            stuckCounter = 0;
            lastNpcPositionCheck = currentNpcPos;
            return;
        }

        // Wenn die Liste groß genug ist, bestimme die Zielposition.
        int targetIndex = positionList.size() - this.distance;
        targetIndex = Math.max(0, targetIndex);
        Vector3f targetPos = positionList.get(targetIndex);

        // Überprüfen, ob der NPC nah genug am aktuellen Zielpunkt ist.
        if (currentNpcPos.distance(targetPos) < ARRIVED_THRESHOLD) {
            // NPC hat seinen Zielpunkt erreicht oder ist sehr nah dran.
            // Er soll dort bleiben und gesperrt werden, um Trippeln zu vermeiden.
            if (!isNpcLocked) { // Sperren, falls er nicht schon gesperrt ist
                npc.setLocked(true);
                isNpcLocked = true;
            }
            stuckCounter = 0;
            lastNpcPositionCheck = currentNpcPos;
            return;
        } else {
            // NPC zum Ziel bewegen, da er noch nicht angekommen ist.
            // Hier wird er nur bewegt, wenn er NICHT gelockt war (was durch die obige isNpcLocked-Prüfung gewährleistet ist)
            npc.moveTo(targetPos);

            // --- Feststecken-Erkennung ---
            if (lastNpcPositionCheck != null && currentNpcPos.distance(lastNpcPositionCheck) < STUCK_DISTANCE_THRESHOLD) {
                stuckCounter++;
            } else {
                stuckCounter = 0;
            }
            lastNpcPositionCheck = currentNpcPos;

            // Fall 2: NPC steckt fest -> Teleportieren und sperren
            if (stuckCounter > 50) { // 50 Ticks * 0.1s/Tick = 5 Sekunden
                npc.setPosition(player.getPosition().add(new Vector3f(1, 0, 1))); // Leicht versetzt zum Spieler
                npc.setLocked(true); // NEU: NPC nach Teleportation sperren
                isNpcLocked = true;   // Internen Zustand aktualisieren
                positionList.clear(); // Liste leeren
                stuckCounter = 0;
                lastNpcPositionCheck = npc.getPosition();
            }
        }

    }
}
