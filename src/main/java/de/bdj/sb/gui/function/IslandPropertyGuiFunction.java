package de.bdj.sb.gui.function;

import de.bdj.sb.SB;
import de.bdj.sb.gui.PropertiesGUI;
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

public class IslandPropertyGuiFunction {

    public static boolean clicked(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        ItemMeta meta = item.getItemMeta();
        IslandProfile ip = IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(e.getWhoClicked().getUniqueId()).getIslandId());
        if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "sb_prop"))) {
            String value = meta.getPersistentDataContainer().get(new NamespacedKey(SB.getInstance(), "sb_prop"), PersistentDataType.STRING);
            /* BLOCK #1006 */
            // "Register new property"
            // Add a new if block corresponding to the new property you have created
            // BLOCK #1006 END
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
                } else if(split[0].equalsIgnoreCase("mob killing")) {
                    if(split[1].equalsIgnoreCase("true")) {
                        Chat.debug(split[0] + " wurde eingeschaltet");
                        ip.setProperty(split[0], "true");
                    }
                    else if(split[1].equalsIgnoreCase("false")) {
                        Chat.debug(split[0] + " wurde ausgeschaltet");
                        ip.setProperty(split[0], "false");
                    }
                }
                PropertiesGUI.open((Player)e.getWhoClicked());
            }
            return true;
        }
        return false;
    }

}
