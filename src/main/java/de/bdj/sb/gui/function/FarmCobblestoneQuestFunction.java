package de.bdj.sb.gui.function;

import de.bdj.sb.SB;
import de.bdj.sb.gui.DashboardGUI;
import de.bdj.sb.gui.quest.FarmCobblestoneQuestGUI;
import de.bdj.sb.gui.quest.QuestGUI;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.Chat;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FarmCobblestoneQuestFunction {

    public static boolean clicked(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        ItemMeta meta = item.getItemMeta();
        if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "guibtn_back_to_quests"))) {
            QuestGUI.open((Player)e.getWhoClicked());
            return true;

        } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "guibtn_quest_reward"))) {
            if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "milestone_reached"))) {
                NamespacedKey key = new NamespacedKey(SB.getInstance(), "milestone_reached");
                meta.getPersistentDataContainer().remove(key);
                item.setItemMeta(meta);
                boolean successfullyAdded = false;
                for(ItemStack i : e.getWhoClicked().getInventory().getStorageContents()) {
                    if(i == null) {
                        e.getWhoClicked().getInventory().addItem(item.clone());
                        successfullyAdded = true;
                        break;
                    }
                }
                if(!successfullyAdded) {
                    Chat.error(e.getWhoClicked(), "Du hast keinen Platz im Inventar");
                }
            } else {
                Chat.error(e.getWhoClicked(), "Diesen Meilenstein hast du noch nicht erreicht!");
            }
            return true;
        }
        return false;
    }

}
