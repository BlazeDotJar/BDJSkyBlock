package de.bdj.sb.event;

import de.bdj.sb.Settings;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.XColor;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener {

    public static void onMove(PlayerMoveEvent e) {
        if(!e.getPlayer().getWorld().getName().equalsIgnoreCase(Settings.sbOverworldName) &&
                !e.getPlayer().getWorld().getName().equalsIgnoreCase(Settings.sbOverworldName) &&
                !e.getPlayer().getWorld().getName().equalsIgnoreCase(Settings.sbOverworldName)) {
            return;
        }
        if(e.getTo() == null) return;

        if (e.getFrom().getBlockX() != e.getTo().getBlockX() ||
                e.getFrom().getBlockY() != e.getTo().getBlockY() ||
                e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {

            IslandProfile ip = IslandManager.getIslandLocationIsIn(e.getPlayer().getLocation());
            if(ip != null && ip.getIslandId() != ProfileManager.getProfile(e.getPlayer().getUniqueId()).getIslandIsCurrentIn()) {
               e.getPlayer().sendTitle("", XColor.green + "Du hast die Insel " + ip.getIslandId() + " Betreten", 10, 20, 10);
                ProfileManager.getProfile(e.getPlayer().getUniqueId()).setIslandIsCurrentIn(ip.getIslandId());
            }
        }
    }

}
