package de.bdj.sb.gui;

import de.bdj.sb.gui.management.GuiButtonManager;
import de.bdj.sb.gui.management.GuiButtonType;
import de.bdj.sb.gui.management.GuiManager;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.Chat;
import de.bdj.sb.utlility.ItemEditor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class PropertiesGUI {

    public static void open(Player p) {
        if(ProfileManager.getProfile(p.getUniqueId()).getIslandId() != 0) {
            Inventory inv = Bukkit.createInventory(null, 27, GuiManager.ISLAND_PROPERTIES_TITLE);

            IslandProfile ip = IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(p.getUniqueId()).getIslandId());

            inv.setItem(0, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_BTN_ALLON), "sb_prop", "all:true"));
            inv.setItem(1, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_BTN_ALLOFF), "sb_prop", "all:false"));
            inv.setItem(9, GuiButtonManager.getGuiBtn(GuiButtonType.PROP_PVP));
            inv.setItem(10, GuiButtonManager.getGuiBtn(GuiButtonType.PROP_MOB_GRIEFING));
            inv.setItem(11, GuiButtonManager.getGuiBtn(GuiButtonType.PROP_EXPLOSION_DAMAGE));
            inv.setItem(12, GuiButtonManager.getGuiBtn(GuiButtonType.PROP_TNT_DAMAGE));
            inv.setItem(13, GuiButtonManager.getGuiBtn(GuiButtonType.PROP_SPREAD_FIRE));
            inv.setItem(14, GuiButtonManager.getGuiBtn(GuiButtonType.PROP_NATURAL_MONSTER_SPAWN));
            inv.setItem(15, GuiButtonManager.getGuiBtn(GuiButtonType.PROP_MOB_KILLING));
            inv.setItem(8, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.BACK_TO_DASHBOARD), "sb_navigation", "dashboard"));

            HashMap<String, String> props = ip.getProperties();
            for(String prop : props.keySet()) {
                // Setting the value of the props under the prop items
                String val = props.get(prop);
                /* BLOCK #1002 */
                // "Register new property"
                // Add a new if block corresponding to the new property you have created
                // Define the value of the persistant data: ------------------------------------------------------------------------------------------------------------ VVV  :  VVVV
                // if(val.equalsIgnoreCase("true")) inv.setItem(18, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_ON), "sb_prop", "propName:booleanValue"));
                // Change the slot of the item ---------------- V
                // if(val.equalsIgnoreCase("true")) inv.setItem(18, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_ON), "sb_prop", "propName:booleanValue"));
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
                } else if(prop.equalsIgnoreCase("mob killing")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(24, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_ON), "sb_prop", "mob killing:false"));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(24, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PROP_STATE_OFF), "sb_prop", "mob killing:true"));
                }
                // Block #1001 END
            }

            p.openInventory(inv);
        } else {
            Chat.error(p, "Du hast keine Insel. Also können dir auch keine Properties deiner Insel angezeigt werden.");
        }
    }

}
