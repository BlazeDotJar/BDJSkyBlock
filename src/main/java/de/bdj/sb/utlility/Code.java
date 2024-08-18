package de.bdj.sb.utlility;

import java.util.concurrent.ThreadLocalRandom;

public class Code {

    /**
     * Generiert einen Zufallswert zwischen min und max UND kann den Wert von min oder max annehmen.
     */
    public static int randomInteger(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
