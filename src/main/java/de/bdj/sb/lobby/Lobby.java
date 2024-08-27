package de.bdj.sb.lobby;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class Lobby {

    private static Location lobbyLoc;

    public Lobby(Location lobbyLoc) {
        Lobby.lobbyLoc = lobbyLoc.clone();
    }

    public static void teleport(Entity ent) {
        ent.teleport(lobbyLoc);
    }

}
