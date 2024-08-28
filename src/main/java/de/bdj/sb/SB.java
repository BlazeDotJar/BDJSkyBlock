package de.bdj.sb;

import de.bdj.NameFetcher;
import de.bdj.UUIDFetcher;
import de.bdj.sb.command.ISCommand;
import de.bdj.sb.command.SBCommand;
import de.bdj.sb.command.SBDEVCommand;
import de.bdj.sb.event.EventListener;
import de.bdj.sb.gui.GuiButtonManager;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.lobby.Waitlobby;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.TimeStamp;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SB extends JavaPlugin {

    public static SB getInstance() { return getPlugin(SB.class); }
    public static String name() { return getInstance().getDescription().getName(); }
    public static String version() { return getInstance().getDescription().getVersion(); }

    public static TimeStamp timeStamp;
    public static boolean isReady = false;

    @Override
    public void onEnable() {
        super.onEnable();

        timeStamp = new TimeStamp();

        log("---> onEnable()");

        Settings.useBDJPlaceholderAPI = this.getServer().getPluginManager().isPluginEnabled("BDJPlaceholder");

        preInit();
        init();
        postInit();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        ProfileManager.unregisterAllProfiles();
    }

    private void preInit() {
        log("---> preInit()");
        ProfileManager.reloadAll();
        Waitlobby.reloadLocation();
        Settings.reload();
        IslandManager.reloadFiles(); //WARNING: This line must come after ProfileManager has reloaded its files!!
    }

    private void init() {
        log("---> init()");

        SkyWorldGenerator.checkWorlds();
        new GuiButtonManager();

    }

    private void postInit() {
        log("---> postInit()");
        new SBCommand();
        new SBDEVCommand();
        new ISCommand();

        new EventListener();
    }


    public static void log(String... strings) {
        for(String msg : strings) {
            Bukkit.getConsoleSender().sendMessage("[" +  name() + "] " + msg);
        }
    }
}
