package de.bdj.sb.utlility;

import net.md_5.bungee.api.ChatColor;

public class XColor {

    public static ChatColor c1 = ChatColor.of("#33ff7a"); //Prefix Color
    public static ChatColor c2 = ChatColor.of("#ff8c00"); //CommandHelp-Cmd Color
    public static ChatColor c3 = ChatColor.of("#00aeff"); //CommandHelp-Hover-Info Color
    public static ChatColor c4 = ChatColor.of("#47d7ff"); //CommandHelp-Hover-Permissions Color
    public static ChatColor c5 = ChatColor.of("#ff2f0a"); //OPChatPrefix Color
    public static ChatColor orange = ChatColor.of("#ff2f00");
    public static ChatColor green = ChatColor.of("#00ff04");

    public static ChatColor getByName(String name) {
        if(name.equalsIgnoreCase("c1")) return c1;
        else if(name.equalsIgnoreCase("c2")) return c2;
        else if(name.equalsIgnoreCase("c3")) return c3;
        else if(name.equalsIgnoreCase("c4")) return c4;

        return ChatColor.of("#FFFFFF");
    }

}
