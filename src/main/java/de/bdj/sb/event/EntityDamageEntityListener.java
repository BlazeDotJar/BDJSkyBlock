package de.bdj.sb.event;

import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.profile.PlayerProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.Chat;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageEntityListener {

    public static void onDamage(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player damager) {
            if(e.getEntity() instanceof Player victim) {
                PlayerProfile pro = ProfileManager.getProfile(damager.getUniqueId());
                if(pro.getIslandIsCurrentIn() == 0) {
                    pro.setIslandIsCurrentIn(IslandManager.getIslandLocationIsIn(damager.getLocation()).getIslandId());
                }
                IslandProfile ip = IslandManager.getLoadedIslandProfile(pro.getIslandIsCurrentIn());
                if(ip == null) {
                    return;
                }
                if(!ip.getArea().isIn(victim.getLocation())) {
                    return;
                }
                String value = ip.getProperties().get("pvp");
                if(value != null && value.equalsIgnoreCase("false")) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

}
