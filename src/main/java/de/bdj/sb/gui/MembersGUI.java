package de.bdj.sb.gui;

import de.bdj.sb.gui.management.GuiButtonManager;
import de.bdj.sb.gui.management.GuiButtonType;
import de.bdj.sb.gui.management.GuiManager;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.utlility.Chat;
import de.bdj.sb.utlility.ItemEditor;
import de.bdj.sb.utlility.PlayerAtlas;
import de.bdj.sb.utlility.XColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class MembersGUI {

    public static void open(Player p, int islandid) {
        IslandProfile ip = IslandManager.getLoadedIslandProfile(islandid);
        if(ip.getOwnerUuid() == null) {
            DashboardGUI.open(p);
            return;
        }
        boolean isOwner = (ip.getOwnerUuid() == null ? false : ip.getOwnerUuid().equals(p.getUniqueId()));
        ItemStack back = ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.BACK_TO_DASHBOARD), "sb_navigation", "dashboard");
        back = ItemEditor.rename(back, "§cZurück").clone();
        Inventory inv = Bukkit.createInventory(null, 27, GuiManager.MEMBERS_GUI_TITLE);
        inv.setItem(8, back);
        ArrayList<String> lore = new ArrayList<>();

        ItemStack item = new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
        ItemMeta meta = item.getItemMeta();

        item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        meta = item.getItemMeta();
        meta.setHideTooltip(true);
        item.setItemMeta(meta);
        item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
        item = ItemEditor.addPersistentData(item, "sb_member", "");
        for(int i = 0; i != 8; i++) inv.setItem(i, item);
        lore.clear();

        item = new ItemStack(Material.ENDER_PEARL);
        meta = item.getItemMeta();
        meta.setDisplayName(XColor.green + "Aktualisieren");
        meta.setLore(lore);
        item.setItemMeta(meta);
        item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
        item = ItemEditor.addPersistentData(item, "sb_member", "refresh members");
        inv.setItem(0, item);
        lore.clear();

        if(isOwner || ip.getMemberProfile(p.getUniqueId().toString()).isAllowedMemberInvitation()) {
            item = new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
            meta = item.getItemMeta();
            meta.setDisplayName(XColor.green + "Member hinzufügen");
            lore.add("§7Füge einen Spieler als Member hinzu.");
            lore.add("");
            lore.add("§3Klicke, um Spielerliste zu öffnen");
            meta.setLore(lore);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
            meta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
            item.setItemMeta(meta);
            item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
            item = ItemEditor.addPersistentData(item, "sb_member", "open finder");
            inv.setItem(1, item);
            lore.clear();
        }

        if(!ip.getMembers().isEmpty()) {
            int i = 9;
            lore.add("§7Klicke, um das Memberprofil zu öffnen.");
            for(String mUuid : ip.getMembers()) {
                ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
                Player member = Bukkit.getPlayer(mUuid);
                if(member != null) {
                    skullmeta.setDisplayName(member.getName());
                } else {
                    skullmeta.setDisplayName(XColor.green + PlayerAtlas.getName(mUuid));
                }

                skullmeta.setLore(lore);
                skull.setItemMeta(skullmeta);

                skull = ItemEditor.addPersistentData(skull, "sb_guibtn", "");
                if(isOwner) skull = ItemEditor.addPersistentData(skull, "sb_member", "open member profile");
                skull = ItemEditor.addPersistentData(skull, "sb_target_member", mUuid);

                skull = ItemEditor.setSkullOwner(skull, mUuid);


                inv.setItem(i, skull);
                i++;
            }
        } else {
            item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            meta = item.getItemMeta();
            meta.setDisplayName("§eKeine Member vorhanden");
            item.setItemMeta(meta);
            for(int i = 9; i != 27; i++) {
                inv.setItem(i, item);
            }
        }

        p.openInventory(inv);
    }

}
