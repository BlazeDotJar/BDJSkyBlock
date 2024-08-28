package de.bdj.sb.event.gui;

import de.bdj.sb.SB;
import de.bdj.sb.gui.function.DashboardFunction;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiClickListener {

    public static void onInventoryClick(InventoryClickEvent e) {
        NamespacedKey key = new NamespacedKey(SB.getInstance(), "sb_guibtn");
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getItemMeta() == null) return;
        if(e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(key)) {
            e.setCancelled(true);

            DashboardFunction.clickedDashboard(e);
        }
    }

}
