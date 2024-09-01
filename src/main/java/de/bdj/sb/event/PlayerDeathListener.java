package de.bdj.sb.event;

import de.bdj.sb.Settings;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.XColor;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeathListener {

    public static void onPlayerRespawn(PlayerDeathEvent e) {
        e.setDeathMessage(XColor.c6 + e.getEntity().getName()  + " starb.");
    }

    public static void onPlayerRespawn(PlayerRespawnEvent e) {
        String world = e.getPlayer().getWorld().getName();
        if(world.equals(Settings.sbOverworldName) ||
                world.equals(Settings.sbNetherName) ||
                world.equals(Settings.sbEndName)) {
            int islandid = ProfileManager.getProfile(e.getPlayer().getUniqueId()).getIslandId();
            IslandProfile ip = IslandManager.getLoadedIslandProfile(islandid);
            if(islandid > 0) {
                e.setRespawnLocation(ip.getSpawnPoint());
            }
        }
    }

}
