package de.bdj.sb.utlility;

import de.bdj.sb.SB;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ItemEditor {

    public static ItemStack addPersistentData(ItemStack item, String key, String value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(SB.getInstance(), key), PersistentDataType.STRING, value);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack rename(ItemStack item, String newDisplayName) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(newDisplayName);
        item.setItemMeta(meta);
        return item;
    }

}
