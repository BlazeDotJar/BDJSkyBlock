package de.bdj.sb.event;

import de.bdj.sb.Settings;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.Chat;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Cauldron;
import org.bukkit.material.FlowerPot;

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

        if(!p.isOp() &&
                (!ip.isMember(p.getUniqueId()) ||
                        !ip.getMemberProfile(p.getUniqueId().toString()).isAllowedModify())) {
            e.setCancelled(true);
            Chat.error(p, "Du hast kein Recht auf dieser Insel zu bauen!");
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

        if(!p.isOp() &&
                (!ip.isMember(p.getUniqueId()) ||
                !ip.getMemberProfile(p.getUniqueId().toString()).isAllowedModify())) {
            e.setCancelled(true);
            Chat.error(p, "Du hast kein Recht auf dieser Insel zu bauen!");
        }
    }

    public static void onBlockClick(PlayerInteractEvent e) {
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
        if(e.getAction() == Action.PHYSICAL) {
            if(e.getClickedBlock().getType().name().toLowerCase().contains("pressure_plate")) {
                if(!p.isOp() &&
                        (!ip.isMember(p.getUniqueId()) ||
                                !ip.getMemberProfile(p.getUniqueId().toString()).isAllowedInteractRedstoneBlocks())) {
                    e.setCancelled(true);
                    Chat.error(p, "Du hast kein Recht auf dieser Insel mit " + e.getClickedBlock().getType().name() + " zu interagieren!");
                }
            }
        } else if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if((e.getClickedBlock() instanceof Chest) ||
                    (e.getClickedBlock() instanceof Furnace) ||
                    (e.getClickedBlock() instanceof Smoker) ||
                    (e.getClickedBlock() instanceof Campfire) ||
                    (e.getClickedBlock() instanceof DecoratedPot) ||
                    (e.getClickedBlock() instanceof FlowerPot) ||
                    (e.getClickedBlock() instanceof ChiseledBookshelf) ||
                    (e.getClickedBlock() instanceof Jukebox) ||
                    (e.getClickedBlock() instanceof Dispenser) ||
                    (e.getClickedBlock() instanceof Dropper) ||
                    (e.getClickedBlock() instanceof Crafter) ||
                    (e.getClickedBlock() instanceof Hopper) ||
                    (e.getClickedBlock() instanceof ShulkerBox) ||
                    (e.getClickedBlock() instanceof Barrel) ||
                    (e.getClickedBlock() instanceof Cauldron)) {
                if(!p.isOp() &&
                        (!ip.isMember(p.getUniqueId()) ||
                                !ip.getMemberProfile(p.getUniqueId().toString()).isAllowedOpenContainerBlocks())) {
                    e.setCancelled(true);
                    Chat.error(p, "Du hast kein Recht auf dieser Insel mit " + e.getClickedBlock().getType().name() + " zu interagieren!");
                }
            } else if(e.getClickedBlock().getType() == Material.REDSTONE_WIRE ||
                    e.getClickedBlock().getType() == Material.LEVER ||
                    e.getClickedBlock().getType().name().toLowerCase().contains("button") ||
                    e.getClickedBlock().getType().name().toLowerCase().contains("pressure_plate") ||
                    e.getClickedBlock().getType() == Material.DAYLIGHT_DETECTOR ||
                    e.getClickedBlock().getType() == Material.NOTE_BLOCK ||
                    e.getClickedBlock().getType() == Material.REPEATER ||
                    e.getClickedBlock().getType() == Material.COMPARATOR ||
                    e.getClickedBlock().getType() == Material.COMMAND_BLOCK ||
                    e.getClickedBlock().getType() == Material.CHAIN_COMMAND_BLOCK ||
                    e.getClickedBlock().getType() == Material.REPEATING_COMMAND_BLOCK ||
                    e.getClickedBlock().getType() == Material.COMMAND_BLOCK_MINECART) {
                if(!p.isOp() &&
                        (!ip.isMember(p.getUniqueId()) ||
                                !ip.getMemberProfile(p.getUniqueId().toString()).isAllowedInteractRedstoneBlocks())) {
                    e.setCancelled(true);
                    Chat.error(p, "Du hast kein Recht auf dieser Insel mit " + e.getClickedBlock().getType().name() + " zu interagieren!");
                }
            } else if(e.getClickedBlock().getType() == Material.BEACON ||
                    e.getClickedBlock().getType() == Material.ANVIL ||
                    e.getClickedBlock().getType() == Material.CHIPPED_ANVIL ||
                    e.getClickedBlock().getType() == Material.DAMAGED_ANVIL ||
                    e.getClickedBlock().getType().name().toLowerCase().contains("sign") ||
                    e.getClickedBlock().getType().name().toLowerCase().contains("_bed") ||
                    e.getClickedBlock().getType() == Material.END_PORTAL_FRAME ||
                    e.getClickedBlock().getType() == Material.DRAGON_EGG ||
                    e.getClickedBlock().getType() == Material.RESPAWN_ANCHOR) {
                if(!p.isOp() &&
                        (!ip.isMember(p.getUniqueId()) ||
                                !ip.getMemberProfile(p.getUniqueId().toString()).isAllowedInteractBlocks())) {
                    e.setCancelled(true);
                    Chat.error(p, "Du hast kein Recht auf dieser Insel mit " + e.getClickedBlock().getType().name() + " zu interagieren!");
                }
            }
        }
    }

}
