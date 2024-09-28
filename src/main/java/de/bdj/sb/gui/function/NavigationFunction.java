package de.bdj.sb.gui.function;

import de.bdj.sb.SB;
import de.bdj.sb.gui.DashboardGUI;
import de.bdj.sb.gui.MembersGUI;
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

public class NavigationFunction {

    public static boolean clicked(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        ItemMeta meta = item.getItemMeta();
        IslandProfile ip = IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(e.getWhoClicked().getUniqueId()).getIslandId());
       if(meta.getPersistentDataContainer().has(new NamespacedKey(SB.getInstance(), "sb_navigation"))) {
            String value = meta.getPersistentDataContainer().get(new NamespacedKey(SB.getInstance(), "sb_navigation"), PersistentDataType.STRING);
            if(value.equalsIgnoreCase("dashboard")) DashboardGUI.open((Player) e.getWhoClicked());
            else if(value.equalsIgnoreCase("close")) e.getWhoClicked().closeInventory();
            else if(value.equalsIgnoreCase("members")) MembersGUI.open((Player) e.getWhoClicked(), ip.getIslandId());
            else if(e.getWhoClicked().isOp()) Chat.error(e.getWhoClicked(), "Dieses Item hat keine Funktion. Melde das bitte BlazeDotJar!");
            return true;
        }
        return false;
    }

}
