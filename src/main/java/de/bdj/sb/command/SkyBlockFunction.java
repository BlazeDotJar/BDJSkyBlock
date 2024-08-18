package de.bdj.sb.command;

import de.bdj.sb.island.IslandManager;
import de.bdj.sb.island.result.AddMemberToIslandResult;
import de.bdj.sb.island.result.RemoveMemberFromIslandResult;
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
            if(IslandManager.addMemberToIsland(ownerIslandId, target.getUniqueId())) {
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
            if(IslandManager.removeMemberFromIsland(ownerIslandId, target.getUniqueId())) {
                return RemoveMemberFromIslandResult.SUCCESS_MEMBER_REMOVE;
            } else return RemoveMemberFromIslandResult.CANCELLED_PLAYER_IS_NOT_MEMBER;
        } else return RemoveMemberFromIslandResult.CANCELLED_PLAYER_IS_NOT_MEMBER;
    }

}
