package de.bdj.sb.event;

import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.utlility.Chat;
import org.bukkit.entity.Monster;
import org.bukkit.event.entity.EntitySpawnEvent;

public class MobSpawnListener {

    public static void onSpawn(EntitySpawnEvent e) {
        if(e.getEntity() instanceof Monster m) {
            IslandProfile ip = IslandManager.getIslandLocationIsIn(m.getLocation());
            if(ip == null) {
                return;
            }
            if(ip.getProperties().get("natural monster spawn") != null && ip.getProperties().get("natural monster spawn").equalsIgnoreCase("false")) {
                e.setCancelled(true);
                m.remove();
            }
        }
    }

}
