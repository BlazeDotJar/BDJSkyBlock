package de.bdj.sb.event;

import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.profile.PlayerProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.Chat;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener {

    public static void onDamage(EntityDamageEvent e) {
        if(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            if(e.getEntity() instanceof Player victim) {
                PlayerProfile pro = ProfileManager.getProfile(victim.getUniqueId());
                if(pro.getIslandIsCurrentIn() == 0) {
                    pro.setIslandIsCurrentIn(IslandManager.getIslandLocationIsIn(victim.getLocation()).getIslandId());
                }
                IslandProfile ip = IslandManager.getLoadedIslandProfile(pro.getIslandIsCurrentIn());
                if(ip == null) {
                    return;
                }
                if(!ip.getArea().isIn(victim.getLocation())) {
                    return;
                }
                String value = ip.getProperties().get("explosion damage");
                if(value != null && value.equalsIgnoreCase("false")) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

}
