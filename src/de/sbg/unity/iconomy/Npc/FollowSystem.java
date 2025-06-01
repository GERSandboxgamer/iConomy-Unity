package de.sbg.unity.iconomy.Npc;

import java.util.ArrayList;
import java.util.List;
import net.risingworld.api.Timer;
import net.risingworld.api.objects.Npc;
import net.risingworld.api.objects.Player;
import net.risingworld.api.utils.Vector3f;

public class FollowSystem { // Public class

    private final Player player;
    private Npc npc;
    private final List<Vector3f> positionList;
    private boolean writePosition;
    private int distance;
    private boolean writeStandby;
    private Timer walkTmer;

    private static final int MAX_POSITION_LIST_SIZE = 40;
    private static final float TELEPORT_DISTANCE_THRESHOLD = 30.0f; // Teleportiert, wenn NPC > 30m vom Spieler ist
    private static final float ARRIVED_THRESHOLD = 1.0f; // Schwellenwert, wann der NPC als "angekommen" gilt (z.B. 1 Meter)
    private static final float STUCK_DISTANCE_THRESHOLD = 0.05f; // Wenn NPC sich weniger als 0.05m in X Ticks bewegt
    private static final int STUCK_TICKS = 100; // Anzahl der Ticks (100 * 0.1s = 10 Sekunden), bis als festgesteckt gilt
    private static final float STAND_BEHIND_DISTANCE = 2.0f; // Abstand in Metern, den der NPC hinter dem Spieler halten soll, wenn er stillsteht

    private int stuckCounter;
    private Vector3f lastNpcPositionCheck;

    private boolean isNpcLocked;

    public FollowSystem(Player player) {
        this.player = player;
        this.positionList = new ArrayList<>();
        this.writePosition = false;
        this.distance = 5;
        this.writeStandby = false;
        this.stuckCounter = 0;
        this.lastNpcPositionCheck = null;
        this.isNpcLocked = false;
    }

    //region Getter und Setter

    public int getDistance() {
        return distance;
    }

    public Npc getNpc() {
        return npc;
    }

