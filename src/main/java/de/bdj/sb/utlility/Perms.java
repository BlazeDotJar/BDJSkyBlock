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
        if(command.equalsIgnoreCase("sb")) return "bdjsb.sb";
        else if(command.equalsIgnoreCase("sb rl")) return "bdjsb.sb.rl";
        else if(command.equalsIgnoreCase("sb swl")) return "bdjsb.sb.swl";
        else if(command.equalsIgnoreCase("sb twl")) return "bdjsb.sb.twl";
        else if(command.equalsIgnoreCase("sb claimed")) return "bdjsb.sb.claimed";
        else if(command.equalsIgnoreCase("sb islands")) return "bdjsb.sb.islands";
        else if(command.equalsIgnoreCase("is")) return "bdjsb.is";
        else if(command.equalsIgnoreCase("is delete")) return "bdjsb.is.delete";

        return "no permissions found for cmd " + command;
    }

}
