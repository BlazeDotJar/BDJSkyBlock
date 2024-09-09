package de.bdj.sb.gui;

import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.Chat;
import de.bdj.sb.utlility.ItemEditor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class GuiManager {

    public static String ISLAND_PROPERTIES_TITLE = "Insel Einstellungen";
    public static String ISLAND_DASHBOARD_TITLE = "D A S H B O A R D";
    public static String DEV_TOOLS_TITLE = "D E V T O O L S";

    public static void openIslandDashboard(Player p) {
        ItemStack close = ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.BACK_TO_DASHBOARD), "sb_navigation", "close");
        close = ItemEditor.rename(close, "§cSchließen").clone();
        if(ProfileManager.getProfile(p.getUniqueId()).getIslandId() == 0) {
            Inventory inv = Bukkit.createInventory(null, 27, "Insel erstellen");

            inv.setItem(8, close);
            inv.setItem(12, GuiButtonManager.getGuiBtn(GuiButtonType.CREATE_CLASSIC_SKYBLOCK));
            inv.setItem(14, GuiButtonManager.getGuiBtn(GuiButtonType.CREATE_ONE_BLOCK_SKYBLOCK));

            p.openInventory(inv);
        } else {
            Inventory inv = Bukkit.createInventory(null, 27, ISLAND_DASHBOARD_TITLE);

            inv.setItem(8, close);
            inv.setItem(11, GuiButtonManager.getGuiBtn(GuiButtonType.ISLAND_ACHIEVEMENTS));
            inv.setItem(13, GuiButtonManager.getGuiBtn(GuiButtonType.ISLAND_TELEPORT));
            inv.setItem(15, GuiButtonManager.getGuiBtn(GuiButtonType.ISLAND_PROPERTIES));
            inv.setItem(18, GuiButtonManager.getGuiBtn(GuiButtonType.SET_ISLAND_SPAWN));
            inv.setItem(19, GuiButtonManager.getGuiBtn(GuiButtonType.RELOAD_DATA));
            inv.setItem(26, GuiButtonManager.getGuiBtn(GuiButtonType.KILL_MONSTERS));

            p.openInventory(inv);
        }
    }

    public static void openDeveloperGui(Player p) {
        ItemStack close = ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.BACK_TO_DASHBOARD), "sb_navigation", "close");
        close = ItemEditor.rename(close, "§cSchließen").clone();
        Inventory inv = Bukkit.createInventory(null, 27, DEV_TOOLS_TITLE);

        inv.setItem(8, close);
        inv.setItem(0, GuiButtonManager.getGuiBtn(GuiButtonType.DEV_TOOL_BIOME_CHANGE));
        inv.setItem(1, GuiButtonManager.getGuiBtn(GuiButtonType.DEV_BUILD_COL));
        inv.setItem(2, GuiButtonManager.getGuiBtn(GuiButtonType.DEV_BUILD_COL_2));

        p.openInventory(inv);
    }

    public static void openPropertiesMenu(Player p) {
        if(ProfileManager.getProfile(p.getUniqueId()).getIslandId() != 0) {
            Inventory inv = Bukkit.createInventory(null, 27, ISLAND_PROPERTIES_TITLE);

            IslandProfile ip = IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(p.getUniqueId()).getIslandId());

            inv.setItem(0, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_BTN_ALLON), "sb_prop", "all:true"));
            inv.setItem(1, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_BTN_ALLOFF), "sb_prop", "all:false"));
            inv.setItem(9, GuiButtonManager.getGuiBtn(GuiButtonType.PROP_PVP));
            inv.setItem(10, GuiButtonManager.getGuiBtn(GuiButtonType.PROP_MOB_GRIEFING));
            inv.setItem(11, GuiButtonManager.getGuiBtn(GuiButtonType.PROP_EXPLOSION_DAMAGE));
            inv.setItem(12, GuiButtonManager.getGuiBtn(GuiButtonType.PROP_TNT_DAMAGE));
            inv.setItem(13, GuiButtonManager.getGuiBtn(GuiButtonType.PROP_SPREAD_FIRE));
            inv.setItem(14, GuiButtonManager.getGuiBtn(GuiButtonType.PROP_NATURAL_MONSTER_SPAWN));
            inv.setItem(8, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.BACK_TO_DASHBOARD), "sb_navigation", "dashboard"));

            HashMap<String, String> props = ip.getProperties();
            for(String prop : props.keySet()) {
                // Setting the value of the props under the prop items
                String val = props.get(prop);
                if(prop.equalsIgnoreCase("pvp")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(18, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_ON), "sb_prop", "pvp:false"));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(18, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_OFF), "sb_prop", "pvp:true"));
                } else if(prop.equalsIgnoreCase("mob griefing")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(19, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_ON), "sb_prop", "mob griefing:false"));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(19, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_OFF), "sb_prop", "mob griefing:true"));
                } else if(prop.equalsIgnoreCase("explosion damage")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(20, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_ON), "sb_prop", "explosion damage:false"));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(20, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_OFF), "sb_prop", "explosion damage:true"));
                } else if(prop.equalsIgnoreCase("tnt damage")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(21, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_ON), "sb_prop", "tnt damage:false"));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(21, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_OFF), "sb_prop", "tnt damage:true"));
                } else if(prop.equalsIgnoreCase("spread fire")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(22, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_ON), "sb_prop", "spread fire:false"));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(22, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_OFF), "sb_prop", "spread fire:true"));
                } else if(prop.equalsIgnoreCase("natural monster spawn")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(23, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_ON), "sb_prop", "natural monster spawn:false"));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(23, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_OFF), "sb_prop", "natural monster spawn:true"));
                }
            }

            p.openInventory(inv);
        } else {
            Chat.error(p, "Du hast keine Insel. Also können dir auch keine Properties deiner Insel angezeigt werden.");
        }
    }

}
