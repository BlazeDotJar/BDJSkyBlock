package de.bdj.sb.gui.function;

import de.bdj.sb.SB;
import de.bdj.sb.gui.DashboardGUI;
import de.bdj.sb.gui.quest.FarmCobblestoneQuestGUI;
import de.bdj.sb.gui.quest.QuestGUI;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.profile.PlayerProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.Chat;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class QuestFunction {

    public static boolean clicked(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        ItemMeta meta = item.getItemMeta();
        if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "guibtn_back_to_dashboard_skyblock"))) {
            DashboardGUI.open((Player)e.getWhoClicked());
            return true;
        } else if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "guibtn_quest_farm_cobble"))) {
            FarmCobblestoneQuestGUI.open((Player)e.getWhoClicked());
            return true;
        }
        return false;
    }

}
