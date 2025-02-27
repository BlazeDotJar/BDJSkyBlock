package de.bdj.sb.event.gui;

import de.bdj.sb.SB;
import de.bdj.sb.gui.function.*;
import de.bdj.sb.gui.management.GuiManager;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiClickListener {

    public static final String guiBtnNamespace = "sb_guibtn";

    public static void onInventoryClick(InventoryClickEvent e) {
        NamespacedKey key = new NamespacedKey(SB.getInstance(), guiBtnNamespace);
        // Block #001 "Checking if inv is a registered GUI"
        if(e.getView().getTitle().equalsIgnoreCase(GuiManager.ISLAND_DASHBOARD_TITLE) ||
                e.getView().getTitle().equalsIgnoreCase(GuiManager.ISLAND_PROPERTIES_TITLE) ||
                e.getView().getTitle().equalsIgnoreCase(GuiManager.DEV_TOOLS_TITLE) ||
                e.getView().getTitle().equalsIgnoreCase(GuiManager.MEMBERS_GUI_TITLE) ||
                e.getView().getTitle().equalsIgnoreCase(GuiManager.MEMBER_FINDER_GUI_TITLE) ||
                e.getView().getTitle().equalsIgnoreCase(GuiManager.MEMBER_ADMINISTRATION_GUI_TITLE) ||
                e.getView().getTitle().equalsIgnoreCase(GuiManager.ISLAND_QUEST_GUI_TITLE)) {
            e.setCancelled(true);
        }
        // Block END
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getItemMeta() == null) return;
        if(e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(key)) {
            boolean found = NavigationFunction.clicked(e);
            if(!found) found = DashboardFunction.clicked(e);
            if(!found) found = IslandPropertyGuiFunction.clicked(e);
            if(!found) found = MemberGuiFunction.clicked(e);
            if(!found) found = QuestFunction.clicked(e);
            if(!found) found = FarmCobblestoneQuestFunction.clicked(e);
            //if(!found) found = IslandPropertyGuiFunction.clickedPropertyGUI(e);

            // If no class found, the event gets cancelled either
            if(!found) e.setCancelled(true);
        }
    }

}
