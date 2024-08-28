package de.bdj.sb.gui.function;

import de.bdj.sb.SB;
import de.bdj.sb.command.SkyBlockFunction;
import de.bdj.sb.gui.GuiManager;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.Chat;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class DashboardFunction {

    public static void clickedDashboard(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        ItemMeta meta = item.getItemMeta();
        if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "guibtn_create_classic_skyblock"))) {
            //Create skyblock island
            SkyBlockFunction.createIsland((Player)e.getWhoClicked());
        } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "guibtn_create_one_block_skyblock"))) {
            //Create one block island
            Chat.error(e.getWhoClicked(), "Dieses Gameplay wird noch nicht supported :(");
        } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "guibtn_teleport_skyblock"))) {
            IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(e.getWhoClicked().getUniqueId()).getIslandId()).teleport(e.getWhoClicked());
            e.getWhoClicked().sendMessage("Du wirst teleportiert..");
        } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "guibtn_island_achievements_skyblock"))) {
            Chat.info(e.getWhoClicked(), "In den Achievements gibt es noch nichts zu sehen.");
        } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "guibtn_island_properties_skyblock"))) {
            GuiManager.openPropertiesMenu((Player) e.getWhoClicked());
        } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "sb_prop"))) {
            String value = meta.getPersistentDataContainer().get(new NamespacedKey(SB.getInstance(), "sb_prop"), PersistentDataType.STRING);
            if(value.contains(":")) {
                IslandProfile ip = IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(e.getWhoClicked().getUniqueId()).getIslandId());
                String[] split = value.split(":");
                if(split[0].equalsIgnoreCase("pvp")) {
                    if(split[1].equalsIgnoreCase("true")) {
                        e.getWhoClicked().sendMessage("PVP wurde eingeschaltet");
                        ip.setProperty("pvp", "true");
                    }
                    else if(split[1].equalsIgnoreCase("false")) {
                        e.getWhoClicked().sendMessage("PVP wurde ausgeschaltet");
                        ip.setProperty("pvp", "false");
                    }
                }
                GuiManager.openPropertiesMenu((Player)e.getWhoClicked());
            }
        }
    }

}
