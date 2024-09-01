package de.bdj.sb.command;

import de.bdj.sb.SB;
import de.bdj.sb.Settings;
import de.bdj.sb.island.*;
import de.bdj.sb.island.result.AddMemberToIslandResult;
import de.bdj.sb.island.result.IslandCreatorReserveResult;
import de.bdj.sb.island.result.RemoveMemberFromIslandResult;
import de.bdj.sb.profile.PlayerProfile;
import de.bdj.sb.profile.ProfileManager;
import de.bdj.sb.utlility.Chat;
import org.bukkit.entity.Player;

public class SkyBlockFunction {

    public static AddMemberToIslandResult addOnlineMember(Player owner, Player target) {
        int ownerIslandId = ProfileManager.getProfile(owner.getUniqueId()).getIslandId();
        int targetIslandId = ProfileManager.getProfile(target.getUniqueId()).getIslandId();
        if(ownerIslandId < 1) {
            Chat.error(owner, "Du musst dir erst selbst eine Insel erstellen.");
            return AddMemberToIslandResult.CANCELLED_YOU_DO_NOT_HAVE_ISLAND;
        } else if(targetIslandId < 1) {
            if(IslandDataWriter.addMemberToIsland(ownerIslandId, target.getUniqueId())) {
                return AddMemberToIslandResult.SUCCESS_MEMBER_ADDED;
            } else return AddMemberToIslandResult.CANCELLED_PLAYER_IS_ALREADY_MEMBER;
        } else {
            Chat.error(owner, "Der Spieler hat bereits eine Insel.");
            return AddMemberToIslandResult.CANCELLED_TARGET_PLAYER_ALREADY_HAS_ISLAND;
        }
    }

    public static RemoveMemberFromIslandResult removeOnlineMember(Player owner, Player target) {
        int ownerIslandId = ProfileManager.getProfile(owner.getUniqueId()).getIslandId();
        int targetIslandId = ProfileManager.getProfile(target.getUniqueId()).getIslandId();
        if(ownerIslandId < 1) {
            Chat.error(owner, "Du musst dir erst selbst eine Insel erstellen.");
            return RemoveMemberFromIslandResult.CANCELLED_YOU_DO_NOT_HAVE_ISLAND;
        } else if(targetIslandId == ownerIslandId) {
            if(IslandDataWriter.removeMemberFromIsland(ownerIslandId, target.getUniqueId())) {
                return RemoveMemberFromIslandResult.SUCCESS_MEMBER_REMOVE;
            } else return RemoveMemberFromIslandResult.CANCELLED_PLAYER_IS_NOT_MEMBER;
        } else return RemoveMemberFromIslandResult.CANCELLED_PLAYER_IS_NOT_MEMBER;
    }

    public static void createIsland(Player p) {
        PlayerProfile pro = ProfileManager.getProfile(p.getUniqueId());
        if(pro.getIslandId() < 1 || pro.getIslandId() == 0) {
            //Create an island
            IslandCreator ic = new IslandCreator(p);
            IslandCreatorReserveResult icrr = ic.reserve();
            if(icrr == IslandCreatorReserveResult.SUCCESS_ISLAND_RESERVED) {
                //
                IslandManager.reloadFile(ProfileManager.getProfile(p.getUniqueId()).getIslandId());
                IslandGenManager.cancelIslandPlacer(p.getUniqueId());
                Chat.info(p, "Deine Insel wurde erstellt. Du hast die Insel-ID: " + ProfileManager.getProfile(p.getUniqueId()).getIslandId());
            } else if(icrr == IslandCreatorReserveResult.CANCELLED_PLAYER_IS_ALREADY_OWNING_AN_ISLAND) {
                //
                Chat.info(p, "Du hast bereits eine Insel: CANCELLED_PLAYER_IS_ALREADY_OWNING_AN_ISLAND");
                IslandGenManager.cancelIslandPlacer(p.getUniqueId());
            } else if(icrr == IslandCreatorReserveResult.CANCELLED_OLD_PLAYER_ISLAND_FILE_STILL_EXISTS) {
                IslandGenManager.cancelIslandPlacer(p.getUniqueId());
            }
        } else {
            //Teleport to the players island
            IslandProfile ip = IslandManager.getIslandDataFromIndexFile(pro.getIslandId());

            if(ip.isClaimed()) {
                Chat.info(p, "Du wirst zu deiner Insel teleportiert...");
                ip.teleport(p);
            } else {
                Chat.error(p, "Es tut uns leid. Etwas ist schief gelaufen mit deiner Insel.");
                Chat.sendOperatorMessage("Der Spieler " + p.getName() + " hat Probleme mit seiner Insel. Er/Sie versuchte vermutlich mit /is zur Insel zu gelangen.",
                        "Möglicherweise fehlt die Inseldatei '" + SB.name() + "/islands/" + p.getUniqueId().toString() + ".yml'.",
                        "Es kann auch sein, dass in der " + Settings.islandIndexFileName + " Datei die Werte für 'Claimed' und 'Owner UUID' fehlen.");
            }

        }
    }

}
