package de.bdj.sb.utlility;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ProgressVisualizer {

    public static ItemStack getProgressVisual(int percentage) {
        ItemStack progress = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta meta = progress.getItemMeta();
        double stage = 16.0 / 100 * percentage;

        assert meta != null;
        meta.setCustomModelData((int)stage + 1);

        progress.setItemMeta(meta);

        return progress;
    }

}
