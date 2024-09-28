package de.bdj.sb.gui;

import de.bdj.sb.gui.management.GuiButtonManager;
import de.bdj.sb.gui.management.GuiButtonType;
import de.bdj.sb.gui.management.GuiManager;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.island.MemberProfile;
import de.bdj.sb.utlility.ItemEditor;
import de.bdj.sb.utlility.PlayerAtlas;
import de.bdj.sb.utlility.XColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.UUID;

public class MemberProfileGUI {

    public static void open(Player p, int islandid, String memberUuid) {
        ItemStack back = ItemEditor.addPersistentData(GuiButtonManager.getGuiBtn(GuiButtonType.BACK_TO_DASHBOARD), "sb_navigation", "members");
        back = ItemEditor.rename(back, "§cZurück").clone();
        Inventory inv = Bukkit.createInventory(null, 54, GuiManager.MEMBER_ADMINISTRATION_GUI_TITLE);
        inv.setItem(8, back);

        IslandProfile ip = IslandManager.getLoadedIslandProfile(islandid);
        MemberProfile mp = ip.getMemberProfile(memberUuid);
        String memberName = PlayerAtlas.getName(memberUuid);

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(memberUuid)));
        skullMeta.setDisplayName("§7Du verwaltest");

        lore.add(XColor.green + memberName);
        skullMeta.setLore(lore);
        skull.setItemMeta(skullMeta);
        lore.clear();
        skull = ItemEditor.addPersistentData(skull, "sb_guibtn", "");
        inv.setItem(0, skull);

        ItemStack item = new ItemStack(Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§eEntfernen");
        lore.add("§7Entferne §f" + memberName + " §7von deiner Insel.");
        lore.add("§7Dieser Spieler kann somit nichts mehr auf dieser Insel machen,");
        lore.add("§7da dieser kein Mitspieler mehr ist.");
        lore.add("");
        lore.add("§3Klicke, um auszuführen");
        meta.setLore(lore);
        lore.clear();
        item.setItemMeta(meta);
        item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
        item = ItemEditor.addPersistentData(item, "sb_member", "remove");
        item = ItemEditor.addPersistentData(item, "sb_target_member", memberUuid);
        item = ItemEditor.addAllHideItemFlags(item);
        inv.setItem(1, item);

        item = new ItemStack(Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE);
        meta = item.getItemMeta();
        meta.setDisplayName("§eVerbannen");
        lore.add("§7Verbanne §f" + memberName + " §7von deiner Insel.");
        lore.add("§7Diesem Spieler wird automatisch, sofern er ein");
        lore.add("§7Member ist, die Memberrolle entzogen");
        lore.add("§7und anschließend verbannt. Dem Spieler ist es");
        lore.add("§7somit nicht möglich deine Insel zu betreten.");
        lore.add("");
        lore.add("§3Klicke, um auszuführen");
        meta.setLore(lore);
        lore.clear();
        item.setItemMeta(meta);
        item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
        item = ItemEditor.addPersistentData(item, "sb_member", "ban");
        item = ItemEditor.addPersistentData(item, "sb_target_member", memberUuid);
        item = ItemEditor.addAllHideItemFlags(item);
        inv.setItem(2, item);

        if(mp.isAllowedModify()) {
            item = new ItemStack(Material.ENCHANTED_BOOK);
            meta = item.getItemMeta();
            meta.setDisplayName("§eBauen/Abbauen");
            lore.add("§7Verbiete das " + XColor.green + "Bauen §7und " + XColor.green + "Abbauen §7auf der Insel.");
            lore.add("§7Aktuell " + XColor.green + "erlaubt§7.");
            lore.add("");
            lore.add("§3Klicke, um zu togglen");
            meta.setLore(lore);
            lore.clear();
            item.setItemMeta(meta);
            item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
            item = ItemEditor.addPersistentData(item, "sb_member", "");
            item = ItemEditor.addPersistentData(item, "sb_target_member", memberUuid);
            item = ItemEditor.addPersistentData(item, "sb_member_prop_modify", "deny");
            item = ItemEditor.addAllHideItemFlags(item);
            inv.setItem(18, item);
        } else {
            item = new ItemStack(Material.BOOK);
            meta = item.getItemMeta();
            meta.setDisplayName("§eBauen/Abbauen");
            lore.add("§7Erlaube das " + XColor.green + "Bauen §7und " + XColor.green + "Abbauen §7auf der Insel.");
            lore.add("§7Aktuell " + XColor.orange + "verboten§7.");
            lore.add("");
            lore.add("§3Klicke, um zu togglen");
            meta.setLore(lore);
            lore.clear();
            item.setItemMeta(meta);
            item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
            item = ItemEditor.addPersistentData(item, "sb_member", "");
            item = ItemEditor.addPersistentData(item, "sb_target_member", memberUuid);
            item = ItemEditor.addPersistentData(item, "sb_member_prop_modify", "allow");
            item = ItemEditor.addAllHideItemFlags(item);
            inv.setItem(18, item);
        }

        if(mp.isAllowedMobkilling()) {
            item = new ItemStack(Material.ENCHANTED_BOOK);
            meta = item.getItemMeta();
            meta.setDisplayName("§eTöten von Mobs");
            lore.add("§7Verbiete das " + XColor.green + "Töten von Mobs §7auf der Insel.");
            lore.add("§7Aktuell " + XColor.green + "erlaubt§7.");
            lore.add("");
            lore.add("§3Klicke, um zu togglen");
            meta.setLore(lore);
            lore.clear();
            item.setItemMeta(meta);
            item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
            item = ItemEditor.addPersistentData(item, "sb_member", "");
            item = ItemEditor.addPersistentData(item, "sb_target_member", memberUuid);
            item = ItemEditor.addPersistentData(item, "sb_member_prop_mobkilling", "deny");
            item = ItemEditor.addAllHideItemFlags(item);
            inv.setItem(19, item);
        } else {
            item = new ItemStack(Material.BOOK);
            meta = item.getItemMeta();
            meta.setDisplayName("§eTöten von Mobs");
            lore.add("§7Erlaube das " + XColor.green + "Töten von Mobs §7auf der Insel.");
            lore.add("§7Aktuell " + XColor.orange + "verboten§7.");
            lore.add("");
            lore.add("§3Klicke, um zu togglen");
            meta.setLore(lore);
            lore.clear();
            item.setItemMeta(meta);
            item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
            item = ItemEditor.addPersistentData(item, "sb_member", "");
            item = ItemEditor.addPersistentData(item, "sb_target_member", memberUuid);
            item = ItemEditor.addPersistentData(item, "sb_member_prop_mobkilling", "allow");
            item = ItemEditor.addAllHideItemFlags(item);
            inv.setItem(19, item);
        }

        // Permission: Allow Member Invitation
        if(mp.isAllowedMemberInvitation()) {
            item = new ItemStack(Material.ENCHANTED_BOOK);
            meta = item.getItemMeta();
            meta.setDisplayName("§eWeitere Member einladen");
            meta.addEnchant(Enchantment.EFFICIENCY, 1, true);
            lore.add("§7Verbiete dem Member das " + XColor.green + "Einladen weiterer Member§7.");
            lore.add("§7Aktuell " + XColor.green + "erlaubt§7.");
            lore.add("");
            lore.add("§3Klicke, um zu togglen");
            meta.setLore(lore);
            lore.clear();
            item.setItemMeta(meta);
            item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
            item = ItemEditor.addPersistentData(item, "sb_member", "");
            item = ItemEditor.addPersistentData(item, "sb_target_member", memberUuid);
            item = ItemEditor.addPersistentData(item, "sb_member_prop_member_invitation", "deny");
            item = ItemEditor.addAllHideItemFlags(item);
            inv.setItem(20, item);
        } else {
            item = new ItemStack(Material.BOOK);
            meta = item.getItemMeta();
            meta.setDisplayName("§eWeitere Member einladen");
            lore.add("§7Erlaube dem Member das " + XColor.green + "Einladen weiterer Member§7.");
            lore.add("§7Aktuell " + XColor.orange + "verboten§7.");
            lore.add("");
            lore.add("§3Klicke, um zu togglen");
            meta.setLore(lore);
            lore.clear();
            item.setItemMeta(meta);
            item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
            item = ItemEditor.addPersistentData(item, "sb_member", "");
            item = ItemEditor.addPersistentData(item, "sb_target_member", memberUuid);
            item = ItemEditor.addPersistentData(item, "sb_member_prop_member_invitation", "allow");
            item = ItemEditor.addAllHideItemFlags(item);
            inv.setItem(20, item);
        }

        // Permission: Allow Container Opening
        if(mp.isAllowedOpenContainerBlocks()) {
            item = new ItemStack(Material.ENCHANTED_BOOK);
            meta = item.getItemMeta();
            meta.setDisplayName("§eContainerblöcke öffnen");
            meta.addEnchant(Enchantment.EFFICIENCY, 1, true);
            lore.add("§7Verbiete dem Member das " + XColor.green + "Öffnen von Container Blöcken§7.");
            lore.add("§7Aktuell " + XColor.green + "erlaubt§7.");
            lore.add("");
            lore.add(XColor.orange + "Nicht geschützte Containerblöcke:");
            lore.add("§7Chest, Furnace, Smoker, Campfire, Decorated Pot,");
            lore.add("§7Flower Pot, Chiseled Bookshelf,");
            lore.add("§7Jukebox, Dispenser, Dropper, Crafter,");
            lore.add("§7Hopper, Shulkerbox, Cauldron und Barrel");
            lore.add("");
            lore.add("§3Klicke, um zu togglen");
            meta.setLore(lore);
            lore.clear();
            item.setItemMeta(meta);
            item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
            item = ItemEditor.addPersistentData(item, "sb_member", "");
            item = ItemEditor.addPersistentData(item, "sb_target_member", memberUuid);
            item = ItemEditor.addPersistentData(item, "sb_member_prop_container_opening", "deny");
            item = ItemEditor.addAllHideItemFlags(item);
            inv.setItem(21, item);
        } else {
            item = new ItemStack(Material.BOOK);
            meta = item.getItemMeta();
            meta.setDisplayName("§eContainerblöcke öffnen");
            lore.add("§7Erlaube dem Member das " + XColor.green + "Öffnen von Container Blöcken§7.");
            lore.add("§7Aktuell " + XColor.orange + "verboten§7.");
            lore.add("");
            lore.add(XColor.green + "Geschützte Containerblöcke:");
            lore.add("§7Chest, Furnace, Smoker, Campfire, Decorated Pot,");
            lore.add("§7Flower Pot, Chiseled Bookshelf,");
            lore.add("§7Jukebox, Dispenser, Dropper, Crafter,");
            lore.add("§7Hopper, Shulkerbox, Cauldron und Barrel");
            lore.add("");
            lore.add("§3Klicke, um zu togglen");
            meta.setLore(lore);
            lore.clear();
            item.setItemMeta(meta);
            item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
            item = ItemEditor.addPersistentData(item, "sb_member", "");
            item = ItemEditor.addPersistentData(item, "sb_target_member", memberUuid);
            item = ItemEditor.addPersistentData(item, "sb_member_prop_container_opening", "allow");
            item = ItemEditor.addAllHideItemFlags(item);
            inv.setItem(21, item);
        }

        // Permission: Allow Redstone blocks interaction
        if(mp.isAllowedInteractRedstoneBlocks()) {
            item = new ItemStack(Material.ENCHANTED_BOOK);
            meta = item.getItemMeta();
            meta.setDisplayName("§eMit Technikblöcken interagieren");
            meta.addEnchant(Enchantment.EFFICIENCY, 1, true);
            lore.add("§7Verbiete dem Member das " + XColor.green + "Interagieren mit technischen Blöcken§7.");
            lore.add("§7Aktuell " + XColor.green + "erlaubt§7.");
            lore.add("");
            lore.add(XColor.orange + "Nicht geschützte Technikblöcke:");
            lore.add("§7Lever, Buttons, Daylight Detector, Noteblock,");
            lore.add("§7Repeater, Comparator, Command Block,");
            lore.add("§7Chain Command Block, Repeating Command Block,");
            lore.add("§7Command Block Minecart, Pressure Plates und Redstone");
            lore.add("");
            lore.add("§3Klicke, um zu togglen");
            meta.setLore(lore);
            lore.clear();
            item.setItemMeta(meta);
            item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
            item = ItemEditor.addPersistentData(item, "sb_member", "");
            item = ItemEditor.addPersistentData(item, "sb_target_member", memberUuid);
            item = ItemEditor.addPersistentData(item, "sb_member_prop_interact_redstone_blocks", "deny");
            item = ItemEditor.addAllHideItemFlags(item);
            inv.setItem(22, item);
        } else {
            item = new ItemStack(Material.BOOK);
            meta = item.getItemMeta();
            meta.setDisplayName("§eMit Technikblöcken interagieren");
            lore.add("§7Erlaube dem Member das " + XColor.green + "Interagieren mit technischen Blöcken§7.");
            lore.add("§7Aktuell " + XColor.orange + "verboten§7.");
            lore.add("");
            lore.add(XColor.green + "Geschützte Technikblöcke:");
            lore.add("§7Lever, Buttons, Daylight Detector, Noteblock,");
            lore.add("§7Repeater, Comparator, Command Block,");
            lore.add("§7Chain Command Block, Repeating Command Block,");
            lore.add("§7Command Block Minecart, Pressure Plates und Redstone");
            lore.add("");
            lore.add("§3Klicke, um zu togglen");
            meta.setLore(lore);
            lore.clear();
            item.setItemMeta(meta);
            item = ItemEditor.addPersistentData(item, "sb_guibtn", "");
            item = ItemEditor.addPersistentData(item, "sb_member", "");
            item = ItemEditor.addPersistentData(item, "sb_target_member", memberUuid);
            item = ItemEditor.addPersistentData(item, "sb_member_prop_interact_redstone_blocks", "allow");
            item = ItemEditor.addAllHideItemFlags(item);
            inv.setItem(22, item);
        }


        p.openInventory(inv);
    }

}
