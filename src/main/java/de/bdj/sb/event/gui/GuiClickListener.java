package de.bdj.sb.event.gui;

import de.bdj.sb.SB;
import de.bdj.sb.gui.GuiManager;
import de.bdj.sb.gui.function.DashboardFunction;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiClickListener {

    public static void onInventoryClick(InventoryClickEvent e) {
        NamespacedKey key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        // Block #001 "Checking if inv is a registered GUI"
        if(e.getView().getTitle().equalsIgnoreCase(GuiManager.ISLAND_DASHBOARD_TITLE) ||
                e.getView().getTitle().equalsIgnoreCase(GuiManager.ISLAND_PROPERTIES_TITLE) ||
                e.getView().getTitle().equalsIgnoreCase(GuiManager.DEV_TOOLS_TITLE) ||
                e.getView().getTitle().equalsIgnoreCase(GuiManager.MEMBERS_GUI_TITLE) ||
                e.getView().getTitle().equalsIgnoreCase(GuiManager.MEMBER_ADMINISTRATION_GUI_TITLE)) {
            e.setCancelled(true);
        }
        // Block END
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getItemMeta() == null) return;
        if(e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(key)) {
            DashboardFunction.clickedDashboard(e);
        }
    }

}
