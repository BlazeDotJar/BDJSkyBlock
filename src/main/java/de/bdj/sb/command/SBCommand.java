package de.bdj.sb.command;

import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import de.bdj.sb.utlility.Chat;
import de.bdj.sb.utlility.Perms;
import de.bdj.sb.utlility.XColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SBCommand implements CommandExecutor {

    public SBCommand() {
        ArrayList<String> cmds = new ArrayList<>();
        cmds.add("sb");

        for(String cmd : cmds) {
            SB.log("Registriere Command: /" + cmd);
            SB.getInstance().getCommand(cmd).setExecutor(this);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(sender instanceof Player p) {
            if(cmd.getName().equalsIgnoreCase("sb")) {
                switch(args.length) {
                    case 0:
                        if(Perms.hasPermission(p, Perms.getPermission("sb"))) {
                            sendCommandHelp(sender, "sb");
                        }
                        break;
                    case 1:
                        if(Perms.hasPermission(p, Perms.getPermission("sb rl"))) {
                            Settings.reload();
                            Chat.info(p, "Die Settings wurden neu geladen!");
                        }
                            break;
                }
            }
        }

        return false;
    }

    public void sendCommandHelp(CommandSender sender, String command) {
        if(command.equalsIgnoreCase("sb")) {
            if(sender instanceof Player p) {
                Chat.info(sender, "Alle verfügbaren BDJSkyBlock Befehle:");
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sb §fHilfe erhalten", XColor.c3 + "Erhalte eine klickbare Liste mit allen SkyBlock Befehlen." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb") : ""), "/sb", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sb rl §fSettings laden", XColor.c3 + "Lade die Einstellungen aus allen Config-Dateien von BDJSkyBlock neu." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb rl") : ""), "/sb rl", false, false);
            }
        }
    }
}
