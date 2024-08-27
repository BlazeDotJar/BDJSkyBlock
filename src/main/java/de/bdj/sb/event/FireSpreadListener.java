package de.bdj.sb.event;

import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.utlility.Chat;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockSpreadEvent;

public class FireSpreadListener {

    public static void onFireSpread(BlockSpreadEvent e) {
        IslandProfile ip = IslandManager.getIslandLocationIsIn(e.getBlock().getLocation());
        if(ip == null) {
            return;
        }
        if(e.getSource().getType() == Material.FIRE) {
            if(ip.getProperties().get("spread fire").equalsIgnoreCase("false")) {
                e.setCancelled(true);
                return;
            }
        }
    }

    public static void onBurn(BlockBurnEvent e) {
        IslandProfile ip = IslandManager.getIslandLocationIsIn(e.getBlock().getLocation());
        if(ip == null) {
            return;
        }
        if(ip.getProperties().get("spread fire").equalsIgnoreCase("false")) {
            e.setCancelled(true);
            return;
        }
    }

    public static void onIgnite(BlockIgniteEvent e) {
        IslandProfile ip = IslandManager.getIslandLocationIsIn(e.getBlock().getLocation());
        if(ip == null) {
            return;
        }
        if(e.getCause() != BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL && ip.getProperties().get("spread fire").equalsIgnoreCase("false")) {
            e.setCancelled(true);
            return;
        }
    }

}
