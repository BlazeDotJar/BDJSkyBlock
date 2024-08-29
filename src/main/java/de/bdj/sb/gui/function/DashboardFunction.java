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
        IslandProfile ip = IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(e.getWhoClicked().getUniqueId()).getIslandId());
        if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "guibtn_create_classic_skyblock"))) {
            //Create skyblock island
            SkyBlockFunction.createIsland((Player)e.getWhoClicked());
        } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "guibtn_create_one_block_skyblock"))) {
            //Create one block island
            Chat.error(e.getWhoClicked(), "Dieses Gameplay wird noch nicht supported :(");
        } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "guibtn_teleport_skyblock"))) {
            ip.teleport(e.getWhoClicked());
            e.getWhoClicked().sendMessage("Du wirst teleportiert..");
        } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "guibtn_island_achievements_skyblock"))) {
            Chat.info(e.getWhoClicked(), "In den Achievements gibt es noch nichts zu sehen.");
        } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "guibtn_island_properties_skyblock"))) {
            GuiManager.openPropertiesMenu((Player) e.getWhoClicked());
        } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "guibtn_island_kill_monsters_skyblock"))) {
            int killed = ip.killHostileMobs();
            long startedAt = System.currentTimeMillis();
            Chat.info(e.getWhoClicked(), "Du hast deine Insel von " + killed + " Monstern befreit.");
            long now = System.currentTimeMillis();
            Chat.debug("Monster killing dauerte " + ((now - startedAt)) + " Millisekunden");
        }  else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "sb_navigation"))) {
            String value = meta.getPersistentDataContainer().get(new NamespacedKey(SB.getInstance(), "sb_navigation"), PersistentDataType.STRING);
            if(value.equalsIgnoreCase("dashboard")) GuiManager.openIslandDashboard((Player) e.getWhoClicked());
            else if(value.equalsIgnoreCase("close")) e.getWhoClicked().closeInventory();
            else if(e.getWhoClicked().isOp()) Chat.error(e.getWhoClicked(), "Dieses Item hat keine Funktion. Melde das bitte BlazeDotJar!");
        } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "sb_prop"))) {
            String value = meta.getPersistentDataContainer().get(new NamespacedKey(SB.getInstance(), "sb_prop"), PersistentDataType.STRING);
            if(value.contains(":")) {
                String[] split = value.split(":");
                if(split[0].equalsIgnoreCase("all")) {
                    if(split[1].equalsIgnoreCase("true")) {
                        ip.setPropertyAllTo("true");
                        Chat.debug("Alle Props auf true gesetzt");
                    } else if(split[1].equalsIgnoreCase("false")) {
                        ip.setPropertyAllTo("false");
                        Chat.debug("Alle Props auf false gesetzt");
                    }
                } else if(split[0].equalsIgnoreCase("pvp")) {
                    if(split[1].equalsIgnoreCase("true")) {
                        Chat.debug(split[0] + " wurde eingeschaltet");
                        ip.setProperty(split[0], "true");
                    } else if(split[1].equalsIgnoreCase("false")) {
                        Chat.debug(split[0] + " wurde ausgeschaltet");
                        ip.setProperty(split[0], "false");
                    }
                } else if(split[0].equalsIgnoreCase("mob griefing")) {
                    if(split[1].equalsIgnoreCase("true")) {
                        Chat.debug(split[0] + " wurde eingeschaltet");
                        ip.setProperty(split[0], "true");
                    }
                    else if(split[1].equalsIgnoreCase("false")) {
                        Chat.debug(split[0] + " wurde ausgeschaltet");
                        ip.setProperty(split[0], "false");
                    }
                } else if(split[0].equalsIgnoreCase("explosion damage")) {
                    if(split[1].equalsIgnoreCase("true")) {
                        Chat.debug(split[0] + " wurde eingeschaltet");
                        ip.setProperty(split[0], "true");
                    }
                    else if(split[1].equalsIgnoreCase("false")) {
                        Chat.debug(split[0] + " wurde ausgeschaltet");
                        ip.setProperty(split[0], "false");
                    }
                } else if(split[0].equalsIgnoreCase("tnt damage")) {
                    if(split[1].equalsIgnoreCase("true")) {
                        Chat.debug(split[0] + " wurde eingeschaltet");
                        ip.setProperty(split[0], "true");
                    }
                    else if(split[1].equalsIgnoreCase("false")) {
                        Chat.debug(split[0] + " wurde ausgeschaltet");
                        ip.setProperty(split[0], "false");
                    }
                } else if(split[0].equalsIgnoreCase("spread fire")) {
                    if(split[1].equalsIgnoreCase("true")) {
                        Chat.debug(split[0] + " wurde eingeschaltet");
                        ip.setProperty(split[0], "true");
                    }
                    else if(split[1].equalsIgnoreCase("false")) {
                        Chat.debug(split[0] + " wurde ausgeschaltet");
                        ip.setProperty(split[0], "false");
                    }
                } else if(split[0].equalsIgnoreCase("natural monster spawn")) {
                    if(split[1].equalsIgnoreCase("true")) {
                        Chat.debug(split[0] + " wurde eingeschaltet");
                        ip.setProperty(split[0], "true");
                    }
                    else if(split[1].equalsIgnoreCase("false")) {
                        Chat.debug(split[0] + " wurde ausgeschaltet");
                        ip.setProperty(split[0], "false");
                    }
                }
                GuiManager.openPropertiesMenu((Player)e.getWhoClicked());
            }
        }
    }

}
