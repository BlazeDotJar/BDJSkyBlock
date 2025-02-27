package de.bdj.sb.gui;

import de.bdj.sb.SB;
import de.bdj.sb.gui.management.GuiButtonManager;
import de.bdj.sb.gui.management.GuiButtonType;
import de.bdj.sb.gui.management.GuiManager;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.ItemEditor;
import de.bdj.sb.utlility.PlayerAtlas;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class DashboardGUI {

    public static void open(Player p) {
        ItemStack close = ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.BACK_TO_DASHBOARD), "sb_navigation", "close");
        close = ItemEditor.rename(close, "§cSchließen").clone();
        if(ProfileManager.getProfile(p.getUniqueId()).getIslandId() == 0) {
            Inventory inv = Bukkit.createInventory(null, 27, "Insel erstellen");

            inv.setItem(8, close);
            inv.setItem(12, GuiButtonManager.getGuiBtn(GuiButtonType.CREATE_CLASSIC_SKYBLOCK));
            inv.setItem(14, GuiButtonManager.getGuiBtn(GuiButtonType.CREATE_ONE_BLOCK_SKYBLOCK));

            p.openInventory(inv);
        } else {
            Inventory inv = Bukkit.createInventory(null, 27, GuiManager.ISLAND_DASHBOARD_TITLE);
            ItemStack playerSkull = ItemEditor.setSkullOwner(GuiButtonManager.getGuiBtn(GuiButtonType.MEMBERS_GUI), p.getUniqueId().toString());

            inv.setItem(8, close);
            inv.setItem(11, GuiButtonManager.getGuiBtn(GuiButtonType.ISLAND_QUESTS));
            inv.setItem(13, GuiButtonManager.getGuiBtn(GuiButtonType.ISLAND_TELEPORT));
            inv.setItem(15, GuiButtonManager.getGuiBtn(GuiButtonType.ISLAND_PROPERTIES));
            inv.setItem(18, GuiButtonManager.getGuiBtn(GuiButtonType.SET_ISLAND_SPAWN));
            inv.setItem(19, GuiButtonManager.getGuiBtn(GuiButtonType.RELOAD_DATA));
            inv.setItem(25, playerSkull);
            inv.setItem(26, GuiButtonManager.getGuiBtn(GuiButtonType.KILL_MONSTERS));

            ItemStack background = new ItemStack(Material.BARRIER);
            ItemMeta meta = background.getItemMeta();
            meta.setHideTooltip(true);
            meta.getPersistentDataContainer().set(new NamespacedKey(SB.getInstance(), "sb_guibtn"), PersistentDataType.STRING, "bdjskyblock");
            meta.getPersistentDataContainer().set(new NamespacedKey(SB.getInstance(), "guibtn_island_dashboard"), PersistentDataType.STRING, "bdjskyblock");
            meta.setCustomModelData(2);
            background.setItemMeta(meta);
            inv.setItem(0, background);

            p.openInventory(inv);
        }
    }
}
