package de.bdj.sb.session;

import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.Chat;
import de.bdj.sb.utlility.XColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ConfirmSession extends TempSession{

    private Player host;
    private String confirmationCode = "";
    private String denyCode = "";
    private ConfirmSession instance;

    public ConfirmSession(Player host, String confirmationCode, String denyCode, String runOutOfTimeString) {
        this.host = host;
        this.confirmationCode = confirmationCode;
        this.denyCode = denyCode;
        this.runOutOfTimeString = runOutOfTimeString;
        this.instance = this;
    }
    @Override
    public void start() {
        Chat.info(host, XColor.c2 + "Bestätige mit §f" + confirmationCode + XColor.c2 + " oder lehne ab mit §f" + denyCode + XColor.c2 + " innerhalb der nächsten §f" + delayTime + XColor.c2 + " Sekunden");
        delay = new BukkitRunnable() {
            @Override
            public void run() {
                Chat.info(host, runOutOfTimeString);
                ProfileManager.getProfile(host.getUniqueId()).removeTempSession(Settings.confirmationSessionKey, instance);
            }
        };
        delay.runTaskLater(SB.getInstance(), delayTime * 20L);

        isListening = true;
    }

    @Override
    public void stop() {
        if(delay != null) delay.cancel();
        Chat.info(host, runOutOfTimeString);
        ProfileManager.getProfile(host.getUniqueId()).removeTempSession(Settings.confirmationSessionKey, this);
    }

    @Override
    public void terminate() {
        if(delay != null) delay.cancel();
        ProfileManager.getProfile(host.getUniqueId()).removeTempSession(Settings.confirmationSessionKey, this);
    }

    @Override
    public void chatEvent(AsyncPlayerChatEvent e) {
        if(!isListening) return;
        if(!e.getPlayer().getUniqueId().toString().equals(host.getUniqueId().toString())) return;
        if(!e.getMessage().equalsIgnoreCase(confirmationCode)) return;
        delay.cancel();
    }
}
