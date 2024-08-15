package de.bdj.sb;

import de.bdj.sb.command.SBCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SB extends JavaPlugin {

    public static SB getInstance() { return getPlugin(SB.class); }

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
    }

    private void init() {
        log("---> init()");

    }

    private void postInit() {
        log("---> postInit()");
        new SBCommand();
    }


    public static void log(String... strings) {
        for(String msg : strings) {
            Bukkit.getConsoleSender().sendMessage(msg);
        }
    }
}
