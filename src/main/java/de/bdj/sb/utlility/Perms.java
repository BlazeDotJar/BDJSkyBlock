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

        return "no permissions found for cmd " + command;
    }

}
