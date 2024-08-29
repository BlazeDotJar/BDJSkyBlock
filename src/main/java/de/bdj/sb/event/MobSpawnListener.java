package de.bdj.sb.event;

import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import org.bukkit.entity.Monster;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class MobSpawnListener {

    public static void onSpawn(CreatureSpawnEvent e) {
        if(e.getEntity() instanceof Monster m) {
            if(e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL) return;
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
