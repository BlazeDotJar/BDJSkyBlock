package de.bdj.sb.command;

import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import de.bdj.sb.island.IslandDataWriter;
import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.IslandProfile;
import de.bdj.sb.island.result.AddMemberToIslandResult;
import de.bdj.sb.island.result.SetIslandSpawnResult;
import de.bdj.sb.lobby.Waitlobby;
import de.bdj.sb.profile.PlayerProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.session.ConfirmSession;
import de.bdj.sb.session.TempSession;
import de.bdj.sb.utlility.Chat;
import de.bdj.sb.utlility.Code;
import de.bdj.sb.utlility.Perms;
import de.bdj.sb.utlility.XColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

public class SBCommand implements CommandExecutor, TabCompleter {

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
            if(cmd.getName().equalsIgnoreCase("sb") && Perms.hasPermission(p, Perms.getPermission("sb"))) {
                switch(args.length) {
                    case 0:
                        sendCommandHelp(sender, "sb");
                        break;
                    case 1:
                        if(args[0].equalsIgnoreCase("rl") && Perms.hasPermission(p, Perms.getPermission("sb rl"))) {
                            Settings.reload();
                            ProfileManager.reloadAll();
                            Waitlobby.reloadLocation();
                            IslandManager.reloadFiles();
                            Chat.info(p, "Die Settings wurden neu geladen!");
                        } else if(args[0].equalsIgnoreCase("twl") && Perms.hasPermission(p, Perms.getPermission("sb twl"))) {
                            Waitlobby.teleport(p);
                            Chat.info(p, "Du wurdest zur Wartelobby teleportiert.");
                        } else if(args[0].equalsIgnoreCase("swl") && Perms.hasPermission(p, Perms.getPermission("sb swl"))) {
                            TempSession ts =  ProfileManager.getProfile(p.getUniqueId()).getTempSession("confirm_session");
                            if(ts != null) ts.terminate();
                            ConfirmSession cs = new ConfirmSession(p, "/sb swl confirm", "/sb swl deny", "Die Position der Wartelobby wurde nicht erneuert. Du warst zu langsam.", ConfirmSession.ConfirmReason.SET_WAITLOBBY_OPERATION);
                            cs.start();
                            ProfileManager.getProfile(p.getUniqueId()).addTempSession(cs);
                        } else if(args[0].equalsIgnoreCase("claimed") && Perms.hasPermission(p, Perms.getPermission("sb claimed"))) {
                            int amountClaimedIslands = IslandManager.getAmountClaimedIslands();

                            if(amountClaimedIslands < 50) {
                                IslandManager.listClaimedIslands(p, 1, 50);
                            } else {
                                Chat.info(p, "Da die Anzahl an belegten Inseln höher als 50 beträgt, muss du einen Bereich angeben.", "Beispiel: Wenn du die Inseln 10 - 30 sehen möchtest, nutze §f/sb claimed 10-30");
                            }

                        } else if(args[0].equalsIgnoreCase("islands") && Perms.hasPermission(p, Perms.getPermission("sb islands"))) {
                            int min = Code.randomInteger(1, 30);
                            int max = Code.randomInteger(min+1, min + 30);
                            Chat.info(p, "Gebe einen Bereich an, den du gerne anzeigen möchtest.", "Beispiel: §f/sb islands " + min + "-" + max);
                        } else if(args[0].equalsIgnoreCase("tp") && Perms.hasPermission(p, Perms.getPermission("sb tp"))) {
                            Chat.info(p, "Gebe eine Insel Id an. Beispiel: §f/sb tp " + Code.randomInteger(1, IslandManager.amountGenerated));
                        } else if(args[0].equalsIgnoreCase("setspawn") && Perms.hasPermission(p, Perms.getPermission("sb setspawn"))) {
                            Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sb setspawn <Insel-ID> §fFremden Inselspawn setzen", XColor.c2 + "Setzte den Insel Spawn einer anderen Insel, falls der Spieler dies nicht schaffe sollte." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb setspawn") : ""), "/sb setspawn", false, false);
                        } else if(args[0].equalsIgnoreCase("addmember") && Perms.hasPermission(p, Perms.getPermission("sb addmember"))) {
                            Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sb addmember <Spielername> <Island-ID> <Force: true/false> §fEinen Freund hinzufügen", XColor.c2 + "Sollte ein Spieler es nicht schaffen, einen anderen Spieler als Freund zur Insel hinzuzufügen,\nkannst du es mit diesen Befehl versuchen und wenn nötig auch forcen." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb addmember") : ""), "/sb addmember", false, false);
                        }
                            break;
                    case 2:
                        if(args[0].equalsIgnoreCase("swl") && Perms.hasPermission(p, Perms.getPermission("sb swl"))) {
                            if(args[1].equalsIgnoreCase("confirm")) {
                                TempSession ts =  ProfileManager.getProfile(p.getUniqueId()).getTempSession("confirm_session");
                                if(ts != null) ts.terminate();
                                Waitlobby.setNewLocation(p.getLocation());
                                Chat.info(p, "Du hast die Location der Wartelobby neu gesetzt");
                            } else if(args[1].equalsIgnoreCase("deny")) {
                                TempSession ts =  ProfileManager.getProfile(p.getUniqueId()).getTempSession("confirm_session");
                                if(ts != null) ts.terminate();
                                Chat.info(p, XColor.c2 + "Vorgang: §fWartelobby Position neusetzen " + XColor.c2 + "wurde abgelehnt.");
                            }
                        } else if(args[0].equalsIgnoreCase("claimed") && Perms.hasPermission(p, Perms.getPermission("sb claimed"))) {
                            try {
                                String[] split = args[1].split("-");
                                int start = Integer.parseInt(split[0]);
                                int end = Integer.parseInt(split[1]);

                                if(start > end) {
                                    int max = start;
                                    start = end;
                                    end = max;
                                }

                                if(end - start > IslandManager.maxClaimedListAmount) {
                                    Chat.warn(p, "Dein Bereich war zu groß und wurde auf " + IslandManager.maxClaimedListAmount + " eingeschränkt.");
                                    end = start + IslandManager.maxClaimedListAmount;
                                }

                                long startedAt = System.currentTimeMillis();

                                HashMap<Integer, IslandProfile> islands = IslandManager.getIslandsInIslandIdRange(start, end);
                                int amountClaimed = 0;
                                for(int i : islands.keySet()) {
                                    if(islands.get(i).isClaimed()) amountClaimed++;
                                }

                                if(islands.isEmpty()) {
                                    Chat.error(p, "Es gibt keine Inseln in dem von dir angegebenen Bereich §f" + start + "-" + end);
                                } else if(amountClaimed > 0) {
                                    Chat.info(p, "Insel " + start + " bis " + end + " aufgelistet(" + islands.size() + " Inseln):");
                                    for(int i : islands.keySet()) {
                                        IslandProfile ip = islands.get(i);
                                        if(!ip.isClaimed()) continue;
                                        Chat.sendHoverableCommandHelpMessage(p, " - " + XColor.c1 + "Island " + i, XColor.c2 + "Claimed: §f" + (ip.isClaimed() ? "Ja" : "Nein") + "\n" +
                                                XColor.c2 + "Owner UUID: §f" + (ip.getOwnerUuid() == null ? "none" : ip.getOwnerUuid().toString()) + "\n" +
                                                XColor.c2 + "X-Coord: §f" + ip.getX() + "\n" +
                                                XColor.c2 + "Y-Coord: §f" + IslandManager.islandY + "\n" +
                                                XColor.c2 + "Z-Coord: §f" + ip.getZ() + "\n" +
                                                XColor.c2 + "Muss bereinigt werden: §f" + (ip.needCleearing() ? "§cJa\nNutze §c/sb helpadmin islandclear" : "§aNein"), false, false);
                                    }

                                    long finishedAt = System.currentTimeMillis();
                                    Chat.info(p, "Diese Operation hat " + ((double)(finishedAt - startedAt) / 1000) + " Sekunden benötigt.");
                                } else {
                                    Chat.error(p, "In deinem angegebenen Bereich gibt es keine belegten Inseln.");
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                int min = Code.randomInteger(1, 30);
                                int max = Code.randomInteger(min, min+30);
                                Chat.error(p, "Du musst als Bereich 2 Ganzzahlen in folgenden Format angeben: §fVON-BIS", "Beispiel: /sb claimed " + min + "-" + max);
                                Chat.error(p, "Deine Argumenete: args[1]=" + args[1]);
                            }

                        } else if(args[0].equalsIgnoreCase("islands") && Perms.hasPermission(p, Perms.getPermission("sb islands"))) {
                            if(IslandManager.getProfiles().size() != IslandManager.amountGenerated) {
                                Chat.warn(p, "Beachte, dass noch nicht alle Insel Profile geladen wurden. Dies wird noch circa 2 Minuten dauern.", "Sollte also jetzt ein Fehler beim Anzeigen der Inseln auftreten, versuche es in 2 Minuten nochmal.");
                            }
                            String[] split = null;
                            int start = 0;
                            int end = 0;

                            if(args[1].contains("-")) {
                                split = args[1].split("-");
                                start = Integer.parseInt(split[0]);
                                end = Integer.parseInt(split[1]);
                            } else {
                                start = Integer.parseInt(args[1]);
                                end = start;
                            }

                            if(start > end) {
                                int max = start;
                                start = end;
                                end = max;
                            }

                            if(end - start > IslandManager.maxClaimedListAmount) {
                                Chat.warn(p, "Dein Bereich war zu groß und wurde auf " + IslandManager.maxClaimedListAmount + " eingeschränkt.");
                                end = start + IslandManager.maxClaimedListAmount;
                            }

                            if(start <= 0) start = 1;

                            long startedAt = System.currentTimeMillis();


                            HashMap<Integer, IslandProfile> islands = IslandManager.getIslandsInIslandIdRange(start, end);
                            if(islands.isEmpty()) {
                                Chat.error(p, "Es gibt keine Inseln in dem von dir angebenen Bereich §f" + start + "-" + end);
                            } else {
                                Chat.info(p, "Insel " + start + " bis " + end + " aufgelistet(" + islands.size() + " Inseln):");
                                for(int i : islands.keySet()) {
                                    IslandProfile ip = islands.get(i);
                                    Chat.sendClickableMessage(p, " - " + (ip.isClaimed() ? XColor.orange : XColor.c2) + "Island " + i, XColor.c2 + "Claimed: §f" + (ip.isClaimed() ? "Ja" : "Nein") + "\n" +
                                            XColor.c2 + "Owner UUID: §f" + (ip.getOwnerUuid() == null ? "none" : ip.getOwnerUuid().toString()) + "\n" +
                                            XColor.c2 + "X-Coord: §f" + ip.getX() + "\n" +
                                            XColor.c2 + "Y-Coord: §f" + IslandManager.islandY + "\n" +
                                            XColor.c2 + "Z-Coord: §f" + ip.getZ() + "\n" +
                                            XColor.c2 + "Muss bereinigt werden: §f" + (ip.needCleearing() ? "§cJa\nNutze §c/sb helpadmin islandclear" : "§aNein") + "\n" +
                                            XColor.c2 + "Properties: §f"  + "\n"  +
                                            ip.getProperties().toString() + "\n" +
                                            XColor.c1 + "Klicke um dich zur Insel " + ip.getIslandId() + " zu teleportieren.", "/sb tp " + ip.getIslandId(),false, false);
                                }

                                long finishedAt = System.currentTimeMillis();
                                Chat.info(p, "Diese Operation hat " + ((double)(finishedAt - startedAt) / 1000) + " Sekunden benötigt.");
                            }
                        } else if(args[0].equalsIgnoreCase("tp") && Perms.hasPermission(p, Perms.getPermission("sb tp"))) {
                            try {
                                int islandId = Integer.parseInt(args[1]);
                                if(islandId > 2500) throw new Exception("Insel ID ist zu groß");
                                if(islandId < 1) throw new Exception("Insel ID ist zu klein");
                                IslandManager.getIslandDataFromIndexFile(islandId).teleport(p);
                            }catch(Exception ex) {
                                Chat.error(p, "Du muss als Insel Id eine Ganzzahl angeben, die kleiner ist als " + IslandManager.amountGenerated + " aber größer als 0");
                            }
                        } else if(args[0].equalsIgnoreCase("setspawn") && Perms.hasPermission(p, Perms.getPermission("sb setspawn"))) {
                            try {
                                int id = Integer.parseInt(args[1]);
                                if(id < 0 || id > IslandManager.amountGenerated) {
                                    Chat.error(p, "Die Insel ID darf nicht kleine 0 und nicht größer als " + IslandManager.amountGenerated + " sein!");
                                    return false;
                                }

                                IslandProfile ip = IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(p.getUniqueId()).getIslandIsCurrentIn());
                                if(ip == null) {
                                    Chat.error(p, "Du musst dich auf der Insel " + id + " befinden!");
                                    return false;
                                }
                                if(id != ip.getIslandId()) {
                                    Chat.error(p, "Du befindest dich auf der falschen Insel!");
                                    return false;
                                }

                                SetIslandSpawnResult r = IslandDataWriter.setIslandSpawn(id, p.getLocation().clone());
                                if(r == SetIslandSpawnResult.SUCCESS) {
                                    IslandManager.reloadFile(ip.getIslandId());
                                    Chat.info(p, "Du hast den Insel Spawnpunkt von Insel " + id + " neu gesetzt!");
                                } else {
                                    Chat.warn(p, "Etwas ist schief gelaufen.");
                                }

                            } catch(Exception ex) {
                                Chat.error(p, "Du musst eine Zahl als Insel ID angeben!");
                            }
                        } else if(args[0].equalsIgnoreCase("addmember") && Perms.hasPermission(p, Perms.getPermission("sb addmember"))) {
                            Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sb addmember <Spielername> <Island-ID> <Force: true/false> §fEinen Freund hinzufügen", XColor.c2 + "Sollte ein Spieler es nicht schaffen, einen anderen Spieler als Freund zur Insel hinzuzufügen,\nkannst du es mit diesen Befehl versuchen und wenn nötig auch forcen." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb addmember") : ""), "/sb addmember", false, false);
                        }
                        break;
                    case 3:
                        if(args[0].equalsIgnoreCase("addmember") && Perms.hasPermission(p, Perms.getPermission("sb addmember"))) {
                            Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sb addmember <Spielername> <Island-ID> <Force: true/false> §fEinen Freund hinzufügen", XColor.c2 + "Sollte ein Spieler es nicht schaffen, einen anderen Spieler als Freund zur Insel hinzuzufügen,\nkannst du es mit diesen Befehl versuchen und wenn nötig auch forcen." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb addmember") : ""), "/sb addmember", false, false);
                        }
                        break;
                    case 4:
                        if(args[0].equalsIgnoreCase("addmember") && Perms.hasPermission(p, Perms.getPermission("sb addmember"))) {
                            String targetName = "";
                            Player target = null;
                            int islandId = 0;
                            boolean force = false;
                            try {
                                targetName = args[1];
                                target = Bukkit.getPlayer(targetName);
                                if(target == null) throw new Exception();
                            } catch(Exception ex) {
                                Chat.error(p, "Der Spielername wurde falsch geschrieben oder dieser Spieler ist nicht online. Deine Angabe: §f" + args[1]);
                                return false;
                            }

                            try {
                                islandId = Integer.parseInt(args[2]);
                                if(islandId < 1 || islandId > IslandManager.amountGenerated) throw new Exception();
                            } catch (Exception ex) {
                                Chat.error(p, "Die Island ID muss eine Ganzzahl zwischen 1 und " + (IslandManager.amountGenerated - 1) + " sein. Deine Angabe: §f" + args[2]);
                                return false;
                            }

                            try {
                                if(!"true".equalsIgnoreCase(args[3]) && !"false".equalsIgnoreCase(args[3])) {
                                    throw new Exception();
                                }
                                force = Boolean.parseBoolean(args[3]);
                            } catch(Exception ex) {
                                Chat.error(p, "Die 'force' Angabe muss entweder 'true' für ja oder 'false' für nein lauten. Deine Angabe: §f" + args[3]);
                                return false;
                            }

                            IslandProfile ip = IslandManager.getLoadedIslandProfile(islandId);
                            if(ip == null) {
                                Chat.error(p, "Die Inseldatei dieser Insel wurde nicht geladen. Die Ursache ist unbekannt.", "Mögliche Behebung: Der Besitzer der Insel und der Spieler, welcher zur Insel hinzugefügt werden möchte müssen rejoinen");
                                return false;
                            }

                            UUID targetUuid = target.getUniqueId();
                            IslandProfile ip2 = IslandManager.getLoadedIslandProfile(ProfileManager.getProfile(targetUuid).getIslandId());
                            if(ip2 != null && ip2.getOwnerUuid() != null && ip2.getOwnerUuid().toString().equalsIgnoreCase(targetUuid.toString())) {
                                Chat.error(p, "Der Spieler " + target.getName() + " hat bereits eine Insel.");
                                return false;
                            }

                            if(ip.getBannedPlayers().contains(targetUuid.toString())) {
                                Chat.error(p, "Der Spieler " + target.getName() + " ist auf der Insel " + islandId + " gebannt.");
                                return false;
                            }

                            Player inviter = Bukkit.getPlayer(ip.getOwnerUuid());

                            SkyBlockFunction.startAddMemberConfirmation(target, inviter);

                            if(force) {
                                target.performCommand("is confirm");
                                Chat.info(p, "Du hast den Spieler " + target.getName() + " zur Insel " + islandId + " hinzugefügt.");
                            } else {
                                Chat.info(p, "Du hast den Spieler " + target.getName() + " zur Insel " + islandId + " eingeladen.");
                            }
                        } else if(args[0].equalsIgnoreCase("delmember") && Perms.hasPermission(p, Perms.getPermission("sb delmember"))) {
                            //TODO
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
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sb §fHilfe erhalten", XColor.c2 + "Erhalte eine klickbare Liste mit allen SkyBlock Befehlen." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb") : ""), "/sb", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sb rl §fSettings laden", XColor.c2 + "Lade die Einstellungen aus allen Config-Dateien von BDJSkyBlock neu." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb rl") : ""), "/sb rl", false, false);
                Chat.sendClickableMessage(p, XColor.c2 + " /sb twl §fZur Wartelobby", XColor.c2 + "Teleportiere dich zur Wartelobby." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb twl") : ""), "/sb twl", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sb swl §fWartelobby setzen", XColor.c2 + "Setze die Location für die Wartelobby. Die Wartelobby wird von Spielern betreten,\ndie darauf warten dass ihre Insel fertig generiert wurde." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb swl") : ""), "/sb swl", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sb claimed <Von-Bis> §fBelegte Inseln", XColor.c2 + "Liste dir alle belegten Inseln auf. Erkenne auch, ob eine Insel eine Bereinigung braucht." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb claimed") : ""), "/sb claimed", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sb islands <Von-Bis> §fInseln auflisten", XColor.c2 + "Liste dir alle Inseln in einem von dir angegebenen Island-ID Bereich auf. Gebe keinen Bereich an, um automatisch den Bereich 1-50 zu wählen." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb islands") : ""), "/sb islands", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sb tp <Island ID> §fInsel-Teleport", XColor.c2 + "Teleportiere dich zu einer beliebigen Insel. Die IslandId muss eine Ganzzahl sein." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb tp") : ""), "/sb tp", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sb setspawn <Insel-ID> §fFremden Inselspawn setzen", XColor.c2 + "Setzte den Insel Spawn einer anderen Insel, falls der Spieler dies nicht schaffen sollte." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb setspawn") : ""), "/sb setspawn", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sb addmember <Spielername> <Island-ID> <Force: true/false> §fEinen Freund hinzufügen", XColor.c2 + "Sollte ein Spieler es nicht schaffen, einen anderen Spieler als Freund zur Insel hinzuzufügen,\nkannst du es mit diesen Befehl versuchen und wenn nötig auch forcen." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb addmember") : ""), "/sb addmember", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.c2 + " /sb delmember <Spielername> <Island-ID> §fEinen Freund löschen", XColor.c2 + "Sollte ein Spieler es nicht schaffen, einen anderen Spieler aus seiner Insel-Freundesliste zu löschen,\nkannst du es mit diesen Befehl forcen." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb delmember") : ""), "/sb delmember", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.orange + " /sb release <Island ID> §fInsel freigeben", XColor.c2 + "Gebe eine Insel frei, damit Spieler diese wieder beziehen können." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb release") : ""), "/sb release", false, false);
                Chat.sendSuggestCommandMessage(p, XColor.orange + " /sb helpadmin <Seiten-ID> §fAdmin-Handbuch", XColor.c2 + "Lies im Handbuch für Admins nach, wenn du vergessen hast, wie etwas zu handhaben ist." + (p.isOp() ? XColor.c4 + "\nPermission: §f" + Perms.getPermission("sb adminhelp") : ""), "/sb adminhelp", false, false);
                Chat.info(p, "Weitere Befehle von BDJSkyBlock:");
                Chat.info(p, "/is");
                if(((Player) sender).getUniqueId().toString().equals("242dad39-544a-4c3a-8d61-17a38e004a6f")) Chat.info(p, "/sbdev");
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        //TODO: BUG_1
        //Every player gets a completion, even without the needed permissions!
        if(sender instanceof Player p) {
            ArrayList<String> l = new ArrayList<>();
            if(cmd.getName().equalsIgnoreCase("sb")) {
                switch(args.length) {
                    case 1:
                        if(Perms.hasPermission(p, Perms.getPermission("sb rl"), false)) l.add("rl");
                        if(Perms.hasPermission(p, Perms.getPermission("sb twl"), false)) l.add("twl");
                        if(Perms.hasPermission(p, Perms.getPermission("sb swl"), false)) l.add("swl");
                        if(Perms.hasPermission(p, Perms.getPermission("sb claimed"), false)) l.add("claimed");
                        if(Perms.hasPermission(p, Perms.getPermission("sb islands"), false)) l.add("islands");
                        if(Perms.hasPermission(p, Perms.getPermission("sb tp"), false)) l.add("tp");
                        if(Perms.hasPermission(p, Perms.getPermission("sb setspawn"), false)) l.add("setspawn");
                        if(Perms.hasPermission(p, Perms.getPermission("sb addmember"), false)) l.add("addmember");
                        if(Perms.hasPermission(p, Perms.getPermission("sb delmember"), false)) l.add("delmember");
                        return l;
                    case 2:
                        if(args[0].equalsIgnoreCase("claimed") && Perms.hasPermission(p, Perms.getPermission("sb claimed"), false)) return Arrays.asList("1-10");
                        else if(args[0].equalsIgnoreCase("islands") && Perms.hasPermission(p, Perms.getPermission("sb islands"), false)) return Arrays.asList("1-10");
                        else if(args[0].equalsIgnoreCase("tp") && Perms.hasPermission(p, Perms.getPermission("sb tp"), false)) return Arrays.asList("1");
                        else if(args[0].equalsIgnoreCase("addmember") && Perms.hasPermission(p, Perms.getPermission("sb addmember"), false)) return null;
                    case 4:
                        if(args[0].equalsIgnoreCase("addmember") && Perms.hasPermission(p, Perms.getPermission("sb addmember"), false)) return Arrays.asList("true", "false");
                }
            }
        }
        return List.of();
    }
}
