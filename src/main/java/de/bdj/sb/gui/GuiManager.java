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

    public static void openIslandDashboard(Player p) {
        ItemStack close = ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.Back_to_Dashboard), "sb_navigation", "close");
        close = ItemEditor.rename(close, "§cSchließen").clone();
        if(ProfileManager.getProfile(p.getUniqueId()).getIslandId() == 0) {
            Inventory inv = Bukkit.createInventory(null, 27, "Insel erstellen");

            inv.setItem(8, close);
            inv.setItem(12, GuiButtonManager.getGuiBtn(GuiButtonType.CreateClassicSkyBlock));
            inv.setItem(14, GuiButtonManager.getGuiBtn(GuiButtonType.CreateOneBlockSkyBlock));

            p.openInventory(inv);
        } else {
            Inventory inv = Bukkit.createInventory(null, 27, ISLAND_DASHBOARD_TITLE);

            inv.setItem(8, close);
            inv.setItem(11, GuiButtonManager.getGuiBtn(GuiButtonType.IslandAchievements));
            inv.setItem(13, GuiButtonManager.getGuiBtn(GuiButtonType.IslandTeleport));
            inv.setItem(15, GuiButtonManager.getGuiBtn(GuiButtonType.IslandProperties));
            inv.setItem(26, GuiButtonManager.getGuiBtn(GuiButtonType.Kill_Monster));

            p.openInventory(inv);
        }
    }

    public static void openPropertiesMenu(Player p) {
        if(ProfileManager.getProfile(p.getUniqueId()).getIslandId() != 0) {
            Inventory inv = Bukkit.createInventory(null, 27, ISLAND_PROPERTIES_TITLE);

            IslandProfile ip = IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(p.getUniqueId()).getIslandId());

            inv.setItem(0, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PropBTN_ALLON), "sb_prop", "all:true"));
            inv.setItem(1, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PropBTN_ALLOFF), "sb_prop", "all:false"));
            inv.setItem(9, GuiButtonManager.getGuiBtn(GuiButtonType.PropPVP));
            inv.setItem(10, GuiButtonManager.getGuiBtn(GuiButtonType.PropMobGriefing));
            inv.setItem(11, GuiButtonManager.getGuiBtn(GuiButtonType.PropExplosionDamage));
            inv.setItem(12, GuiButtonManager.getGuiBtn(GuiButtonType.PropTntDamage));
            inv.setItem(13, GuiButtonManager.getGuiBtn(GuiButtonType.PropSpreadFire));
            inv.setItem(14, GuiButtonManager.getGuiBtn(GuiButtonType.PropNaturalMonsterSpawn));
            inv.setItem(8, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.Back_to_Dashboard), "sb_navigation", "dashboard"));

            HashMap<String, String> props = ip.getProperties();
            for(String prop : props.keySet()) {
                // Setting the value of the props under the prop items
                String val = props.get(prop);
                if(prop.equalsIgnoreCase("pvp")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(18, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_ON), "sb_prop", "pvp:false"));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(18, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_OFF), "sb_prop", "pvp:true"));
                } else if(prop.equalsIgnoreCase("mob griefing")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(19, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_ON), "sb_prop", "mob griefing:false"));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(19, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_OFF), "sb_prop", "mob griefing:true"));
                } else if(prop.equalsIgnoreCase("explosion damage")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(20, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_ON), "sb_prop", "explosion damage:false"));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(20, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_OFF), "sb_prop", "explosion damage:true"));
                } else if(prop.equalsIgnoreCase("tnt damage")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(21, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_ON), "sb_prop", "tnt damage:false"));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(21, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_OFF), "sb_prop", "tnt damage:true"));
                } else if(prop.equalsIgnoreCase("spread fire")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(22, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_ON), "sb_prop", "spread fire:false"));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(22, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_OFF), "sb_prop", "spread fire:true"));
                } else if(prop.equalsIgnoreCase("natural monster spawn")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(23, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_ON), "sb_prop", "natural monster spawn:false"));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(23, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_OFF), "sb_prop", "natural monster spawn:true"));
                }
            }

            p.openInventory(inv);
        } else {
            Chat.error(p, "Du hast keine Insel. Also können dir auch keine Properties deiner Insel angezeigt werden.");
        }
    }

}
