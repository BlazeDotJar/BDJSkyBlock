package de.bdj.sb.island;

import de.bdj.sb.SB;
import de.bdj.sb.island.result.IslandPlacerStartResult;
import de.bdj.sb.utlility.Chat;

import java.util.HashMap;
import java.util.UUID;

public class IslandGenManager {

    private static final HashMap<UUID, IslandPlacer> placers = new HashMap<UUID, IslandPlacer>();
    //TODO: Add stop void to stop all placers in case the server reloads/restarts

    public static IslandPlacerStartResult runNewIslandPlacer(UUID uuid, int islandId) {
        if(uuid == null || islandId < 1) {
            SB.log("Error while starting a new IslandPlacer. UUID is null or islandId is <1.", "runNewIslandPlacer@IslandGenManager.java", "Parameters:", "UUID: " + (uuid == null ? "null" : uuid.toString()), "islandId: " + islandId);
            cancelIslandPlacer(uuid);
            return IslandPlacerStartResult.CANCELLED_PARAMETER_IS_NULL;
        }
        if(placers.containsKey(uuid)) {
            return IslandPlacerStartResult.CANCELLED_BECAUSE_ALREADY_STARTED;
        }
        placers.put(uuid, new IslandPlacer(uuid, islandId));
        return IslandPlacerStartResult.STARTED;
    }

    public static void cancelIslandPlacer(UUID uuid) {
        IslandPlacer ip = placers.get(uuid);
        if(ip == null) placers.remove(uuid);
        else placers.remove(uuid, ip);
    }

    public static boolean isRunningIslandPlacer(UUID uuid) {
        return placers.containsKey(uuid);
    }

}
