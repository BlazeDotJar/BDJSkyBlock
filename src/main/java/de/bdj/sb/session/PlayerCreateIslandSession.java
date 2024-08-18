package de.bdj.sb.session;

import de.bdj.sb.Settings;
import de.bdj.sb.lobby.Waitlobby;
import de.bdj.sb.utlility.Chat;
import de.bdj.sb.utlility.XColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class PlayerCreateIslandSession extends TempSession {

    private UUID uuid;
    private Player p;
    private int islandId = 0;

    public PlayerCreateIslandSession(Player p) {
        this.uuid = p.getUniqueId();
        this.p = p;
        start();

        //TODO: Handle Player Quitting by overwriting the playerQuit method
    }

    @Override
    public void start() {

        if(Settings.teleportPlayersToWaitlobbyWhenCreating) {
            Waitlobby.teleport(p);
        }

        Chat.info(p, "Rufe freie Inseldaten ab...", "Bitte habe etwas Geduld.");
        p.sendTitle("", XColor.c1 + "Inseldaten werden abgerufen...", 0, 20, 20);
    }

    @Override
    public void stop() {

    }

    @Override
    public void terminate() {

    }

    @Override
    public void chatEvent(AsyncPlayerChatEvent e) {

    }

    public void setIslandId(int value) {
        this.islandId = value;
    }
}
