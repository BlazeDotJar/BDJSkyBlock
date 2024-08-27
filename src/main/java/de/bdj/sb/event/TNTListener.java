package de.bdj.sb.event;

import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class TNTListener {

    public static void onExplosion(ExplosionPrimeEvent e) {
        IslandProfile ip = IslandManager.getIslandLocationIsIn(e.getEntity().getLocation());
        if(ip == null) {
            return;
        }
        if(e.getEntity() instanceof Creeper) {
            if(ip.getProperties().get("mob griefing").equalsIgnoreCase("false")) {
                e.setCancelled(true);
                e.getEntity().getWorld().createExplosion(e.getEntity().getLocation(), 0F); // nur Sound und Partikel, keine Zerstörung
                e.getEntity().remove();
            }
        } else if(e.getEntity() instanceof TNTPrimed) {
            if(ip.getProperties().get("tnt damage").equalsIgnoreCase("false")) {
                e.setCancelled(true);
                e.getEntity().getWorld().createExplosion(e.getEntity().getLocation(), 0F); // nur Sound und Partikel, keine Zerstörung
            }
        }
    }

}
