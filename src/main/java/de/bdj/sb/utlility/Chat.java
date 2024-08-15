package de.bdj.sb.utlility;

import de.bdj.sb.Settings;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Chat {

    public static void info(CommandSender sender, String... msgs) {
        info(sender, true, msgs);
    }

    public static void info(CommandSender sender, boolean withPrefix, String... msgs) {
        if(msgs.length == 0) return;

        for(String msg : msgs) {
            sender.sendMessage((withPrefix ? Settings.pluginPrefix + Settings.pluginSuffix : "") + msg);
        }
    }

    @SuppressWarnings("deprecation")
    public static boolean sendSuggestCommandMessage(Player target, String msg, String hovermsg, String copy, boolean bold, boolean italic) {
        msg = prepareString(msg);
        hovermsg = prepareString(hovermsg);
        // fromLegacy() is needed to use hexadecimal ChatColor ------V
        TextComponent message = new TextComponent(TextComponent.fromLegacy(msg));

        message.setItalic(italic);
        message.setBold(bold);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, copy));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(TextComponent.fromLegacy(hovermsg)).italic(italic).bold(bold).create()));

        target.spigot().sendMessage(message);
        return true;
    }

    public static boolean sendHoverableCommandHelpMessage(Player target, String msg, String hovermsg, boolean bold, boolean italic) {
        msg = prepareString(msg);
        hovermsg = prepareString(hovermsg);
        // fromLegacy() is needed to use hexadecimal ChatColor ------V
        TextComponent message = new TextComponent(TextComponent.fromLegacy(msg));

        message.setItalic(italic);
        message.setBold(bold);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(TextComponent.fromLegacy(hovermsg)).italic(italic).bold(bold).color(ChatColor.DARK_AQUA).create()));

        target.spigot().sendMessage(message);
        return true;
    }

    @SuppressWarnings("deprecation")
    public static boolean sendClickableMessage(Player target, String msg, String hovermsg, String command, boolean bold, boolean italic) {
        msg = prepareString(msg);
        hovermsg = prepareString(hovermsg);
        // fromLegacy() is needed to use hexadecimal ChatColor ------V
        TextComponent message = new TextComponent(TextComponent.fromLegacy("§7➲ " + msg));

        message.setItalic(italic);
        message.setBold(bold);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(TextComponent.fromLegacy(hovermsg)).italic(italic).color(ChatColor.GREEN).bold(bold).create()));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));

        target.spigot().sendMessage(message);
        return true;
    }

    public static String prepareString(String s) {
        s = s.replace("{NEWLINE}", "\n");
        return s;
    }

}
