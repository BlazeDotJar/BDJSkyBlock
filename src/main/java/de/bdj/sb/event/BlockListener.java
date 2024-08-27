package de.bdj.sb.event;

import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.Chat;
import de.bdj.sb.utlility.Perms;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener {


    public static void onBlockBreak(BlockBreakEvent e) {
        if(!e.getPlayer().getWorld().getName().equalsIgnoreCase(Settings.sbOverworldName) &&
                !e.getPlayer().getWorld().getName().equalsIgnoreCase(Settings.sbNetherName) &&
                !e.getPlayer().getWorld().getName().equalsIgnoreCase(Settings.sbEndName)) {
            return;
        }

        Player p = e.getPlayer();
        if(ProfileManager.getProfile(p.getUniqueId()).getIslandIsCurrentIn() == 0) {
            ProfileManager.getProfile(p.getUniqueId()).setIslandIsCurrentIn(IslandManager.getIslandLocationIsIn(p.getLocation()).getIslandId());
        }
        IslandProfile ip = IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(p.getUniqueId()).getIslandIsCurrentIn());
        if(ip == null) return;

        if(!p.isOp() && !Perms.hasPermission(p, Perms.getPermission("modify all"), false) && !ip.isMember(p.getUniqueId())) {
            e.setCancelled(true);
        }
    }

    public static void onBlockPlace(BlockPlaceEvent e) {
        if(!e.getPlayer().getWorld().getName().equalsIgnoreCase(Settings.sbOverworldName) &&
                !e.getPlayer().getWorld().getName().equalsIgnoreCase(Settings.sbNetherName) &&
                !e.getPlayer().getWorld().getName().equalsIgnoreCase(Settings.sbEndName)) {
            return;
        }

        Player p = e.getPlayer();
        if(ProfileManager.getProfile(p.getUniqueId()).getIslandIsCurrentIn() == 0) {
            if(IslandManager.getIslandLocationIsIn(p.getLocation()) != null) {
                ProfileManager.getProfile(p.getUniqueId()).setIslandIsCurrentIn(IslandManager.getIslandLocationIsIn(p.getLocation()).getIslandId());
            }
        }
        IslandProfile ip = IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(p.getUniqueId()).getIslandIsCurrentIn());
        if(ip == null) return;

        if(!p.isOp() && !Perms.hasPermission(p, Perms.getPermission("modify all"), false) && !ip.isMember(p.getUniqueId())) {
            e.setCancelled(true);
        }
    }

}
