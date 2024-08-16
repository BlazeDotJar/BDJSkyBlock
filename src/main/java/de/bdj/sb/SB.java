package de.bdj.sb;

import de.bdj.sb.command.SBCommand;
import de.bdj.sb.event.EventListener;
import de.bdj.sb.event.JoinQuitListener;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.lobby.Waitlobby;
import de.bdj.sb.profile.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SB extends JavaPlugin {

    public static SB getInstance() { return getPlugin(SB.class); }
    public static String name() { return getInstance().getDescription().getName(); }

    @Override
    public void onEnable() {
        super.onEnable();

        log("---> onEnable()");
        preInit();
        init();
        postInit();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void preInit() {
        log("---> preInit()");
        ProfileManager.reloadAll();
        Waitlobby.reloadLocation();
        Settings.reload();
        IslandManager.reloadFiles();
    }

    private void init() {
        log("---> init()");

    }

    private void postInit() {
        log("---> postInit()");
        new SBCommand();

        new EventListener();
    }


    public static void log(String... strings) {
        for(String msg : strings) {
            Bukkit.getConsoleSender().sendMessage(msg);
        }
    }
}
