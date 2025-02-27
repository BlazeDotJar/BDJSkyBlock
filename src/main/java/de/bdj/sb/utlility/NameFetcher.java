package de.bdj.sb.utlility;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class NameFetcher {

    private static final String MOJANG_API_PROFILE = "https://api.mojang.com/users/profiles/minecraft/%s";
    private static final String MOJANG_API_NAMES = "https://api.mojang.com/user/profiles/%s/names";

    /**
     * Holt die UUID eines Spielers anhand seines Namens.
     *
     * @param playerName Der Name des Spielers.
     * @return Die UUID des Spielers oder null, wenn nicht gefunden.
     */
    public static UUID getUUID(String playerName) {
        // Zuerst versuchen, den Spieler lokal zu finden
        Player onlinePlayer = Bukkit.getPlayerExact(playerName);
        if (onlinePlayer != null) {
            return onlinePlayer.getUniqueId();
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
        if (offlinePlayer.hasPlayedBefore()) {
            return offlinePlayer.getUniqueId();
        }

        // Wenn der Spieler nicht lokal gefunden wurde, die Mojang-API abfragen
        try {
            URL url = new URL(String.format(MOJANG_API_PROFILE, playerName));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
                JsonElement jsonElement = JsonParser.parseReader(reader);
                String uuidStr = jsonElement.getAsJsonObject().get("id").getAsString();
                return UUID.fromString(uuidStr.replaceFirst(
                        "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{12})",
                        "$1-$2-$3-$4-$5"
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getName(String uuid) {
        return getName(UUID.fromString(uuid));
    }
    /**
     * Holt den Namen eines Spielers anhand seiner UUID.
     *
     * @param uuid Die UUID des Spielers.
     * @return Der Name des Spielers oder null, wenn nicht gefunden.
     */
    public static String getName(UUID uuid) {
        // Zuerst versuchen, den Spieler lokal zu finden
        Player onlinePlayer = Bukkit.getPlayer(uuid);
        if (onlinePlayer != null) {
            return onlinePlayer.getName();
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if (offlinePlayer.hasPlayedBefore()) {
            return offlinePlayer.getName();
        }

        // Wenn der Spieler nicht lokal gefunden wurde, die Mojang-API abfragen
        try {
            URL url = new URL(String.format(MOJANG_API_NAMES, uuid.toString().replace("-", "")));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
                JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
                if (jsonArray.size() > 0) {
                    return jsonArray.get(jsonArray.size() - 1).getAsJsonObject().get("name").getAsString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}