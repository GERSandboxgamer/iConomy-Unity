package de.sbg.unity.iconomy;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import net.risingworld.api.Internals;
import net.risingworld.api.Plugin;
import net.risingworld.api.Server;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerSpawnEvent;
import net.risingworld.api.objects.Player;

/**
 * The Update class is responsible for checking if the iConomy plugin has an
 * update available. It compares the current version of the plugin with the
 * online version from a provided URL and informs the server console or players
 * (if they are admins) about available updates.
 */
public final class Update implements Listener {

    private final URI url;
    private Scanner Scanner;
    private String resultString;
    private final String currentVersion;
    private final Plugin plugin;
    private final boolean developmentVersion;

    /**
     * Creates a new Update object and initiates a scan for updates.
     *
     * @param plugin The instance of the iConomy plugin.
     * @param URL The URL where the current version of the plugin is listed.
     * @throws IOException If an I/O error occurs during the update check.
     * @throws URISyntaxException If the provided URL is malformed.
     * @hidden Only for DEV
     */
    public Update(Plugin plugin, String URL) throws IOException, URISyntaxException {
        this.plugin = plugin;
        this.currentVersion = plugin.getDescription("version");
        this.url = new URI(URL);
        developmentVersion = currentVersion.split("\\.").length == 4;
        Scanner = new Scanner(url.toURL().openStream());
        if (Scanner.hasNext()) {
            resultString = Scanner.nextLine();
        } else {
            resultString = null;
        }
        Scanner.close();
        DoScann(false, true);
    }

    /**
     * Scans the provided URL for an updated version and prints the results in
     * the console or the plugin.
     *
     * @param newScann Whether to perform a new scan for updates.
     * @param console Whether to output the results to the console.
     * @throws IOException If an I/O error occurs during the scan.
     */
    public void DoScann(boolean newScann, boolean console) throws IOException {
        if (newScann) {
            newScann();
        }
        String Plugin = plugin.getDescription("name");
        if (hasUpdate()) {
            plugin.registerEventListener(this);
            if (console) {
                if (!developmentVersion) {
                    Internals.println("[" + Plugin + "-Warning] [Update] -------- New Update --------", 14);
                    Internals.println("[" + Plugin + "-Warning] [Update] Plugin is NOT up to date", 14);
                    Internals.println("[" + Plugin + "-Warning] [Update] Current Version: " + currentVersion, 14);
                    Internals.println("[" + Plugin + "-Warning] [Update]     New Version: " + resultString, 14);
                    Internals.println("[" + Plugin + "-Warning] [Update] ----------------------------", 14);
                } else {
                    Internals.println("[" + Plugin + "-Warning] [DEV] This version is a development version!", 14);
                    Internals.println("[" + Plugin + "-Warning] [DEV] Use it on your one risk!", 14);
                }
            }
        } else {
            Internals.println("[" + Plugin + "-Info] [Update] Plugin is up to date!", 10);
        }
    }

    /**
     * Scans the URL for updates and refreshes the current result.
     *
     * @throws IOException If an I/O error occurs during the scan.
     */
    private void newScann() throws IOException {
        Scanner = new Scanner(url.toURL().openStream());
        if (Scanner.hasNext()) {
            resultString = Scanner.nextLine();
        } else {
            resultString = null;
        }
        Scanner.close();
    }

    /**
     * Checks if an update is available by comparing the current version to the
     * online version.
     *
     * @return True if an update is available, false otherwise.
     */
    public boolean hasUpdate() {
        if (resultString != null) {
            return !currentVersion.equals(resultString);
        }
        return false;
    }

    /**
     * Gets the current version of the plugin.
     *
     * @return A String representing the current version.
     */
    public String getCurrentVersion() {
        return currentVersion;
    }

    /**
     * Gets the version of the plugin available online.
     *
     * @return A String representing the online version, or null if not
     * available.
     */
    public String getOnlineVersion() {
        return resultString;
    }

    /**
     * Event listener that notifies players when they spawn into the game if
     * there is an update available. Admins will be notified if they are on a
     * multiplayer server.
     *
     * @param event The PlayerSpawnEvent that triggers when a player spawns.
     */
    @EventMethod
    public void onPlayerConnectEvent(PlayerSpawnEvent event) {
        Player player = event.getPlayer();
        if (hasUpdate() && !developmentVersion) {
            if (Server.getType() == Server.Type.Singleplayer) {
                player.sendTextMessage(Color("Orange", "[" + plugin.getDescription("name") + "] Plugin has an update!"));
            } else {
                if (player.isAdmin()) {
                    player.sendTextMessage(Color("Orange", "[" + plugin.getDescription("name") + "] Plugin has an update!"));
                }
            }
        }
        if (developmentVersion) {
            if (Server.getType() == Server.Type.Singleplayer) {
                player.sendTextMessage(Color("Orange", "[" + plugin.getDescription("name") + "] This is development Version!"));
            } else {
                if (player.isAdmin()) {
                    player.sendTextMessage(Color("Orange", "[" + plugin.getDescription("name") + "] This is development Version!"));
                }
            }
        }
    }

    /**
     * Helper method to format text with color.
     *
     * @param color The color to be used (e.g., "Orange").
     * @param text The text to be colored.
     * @return The formatted string with color.
     */
    private String Color(String color, String text) {
        return "<color=" + color + ">" + text + "</color>";
    }
}
