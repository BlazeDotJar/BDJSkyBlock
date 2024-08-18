package de.bdj.sb.utlility;

import de.bdj.sb.Settings;
import org.bukkit.command.CommandSender;

public class Perms {

    public static boolean hasPermission(CommandSender sender, String perm) {
        return hasPermission(sender, perm, true);
    }
    public static boolean hasPermission(CommandSender sender, String perm, boolean sendNoPerm) {
        if(sender.hasPermission(perm)) return true;
        if(sendNoPerm) Chat.info(sender, Settings.noPermMessage);
        return false;
    }

    public static String getPermission(String command) {
        if(command.equalsIgnoreCase("sb")) return "bdj.sb";
        else if(command.equalsIgnoreCase("sb rl")) return "bdj.sb.rl";
        else if(command.equalsIgnoreCase("sb swl")) return "bdj.sb.swl";
        else if(command.equalsIgnoreCase("sb twl")) return "bdj.sb.twl";
        else if(command.equalsIgnoreCase("sb claimed")) return "bdj.sb.claimed";
        else if(command.equalsIgnoreCase("sb islands")) return "bdj.sb.islands";
        else if(command.equalsIgnoreCase("sb tp")) return "bdj.sb.tp";
        else if(command.equalsIgnoreCase("is")) return "bdj.is";
        else if(command.equalsIgnoreCase("is help")) return "bdj.is.help";
        else if(command.equalsIgnoreCase("is delete")) return "bdj.is.delete";
        else if(command.equalsIgnoreCase("is member")) return "bdj.is.member";

        return "no permissions found for cmd " + command;
    }

}
