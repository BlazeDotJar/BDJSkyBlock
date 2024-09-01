package de.bdj.sb.utlility;

import de.bdj.sb.Settings;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
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


    public static void warn(CommandSender sender, String... msgs) {
        warn(sender, true, msgs);
    }

    public static void warn(CommandSender sender, boolean withPrefix, String... msgs) {
        if(msgs.length == 0) return;

        for(String msg : msgs) {
            sender.sendMessage((withPrefix ? Settings.pluginPrefix + Settings.pluginSuffix : "") + XColor.warn + msg);
        }
    }

    public static void error(CommandSender sender, String... msgs) {
        error(sender, true, msgs);
    }

    public static void error(CommandSender sender, boolean withPrefix, String... msgs) {
        if(msgs.length == 0) return;

        for(String msg : msgs) {
            sender.sendMessage((withPrefix ? Settings.pluginPrefix + Settings.pluginSuffix : "") + "§c" + msg);
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
        //TextComponent message = new TextComponent(TextComponent.fromLegacy("§7➲ " + msg));
        TextComponent message = new TextComponent(TextComponent.fromLegacy(msg));

        message.setItalic(italic);
        message.setBold(bold);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(TextComponent.fromLegacy(hovermsg)).italic(italic).color(ChatColor.GREEN).bold(bold).create()));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));

        target.spigot().sendMessage(message);
        return true;
    }

    /**
     * Sendet eine Nachricht mit Prefix an alle Operatoren
     */
    public static void sendOperatorMessage(String... strings) {
        for(String msg : strings) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(p.isOp()) p.sendMessage(Settings.opChatPrefix + msg);
            }
            Bukkit.getConsoleSender().sendMessage(Settings.opChatPrefix+msg);
        }
    }

    public static void debug(String... strings) {
        if(!Settings.pluginDeveloperHelpMode) return;
        for(String msg : strings) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(p.getUniqueId().toString().equalsIgnoreCase("242dad39-544a-4c3a-8d61-17a38e004a6f")) p.sendMessage(XColor.debug1 +"DEBUG: " + XColor.debug2 + msg);
            }
            Bukkit.getConsoleSender().sendMessage(XColor.debug1 +"DEBUG: " + XColor.debug2 + msg);
        }
    }

    public static String prepareString(String s) {
        s = s.replace("{NEWLINE}", "\n");
        return s;
    }

}
