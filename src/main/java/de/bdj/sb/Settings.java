package de.bdj.sb;

import de.bdj.sb.utlility.XColor;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Settings {

    public static String pluginPrefix = XColor.c1 + "§lSkyBlock";
    public static String pluginSuffix = XColor.c1 + " §8> §f";
    public static String noPermMessage = "§cDu hast kein Recht dazu";

    public Settings() {
        reload();
    }

    public static void reload() {
        File file = new File("plugins/" + SB.getInstance().getDescription().getName() + "/config.yml");
        if(!file.exists()) SB.getInstance().saveResource("config.yml", false);
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        String pre = cfg.getString("plugin prefix");
        String suf = cfg.getString("plugin suffix");
        String noPerm = cfg.getString("no permission message");

        pluginPrefix = processString(XColor.c1 + "§l" + pre);
        pluginSuffix = processString(suf);
        noPermMessage = processString(noPerm);
    }

    private static String processString(String raw) {

        if(raw == null) raw = "§c§lraw string was null!§f";

        raw = raw.replace("%pluginPrefix%", Settings.pluginPrefix);
        raw = raw.replace("%pluginSuffix%", Settings.pluginSuffix);
        raw = ChatColor.translateAlternateColorCodes('&', raw);
        return raw;
    }


}
