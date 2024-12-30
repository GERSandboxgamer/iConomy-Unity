
package de.sbg.unity.iconomy.Objects;

public class OfflinePlayer {
    
    private final String uid, name;
    
    /**
     * Create new OfflinePlayer
     * @param uid UID
     * @param name Name 
     */
    public OfflinePlayer(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUID() {
        return uid;
    }
}
