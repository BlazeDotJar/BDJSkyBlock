package de.bdj.sb.gui;

import de.bdj.sb.gui.management.GuiButtonManager;
import de.bdj.sb.gui.management.GuiButtonType;
import de.bdj.sb.gui.management.GuiManager;
import de.bdj.sb.utlility.ItemEditor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DeveloperGUI {

    public static void open(Player p) {
        ItemStack close = ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.BACK_TO_DASHBOARD), "sb_navigation", "close");
        close = ItemEditor.rename(close, "§cSchließen").clone();
        Inventory inv = Bukkit.createInventory(null, 27, GuiManager.DEV_TOOLS_TITLE);

        inv.setItem(8, close);
        inv.setItem(0, GuiButtonManager.getGuiBtn(GuiButtonType.DEV_TOOL_BIOME_CHANGE));
        inv.setItem(1, GuiButtonManager.getGuiBtn(GuiButtonType.DEV_BUILD_COL));
        inv.setItem(2, GuiButtonManager.getGuiBtn(GuiButtonType.DEV_BUILD_COL_2));

        p.openInventory(inv);
    }

}
