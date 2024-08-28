package de.bdj.sb.gui;

import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.Chat;
import de.bdj.sb.utlility.ItemEditor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class GuiManager {

    public static void openIslandDashboard(Player p) {
        if(ProfileManager.getProfile(p.getUniqueId()).getIslandId() == 0) {
            Inventory inv = Bukkit.createInventory(null, 27, "Insel erstellen");

            inv.setItem(12, GuiButtonManager.getGuiBtn(GuiButtonType.CreateClassicSkyBlock));
            inv.setItem(14, GuiButtonManager.getGuiBtn(GuiButtonType.CreateOneBlockSkyBlock));

            p.openInventory(inv);
        } else {
            Inventory inv = Bukkit.createInventory(null, 27, "D A S H B O A R D");

            inv.setItem(11, GuiButtonManager.getGuiBtn(GuiButtonType.IslandAchievements));
            inv.setItem(13, GuiButtonManager.getGuiBtn(GuiButtonType.IslandTeleport));
            inv.setItem(15, GuiButtonManager.getGuiBtn(GuiButtonType.IslandProperties));

            p.openInventory(inv);
        }
    }

    public static void openPropertiesMenu(Player p) {
        if(ProfileManager.getProfile(p.getUniqueId()).getIslandId() != 0) {
            Inventory inv = Bukkit.createInventory(null, 27, "Insel Einstellungen");

            IslandProfile ip = IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(p.getUniqueId()).getIslandId());

            inv.setItem(0, GuiButtonManager.getGuiBtn(GuiButtonType.PropBTN_ALLON));
            inv.setItem(1, GuiButtonManager.getGuiBtn(GuiButtonType.PropBTN_ALLOFF));
            inv.setItem(9, GuiButtonManager.getGuiBtn(GuiButtonType.PropPVP));
            inv.setItem(10, GuiButtonManager.getGuiBtn(GuiButtonType.PropMobGriefing));
            inv.setItem(11, GuiButtonManager.getGuiBtn(GuiButtonType.PropExplosionDamage));
            inv.setItem(12, GuiButtonManager.getGuiBtn(GuiButtonType.PropTntDamage));
            inv.setItem(13, GuiButtonManager.getGuiBtn(GuiButtonType.PropSpreadFire));
            inv.setItem(14, GuiButtonManager.getGuiBtn(GuiButtonType.PropNaturalMonsterSpawn));
            inv.setItem(8, GuiButtonManager.getGuiBtn(GuiButtonType.BACK_TO_DASHBOARD));

            HashMap<String, String> props = ip.getProperties();
            for(String prop : props.keySet()) {
                // Setting the value of the props under the prop items
                String val = props.get(prop);
                if(prop.equalsIgnoreCase("pvp")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(18, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_ON), "sb_prop", "pvp:false"));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(18, ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_OFF), "sb_prop", "pvp:true"));
                } else if(prop.equalsIgnoreCase("mob griefing")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(19, GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_ON));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(19, GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_OFF));
                } else if(prop.equalsIgnoreCase("explosion damage")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(20, GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_ON));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(20, GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_OFF));
                } else if(prop.equalsIgnoreCase("tnt damage")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(21, GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_ON));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(21, GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_OFF));
                } else if(prop.equalsIgnoreCase("spread fire")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(22, GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_ON));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(22, GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_OFF));
                } else if(prop.equalsIgnoreCase("natural monster spawn")) {
                    if(val.equalsIgnoreCase("true")) inv.setItem(23, GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_ON));
                    else if(val.equalsIgnoreCase("false")) inv.setItem(23, GuiButtonManager.getGuiBtn(GuiButtonType.PropSTATE_OFF));
                }
            }

            p.openInventory(inv);
        } else {
            Chat.error(p, "Du hast keine Insel. Also können dir auch keine Properties deiner Insel angezeigt werden.");
        }
    }

}
