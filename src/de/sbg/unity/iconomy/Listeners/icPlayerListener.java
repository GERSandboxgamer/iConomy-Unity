package de.sbg.unity.iconomy.Listeners;

import de.sbg.unity.iconomy.Utils.TextFormat;
import de.sbg.unity.iconomy.iConomy;
import de.sbg.unity.iconomy.icConsole;
import java.sql.SQLException;
import net.risingworld.api.Server;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerSpawnEvent;
import net.risingworld.api.objects.Player;

public class icPlayerListener implements Listener {

    private final iConomy plugin;
    private final icConsole Console;
    private final TextFormat format;

    public icPlayerListener(iConomy plugin, icConsole Console) {
        this.plugin = plugin;
        this.Console = Console;
        this.format = new TextFormat();
    }

    @EventMethod
    public void onPlayerSpawnEvent(PlayerSpawnEvent event) {
        Player player = event.getPlayer();
        
        try {
            if (plugin.CashSystem.addPlayer(player)) {
                player.sendTextMessage(format.Color("orange", "Hello new Player! You become for start: " + plugin.CashSystem.getCashAsFormatedString(player)));
            }
        } catch (SQLException ex) {
            Console.sendErr("DB-SQL", "Can not save player to Database!");
                Console.sendErr("DB-SQL", "Ex-Msg: " + ex.getMessage());
                Console.sendErr("DB-SQL", "Ex-SQLState: " + ex.getSQLState());
                ex.printStackTrace();
                Console.sendErr("SERVER", "STOP SERVER!");
                Server.shutdown();
        }
    }

}
