package de.bdj.sb;

import de.bdj.BDJ;
import de.bdj.sb.lobby.Lobby;
import de.bdj.sb.utlility.XColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Set;

public class Settings {

    public static String pluginPrefix = XColor.c1 + "§lSkyBlock"; //Configurable
    public static String pluginSuffix = XColor.c1 + " §8> §f"; //Configurable
    public static String opChatPrefix = XColor.c5 + "Personal-Alert: §c"; //Configurable
    public static String noPermMessage = "§cDu hast kein Recht dazu"; //Configurable
    public static String islandIndexFileName = "§cDu hast kein Recht dazu"; //Configurable
    public static String sbOverworldName = "skyblockworld"; //Configurable
    public static String sbNetherName = "skyblocknether"; //Configurable
    public static String sbEndName = "skyblockend"; //Configurable
    public static boolean teleportPlayersToWaitlobbyWhenCreating = true;
    public static boolean pluginDeveloperHelpMode = true;

    public static String confirmationSessionKey = "confirm_session";
    public static String playerCreateSessionKey = "player_create_island_session";
    public static final int islandDiameter = 400;
    public static final int spaceBetweenIslands = 25;

    public static boolean useBDJPlaceholderAPI = false;
    public static boolean useGui = true;

    public Settings() {
        reload();
    }

    public static void reload() {
        File file = new File("plugins/" + SB.name() + "/config.yml");
        if(!file.exists()) SB.getInstance().saveResource("config.yml", false);
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        String pre = cfg.getString("plugin prefix");
        String suf = cfg.getString("plugin suffix");
        String noPerm = cfg.getString("no permission message");
        String islandIndex = cfg.getString("island index file name");
        String sbOverworldName = cfg.getString("skyblock overworld name");
        String sbNetherName = cfg.getString("skyblock nether name");
        String sbEndName = cfg.getString("skyblock end name");
        pluginDeveloperHelpMode = cfg.getBoolean("skyblock plugin developer help mode");
        String lobbySpawnWorld = cfg.getString("server lobby spawn.world");
        double x = cfg.getDouble("server lobby spawn.x");
        double y = cfg.getDouble("server lobby spawn.y");
        double z = cfg.getDouble("server lobby spawn.z");
        float yaw = (float)cfg.getInt("server lobby spawn.yaw");
        float pitch = (float)cfg.getInt("server lobby spawn.pitch");

        pluginPrefix = processString(XColor.c1 + "§l" + pre);
        pluginSuffix = processString(suf);
        noPermMessage = processString(noPerm);
        islandIndexFileName = islandIndex;

        new Lobby(new Location(Bukkit.getWorld(lobbySpawnWorld), x, y, z, yaw, pitch));

        // Implement BDJPlaceholder Support
        if(useBDJPlaceholderAPI) {
            Set<String> placeholders = BDJ.getPlaceholders();
            if(placeholders.isEmpty())return;
            for(String ph : placeholders) {
                if(ph.equalsIgnoreCase("server-prefix")) pluginPrefix = BDJ.getValue(ph);
                else if(ph.equalsIgnoreCase("no-permission-message")) noPermMessage = BDJ.getValue(ph);

                SB.log("loaded data from BDJPlaceholder '" + ph + "'");
            }
        }
    }

    private static String processString(String raw) {

        if(raw == null) raw = "§c§lraw string was null!§f";

        raw = raw.replace("%pluginPrefix%", Settings.pluginPrefix);
        raw = raw.replace("%pluginSuffix%", Settings.pluginSuffix);
        raw = ChatColor.translateAlternateColorCodes('&', raw);
        return raw;
    }


}
