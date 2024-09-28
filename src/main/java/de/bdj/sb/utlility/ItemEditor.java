package de.bdj.sb.utlility;

import de.bdj.sb.SB;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

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

    public static ItemStack setSkullOwner(ItemStack item, String ownerUuid) {
        if(item.getType() != Material.PLAYER_HEAD && item.getType() != Material.PLAYER_WALL_HEAD) return item;

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        /*
        if(Bukkit.getPlayer(UUID.fromString(ownerUuid)).isOnline()) {

        }
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(ownerUuid));
         */
        Player p = Bukkit.getPlayer(UUID.fromString(ownerUuid));
        if (p == null || !p.isOnline()) {
            OfflinePlayer off = Bukkit.getOfflinePlayer(UUID.fromString(ownerUuid));
            meta.setOwnerProfile(Bukkit.getOfflinePlayer(UUID.fromString(ownerUuid)).getPlayerProfile());
            meta.setOwningPlayer(off);
        } else meta.setOwnerProfile(p.getPlayerProfile());

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack addAllHideItemFlags(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        for(ItemFlag f : ItemFlag.values()) meta.addItemFlags(f);
        item.setItemMeta(meta);
        return item;
    }

}