    public void setNpc(Npc npc) {
        this.npc = npc;
        // Beim Setzen des NPC sicherstellen, dass er sich in einer guten Startposition befindet, falls er nicht schon dort ist
        if (npc != null && player != null) {
            Vector3f playerForward = player.getViewDirection(); // Korrigiert: getViewDirection()
            Vector3f desiredStandPos = player.getPosition().subtract(playerForward.mult(STAND_BEHIND_DISTANCE)); // Korrigiert: mult()
            if (npc.getPosition().distance(desiredStandPos) > 5.0f) { // Wenn mehr als 5m entfernt, teleportieren
                npc.setPosition(desiredStandPos);
                npc.setLocked(true); // Direkt sperren, bis der Spieler sich bewegt
                isNpcLocked = true;
                positionList.clear(); // Liste leeren
            }
        }
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
                positionList.remove(0); // Älteste Position entfernen
            }
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
            if (isNpcLocked) {
                npc.setLocked(false);
                isNpcLocked = false;
            }
        }
    }

    public void stopFollow() {
        if (walkTmer != null) {
            walkTmer.kill();
            walkTmer = null;
        }
        if (this.npc != null) {
            // Berechne die gewünschte Stehposition hinter dem Spieler
            Vector3f playerForward = player.getViewDirection(); // Korrigiert: getViewDirection()
            Vector3f desiredStandPos = player.getPosition().subtract(playerForward.mult(STAND_BEHIND_DISTANCE)); // Korrigiert: mult()
            this.npc.setPosition(desiredStandPos); // NPC an gewünschte Position hinter Spieler setzen
            this.npc.setLocked(true); // NPC einfrieren
            this.isNpcLocked = true; // Internen Zustand aktualisieren
        }
        this.npc = null;
        positionList.clear();
        stuckCounter = 0;
        lastNpcPositionCheck = null;
    }

    private void follow() {
        if (this.npc == null) {
            return;
        }

        Vector3f currentNpcPos = npc.getPosition();
        float distanceToPlayer = player.getPosition().distance(currentNpcPos);

        // --- Primäre Teleportationslogik (Notfall): Wenn NPC zu weit vom Spieler entfernt ist ---
        if (distanceToPlayer > TELEPORT_DISTANCE_THRESHOLD) {
            Vector3f playerForward = player.getViewDirection(); // Korrigiert: getViewDirection()
            Vector3f teleportTargetPos = player.getPosition().subtract(playerForward.mult(STAND_BEHIND_DISTANCE)); // Korrigiert: mult()
            
            npc.setPosition(teleportTargetPos); // Teleportiert
            npc.setLocked(true); // NPC nach Teleportation sperren
            isNpcLocked = true;
            positionList.clear(); // Liste leeren, um von neuem zu beginnen
            stuckCounter = 0;
            lastNpcPositionCheck = npc.getPosition();
            return;
        }
        
        // Wenn der NPC gesperrt ist, prüfen, ob er entsperrt werden muss
        if (isNpcLocked) {
            if (positionList.size() > this.distance) {
                npc.setLocked(false);
                isNpcLocked = false;
            } else {
                return; // Immer noch gesperrt
            }
        }

        // --- Feststecken-Erkennung (bevor der NPC sich bewegt) ---
        if (lastNpcPositionCheck != null) {
            if (currentNpcPos.distance(lastNpcPositionCheck) < STUCK_DISTANCE_THRESHOLD) {
                stuckCounter++;
            } else {
                stuckCounter = 0;
            }
        }
        lastNpcPositionCheck = currentNpcPos;

        // Fall 2: NPC steckt fest -> Teleportieren und sperren
        if (stuckCounter > STUCK_TICKS) {
            Vector3f playerForward = player.getViewDirection(); // Korrigiert: getViewDirection()
            Vector3f teleportTargetPos = player.getPosition().subtract(playerForward.mult(STAND_BEHIND_DISTANCE)); // Korrigiert: mult()
            
            npc.setPosition(teleportTargetPos); // Teleportiert
            npc.setLocked(true); // NPC nach Teleportation sperren
            isNpcLocked = true;
            positionList.clear(); // Liste leeren
            stuckCounter = 0;
            lastNpcPositionCheck = npc.getPosition();
            return;
        }

        // --- Logik für das Halten der Distanz und das Bewegen ---
        // NPC wartet, bis genügend Positionen für seinen Abstand gesammelt wurden.
        // Oder wenn er seine letzte "Abstandsposition" erreicht hat.
        if (positionList.size() <= this.distance) {
            // Wenn der NPC seinen Zielabstand erreicht hat, sollte er die finale Position hinter dem Spieler einnehmen
            // und dort gelockt werden, um das Trippeln zu vermeiden.
            if (!isNpcLocked) {
                Vector3f playerForward = player.getViewDirection(); // Korrigiert: getViewDirection()
                Vector3f desiredStandPos = player.getPosition().subtract(playerForward.mult(STAND_BEHIND_DISTANCE)); // Korrigiert: mult()
                
                // Nur setzen, wenn er nicht schon sehr nah dran ist, um unnötige Teleports zu vermeiden
                if (currentNpcPos.distance(desiredStandPos) > ARRIVED_THRESHOLD) {
                     npc.setPosition(desiredStandPos);
                }
               
                npc.setLocked(true);
                isNpcLocked = true;
            }
            return; // Nichts weiter tun, da er wartet/steht
        }

        // Wenn die Liste groß genug ist, bestimme die Zielposition.
        // Dies ist die Position, die 'distance' Schritte vom Ende der Liste (der neuesten) entfernt ist.
        int targetIndex = positionList.size() - this.distance;
        targetIndex = Math.max(0, targetIndex); 
        Vector3f targetPos = positionList.get(targetIndex);

        // Überprüfen, ob der NPC nah genug am aktuellen Zielpunkt ist.
        if (currentNpcPos.distance(targetPos) < ARRIVED_THRESHOLD) {
            // NPC hat diesen spezifischen Pfadpunkt erreicht.
            // Er soll sich nicht trippeln. Wenn er hier ankommt, bewegt er sich zum nächsten Punkt.
            stuckCounter = 0; // Kein Feststecken, wenn er angekommen ist
            return; 
        } else {
            // NPC zum Ziel bewegen, da er noch nicht angekommen ist.
            npc.moveTo(targetPos);
        }
    }
}