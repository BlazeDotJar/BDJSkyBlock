package de.bdj.sb.event.gui;

import de.bdj.sb.gui.GuiManager;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.profile.ProfileManager;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener {

    public static void onInventoryClose(InventoryCloseEvent e) {
        if(e.getView().getTitle().equalsIgnoreCase(GuiManager.ISLAND_PROPERTIES_TITLE)) {
            IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(e.getPlayer().getUniqueId()).getIslandId()).saveProperties();
        }
    }

}
