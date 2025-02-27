package de.bdj.sb.utlility;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Gradient {

    /**
     * Sendet eine Nachricht mit einem Farbverlauf an einen Spieler.
     * @param player Der Spieler, der die Nachricht erhalten soll.
     * @param message Die Nachricht, die gesendet werden soll.
     * @param startColor Die Startfarbe im Hex-Format (z. B. "#FF0000" für Rot).
     * @param endColor Die Endfarbe im Hex-Format (z. B. "#0000FF" für Blau).
     */
    public static void send(Player player, String message, String startColor, String endColor) {
        String gradientMessage = applyGradient(message, startColor, endColor);
        player.sendMessage(gradientMessage);
    }

    /**
     * Sendet eine Nachricht mit einem Farbverlauf an alle Spieler.
     * @param message Die Nachricht, die gesendet werden soll.
     * @param startColor Die Startfarbe im Hex-Format.
     * @param endColor Die Endfarbe im Hex-Format.
     */
    public static void broadcast(String message, String startColor, String endColor) {
        String gradientMessage = applyGradient(message, startColor, endColor);
        Bukkit.broadcastMessage(gradientMessage);
    }

    /**
     * Wendet einen Farbverlauf auf eine Nachricht an.
     * @param message Die Nachricht, auf die der Farbverlauf angewendet werden soll.
     * @param startColor Die Startfarbe im Hex-Format.
     * @param endColor Die Endfarbe im Hex-Format.
     * @return Die Nachricht mit angewendetem Farbverlauf.
     */
    public static String applyGradient(String message, String startColor, String endColor) {
        StringBuilder gradientMessage = new StringBuilder();
        int length = message.length();

        int[] startRGB = hexToRgb(startColor);
        int[] endRGB = hexToRgb(endColor);

        for (int i = 0; i < length; i++) {
            double ratio = (double) i / (length - 1);
            int red = (int) (startRGB[0] + ratio * (endRGB[0] - startRGB[0]));
            int green = (int) (startRGB[1] + ratio * (endRGB[1] - startRGB[1]));
            int blue = (int) (startRGB[2] + ratio * (endRGB[2] - startRGB[2]));

            String hexColor = String.format("#%02X%02X%02X", red, green, blue);
            gradientMessage.append(ChatColor.of(hexColor)).append(message.charAt(i));
        }

        return gradientMessage.toString();
    }

    /**
     * Konvertiert einen Hex-Farbcode in ein RGB-Array.
     * @param hex Die Hex-Farbe (z. B. "#FF0000").
     * @return Ein Array mit den RGB-Werten.
     */
    private static int[] hexToRgb(String hex) {
        return new int[]{
                Integer.valueOf(hex.substring(1, 3), 16),
                Integer.valueOf(hex.substring(3, 5), 16),
                Integer.valueOf(hex.substring(5, 7), 16)
        };
    }
}

