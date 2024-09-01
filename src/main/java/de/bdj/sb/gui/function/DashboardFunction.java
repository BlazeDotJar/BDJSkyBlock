package de.bdj.sb.gui.function;

import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import de.bdj.sb.command.SkyBlockFunction;
import de.bdj.sb.gui.GuiManager;
import de.bdj.sb.island.IslandDataWriter;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.island.result.SetIslandSpawnResult;
import de.bdj.sb.island.weapi.WETools;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.Chat;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class DashboardFunction {

    public static void clickedDashboard(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        ItemMeta meta = item.getItemMeta();
        IslandProfile ip = IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(e.getWhoClicked().getUniqueId()).getIslandId());
        if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "guibtn_dev_tool_change_biome"))) {
            Chat.info(e.getWhoClicked(), "Ändere Biome...");
            long startedAt = System.currentTimeMillis();
            WETools.changeBiome(ip.getArea().getPoint1(), ip.getArea().getPoint2());
            long finished = System.currentTimeMillis();
            Chat.info(e.getWhoClicked(), "Das Biom ist geändert worden. Das dauerte " + ((finished - startedAt) / 1000) + " Sekunden");
        } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "guibtn_create_classic_skyblock"))) {
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
        } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "guibtn_reload_data"))) {
            IslandManager.reloadFile(ip.getIslandId());
            Chat.info(e.getWhoClicked(), "Du hast deine Inseldaten neu geladen");
        }  else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "guibtn_set_island_spawn"))) {
            if((!e.getWhoClicked().getWorld().getName().equals(Settings.sbOverworldName) &&
                    !e.getWhoClicked().getWorld().getName().equals(Settings.sbNetherName) &&
                    !e.getWhoClicked().getWorld().getName().equals(Settings.sbEndName)) ||
            ProfileManager.getProfile(e.getWhoClicked().getUniqueId()).getIslandIsCurrentIn() != ip.getIslandId()) {
                Chat.warn(e.getWhoClicked(), "Du musst dich auf deiner Insel befinden um das tun zu können!");
                return;
            }
            Location loc = e.getWhoClicked().getLocation().clone();
            SetIslandSpawnResult r = IslandDataWriter.setIslandSpawn(ip.getIslandId(), loc);
            if(r == SetIslandSpawnResult.SUCCESS) {
                IslandManager.reloadFile(ip.getIslandId());
                Chat.info(e.getWhoClicked(), "Du hast deinen Insel Spawnpunkt neu gesetzt!");
            } else {
                Chat.warn(e.getWhoClicked(), "Etwas ist schief gelaufen. Rejoin den Server und versuche es nochmal.");
                if(r == SetIslandSpawnResult.NO_ISLAND_FOUND) {
                    Chat.sendOperatorMessage("Der Spieler " + e.getWhoClicked().getName() + " hat versucht seinen Insel Spawnpunkt zu setzen. Es konnte aber keine Insel mit der ID " + ip.getIslandId() + " gefunden werden!", "Behaltet das bitte im Auge.");
                } else if(r == SetIslandSpawnResult.COULD_NOT_SAVE) {
                    Chat.sendOperatorMessage("Der Spieler " + e.getWhoClicked().getName() + " hat versucht seinen Insel Spawnpunkt zu setzen. Die Insel Datei konnte aber nicht gespeichert werden!", "Behaltet das bitte im Auge.");
                } else if(r == SetIslandSpawnResult.FILE_DOES_NOT_EXIST) {
                    Chat.sendOperatorMessage("Der Spieler " + e.getWhoClicked().getName() + " hat versucht seinen Insel Spawnpunkt zu setzen. Die Insel Datei existiert aber nicht.", "Eventuell ist der gesamte Fortschritt von dem Spieler weg!", "Kontaktiere bitte sofort einen Developer oder die letzte Person, die an den Server Dateien gespielt hat!");
                }
            }
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
