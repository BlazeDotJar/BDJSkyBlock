package de.bdj.sb.gui;

import de.bdj.sb.gui.management.GuiButtonManager;
import de.bdj.sb.gui.management.GuiButtonType;
import de.bdj.sb.gui.management.GuiManager;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.Chat;
import de.bdj.sb.utlility.ItemEditor;
import de.bdj.sb.utlility.XColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.UUID;

public class MemberFinderGUI {

    public static void open(Player p, int islandid, int page) {
        boolean isOwner = IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(p.getUniqueId()).getIslandId()).getOwnerUuid().equals(p.getUniqueId());
        Inventory inv = Bukkit.createInventory(null, 54, GuiManager.MEMBER_FINDER_GUI_TITLE);
        IslandProfile ip = IslandManager.getLoadedIslandProfile(islandid);

        if(!isOwner && !ip.getMemberProfile(p.getUniqueId().toString()).isAllowedMemberInvitation()) {
            Chat.error(p, "Du hast kein recht dazu, neue Member auf die Insel einzuladen.");
            MembersGUI.open(p, islandid);
        }

        ArrayList<String> lore = new ArrayList<>();
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setHideTooltip(true);
        item.setItemMeta(meta);
        item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
        item = ItemEditor.addPersistentData(item, "sb_member", "");
        for(int i = 0; i != 54; i++) inv.setItem(i, item);
        lore.clear();

        // Back Button
        ItemStack back = ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.BACK_TO_DASHBOARD), "sb_navigation", "members");
        back = ItemEditor.rename(back, "§cZurück").clone();
        inv.setItem(8, back);

        item = new ItemStack(Material.ENDER_PEARL);
        meta = item.getItemMeta();
        meta.setDisplayName(XColor.green + "Aktualisieren");
        meta.setLore(lore);
        item.setItemMeta(meta);
        item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
        item = ItemEditor.addPersistentData(item, "sb_member", "refresh finder");
        inv.setItem(0, item);
        lore.clear();

        // Prev and Next Page Items
        item = new ItemStack(Material.ARROW);
        meta = item.getItemMeta();
        meta.setDisplayName("§fNächste Seite");
        lore.add("§3Klicke, um zur Seite " + (page + 1) + " zu springen");
        meta.setLore(lore);
        item.setItemMeta(meta);
        item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
        item = ItemEditor.addPersistentData(item, "sb_member", "finder page:" + (page + 1));
        inv.setItem(53, item);
        lore.clear();

        ArrayList<String> members = ip.getMembers();


        // Open GUI before the latency of the player skull generating is blocking the gui opening
        p.openInventory(inv);

        ArrayList<Player> players = new ArrayList<>();
        for(Player t : Bukkit.getOnlinePlayers()) {
            //for(int i = 0; i != 120; i++)
                players.add(t);
        }

        int i = 9;
        int slot = 9;
        int startAt = (page - 1) * 36;
        for(Player t : players) {
            if((i - 9) < startAt) {
                i++;
                continue;
            } else if((i - 9 ) == (startAt + 36)) {
                break;
            }
            if(t.getUniqueId().equals(p.getUniqueId())) continue;
            UUID mUuid = t.getUniqueId();
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullmeta = (SkullMeta) skull.getItemMeta();
            skullmeta.setDisplayName(XColor.green + t.getName());
            boolean allowInvitation = false;

            if(ProfileManager.getProfile(mUuid).hasIsland()) {
                if(members.contains(mUuid.toString())) lore.add("§7Dieser Spieler ist bereits Member auf deiner Insel.");
                else if(mUuid.equals(p.getUniqueId())) lore.add("§7Das bist du. (Diese Zeile ist ein Bug :) Glückwunsch!)");
                else lore.add("§cDieser Spieler hat bereits eine Insel.");
            } else {
                lore.add("§3Klicke um diesen Spieler zur Insel einzuladen.");
                allowInvitation = true;
            }
            skullmeta.setLore(lore);
            skull.setItemMeta(skullmeta);

            skull = ItemEditor.addPersistentData(skull, "sb_guibtn", "");
            if(allowInvitation) skull = ItemEditor.addPersistentData(skull, "sb_member", "invite :" + t.getName() + ":" + islandid);
            skull = ItemEditor.addPersistentData(skull, "sb_target_member_name", t.getName());

            skull = ItemEditor.setSkullOwner(skull, mUuid.toString());


            inv.setItem(slot, skull);
            lore.clear();
            i++;
            slot++;
        }

        if(page > 1) {
            item = new ItemStack(Material.ARROW);
            meta = item.getItemMeta();
            meta.setDisplayName("§fVorherige Seite");
            lore.add("§3Klicke, um zur Seite " + (page - 1) + " zu springen");
            meta.setLore(lore);
            item.setItemMeta(meta);
            item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
            item = ItemEditor.addPersistentData(item, "sb_member", "finder page:" + (page - 1));
            inv.setItem(45, item);
            lore.clear();
        }
        if((i - 9) >= players.size() || players.size() < 36) {
            inv.setItem(53, inv.getItem(52));
        }
    }

}
