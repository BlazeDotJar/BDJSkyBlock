package de.bdj.sb.gui.quest;

import de.bdj.sb.gui.management.GuiButtonManager;
import de.bdj.sb.gui.management.GuiButtonType;
import de.bdj.sb.gui.management.GuiManager;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import javax.naming.Name;
import java.util.Objects;

public class QuestGUI {
    public static final String persistentKey = "";
    public static BukkitRunnable timer;

    public static void open(Player p) {
        if(ProfileManager.getProfile(p.getUniqueId()).getIslandId() != 0) {
            Inventory inv = Bukkit.createInventory(null, 54, GuiManager.ISLAND_QUEST_GUI_TITLE);

            IslandProfile ip = IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(p.getUniqueId()).getIslandId());

            int slot = 9;
            inv.setItem(slot, GuiButtonManager.getGuiBtn(GuiButtonType.QUEST_FARM_COBBLE_BTN)); slot++;
            inv.setItem(slot, GuiButtonManager.getGuiBtn(GuiButtonType.QUEST_FARM_LOG_BTN)); slot++;
            inv.setItem(slot, GuiButtonManager.getGuiBtn(GuiButtonType.QUEST_BUILD_COBBLE_GENERATOR_BTN)); slot++;

            ItemStack backgroundItem = new ItemStack(Material.BARRIER);
            ItemMeta m = backgroundItem.getItemMeta();
            m.setCustomModelData(1);
            m.setHideTooltip(true);
            backgroundItem.setItemMeta(m);
            inv.setItem(0, backgroundItem);


            inv.setItem(8, GuiButtonManager.getGuiBtn(GuiButtonType.BACK_TO_DASHBOARD));

            p.openInventory(inv);
        } else {
            Chat.error(p, "Du hast keine Insel. Also können dir auch keine Properties deiner Insel angezeigt werden.");
        }
    }

}
