package de.bdj.sb.island.generator;

import de.bdj.sb.SB;
import de.bdj.sb.utlility.Chat;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CobbleGeneratorRenewed implements Listener {

    private static Random r = new Random();
    public static ConcurrentLinkedQueue<GeneratorResultData> results = new ConcurrentLinkedQueue<GeneratorResultData>();

    public CobbleGeneratorRenewed() {
        SB.getInstance().getServer().getPluginManager().registerEvents(this, SB.getInstance());
        ArrayList<GeneratorResultData> datas = CobblestoneGeneratorFileReader.getCobbleResultData();

        for(GeneratorResultData data : datas) {
            for(int i = 0; i != data.getEntries(); i++) results.add(data);
        }
    }

    public static void reload() {
        results.clear();
        ArrayList<GeneratorResultData> datas = CobblestoneGeneratorFileReader.getCobbleResultData();

        for(GeneratorResultData data : datas) {
            for(int i = 0; i != data.getEntries(); i++) results.add(data);
        }
    }


    @EventHandler
    public void onCobble(BlockFromToEvent e) {
        Material type = e.getBlock().getType();
        if(type == Material.WATER || type == Material.LAVA){
            Block b = e.getToBlock();
            if(b.getType() == Material.AIR){
                if (generatesCobble(type, b)){
                    e.setCancelled(true);
                    //e.getToBlock().getWorld().playSound(e.getToBlock().getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1f, 6f);
                    Material mat = pickRandomResult(2, e.getToBlock().getLocation());
                    if(!mat.toString().contains("CHEST")) {
                        e.getToBlock().getWorld().playSound(e.getToBlock().getLocation(), Sound.BLOCK_BASALT_BREAK, 1f, 0f);
                        e.getToBlock().getLocation().getBlock().setType(mat);
                    }else e.getToBlock().getWorld().playSound(e.getToBlock().getLocation(), Sound.BLOCK_WOOD_PLACE, 1f, 1f);
                }
            }
        }
    }

    private final BlockFace[] faces = new BlockFace[]{
            BlockFace.SELF,
            BlockFace.UP,
            BlockFace.DOWN,
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST
    };

    public boolean generatesCobble(Material type, Block b){
        Material mirrorID1 = (type == Material.WATER ? Material.LAVA : Material.WATER);
        for (BlockFace face : faces){
            Block r = b.getRelative(face, 1);
            if (r.getType() == mirrorID1){
                return true;
            }
        }
        return false;
    }




    public static Material pickRandomResult(int variante, Location location) {

        Material mat = Material.AIR;
        double pick = r.nextInt(100) * 0.01d;
        double m_p = 0;
        switch(variante) {
            case 1:
                for(GeneratorResultData data : results) {

                    m_p = (data.getPercentage() * 0.01d);

                    if(pick >= (1-m_p)) {
                        mat = data.getResult();
                    }
                    else break;
                }

                if(mat == Material.AIR) mat = Material.COBBLESTONE;
                break;

            case 2:
                boolean succes = false;
                while(!succes) {
                    int i = r.nextInt(results.size());

                    //'Die While Schleife geht unendlich lange'  <== Was habe ich damit gemeint? Kann eventuell entfernt werden
                    while(pick > (((GeneratorResultData) results.toArray()[i]).getPercentage() * 0.01d)) {
                        i = r.nextInt(results.size());
                        pick = r.nextInt(1000) * 0.001d;
                    }
                    if(pick < ((GeneratorResultData) results.toArray()[i]).getPercentage()) mat = ((GeneratorResultData) results.toArray()[i]).getResult();

                    if(mat.toString().contains("CHEST")) {
                        if(location != null) {
                            //TODO: Place Loot Chest here
                            //succes = LootManager.placeChestWithLoot(location);
                            // Delete folowing code when implementing chbests
                            location.getBlock().setType(Material.CHEST);
                            succes = true;
                        }
                    }else succes = true;
                }

                break;

        }
        return mat;
    }

    public static class CobblestoneGeneratorFileReader {

        public CobblestoneGeneratorFileReader() {

        }

        /**
         * Gibt eine ArrayList mit den Resultaten eines Cobblegenerators zurück
         */
        public static ArrayList<GeneratorResultData> getCobbleResultData() {
            String kit = "DEFAULT";

            File file = new File("plugins/" + SB.getInstance().getDescription().getName() + "/cobble_results.yml");
            if(!file.exists()) {
                SB.getInstance().saveResource("cobble_results.yml", false);
                file = new File("plugins/" + SB.getInstance().getDescription().getName() + "/cobble_results.yml");
            }
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            ArrayList<String> raw_results = (ArrayList<String>) cfg.getStringList("Generator."+kit+".Results");
            String[] splitter = ":".split(":");
            GeneratorResultData data = null;
            String a = "";
            String b = "";
            String c = "";
            String arg1 = "";
            String arg2 = "";
            String arg3 = "";
            String arg4 = "";
            String arg5 = "";

            int i = 0;
            double p = 0d;

            ArrayList<GeneratorResultData> results = new ArrayList<GeneratorResultData>();

            for(String s : raw_results) {
                //Material Herausfiltern
                splitter = s.split(" ");
                a = splitter[0];

                //Amount Of Entries herausfiltern
                b = splitter[1];

                //Chance To Place herausfiltern
                c = splitter[2].replace("%", "");

                //Argumente herausfiltern
                if(splitter.length >= 4) arg1 = splitter[3];
                if(splitter.length >= 5) arg2 = splitter[4];
                if(splitter.length >= 6) arg3 = splitter[5];
                if(splitter.length >= 7) arg4 = splitter[6];
                if(splitter.length >= 8) arg5 = splitter[7];

                i = Integer.parseInt(b);
                p = Double.parseDouble(c);
                try {
                    Material.valueOf(a);
                }catch(Exception ex) {
                    Chat.sendOperatorMessage("Cobblestone Konfigurationsdatei falsch konfiguriert!", "Kann Material " + a + " nicht erfassen!");
                }


                if(arg1.equals("") == false) {
                    if(arg2.equals("") == false) {
                        if(arg3.equals("") == false) {
                            if(arg4.equals("") == false) {
                                if(arg5.equals("") == false) {
                                    data = new GeneratorResultData(Material.valueOf(a), i, p, arg1, arg2, arg3, arg4, arg5);
                                }else data = new GeneratorResultData(Material.valueOf(a), i, p, arg1, arg2, arg3, arg4);
                            }else data = new GeneratorResultData(Material.valueOf(a), i, p, arg1, arg2, arg3);
                        }else data = new GeneratorResultData(Material.valueOf(a), i, p, arg1, arg2);
                    }else data = new GeneratorResultData(Material.valueOf(a), i, p, arg1);
                }else data = new GeneratorResultData(Material.valueOf(a), i, p);

                results.add(data.clone());
            }
            return results;
        }



    }

    public static class GeneratorResultData {

        private Material result;
        private int amount_of_entries;
        private double percentage;
        private String arg1, //CONTENT KEY FOR CHESTS
                arg2, //SETTINGS KEY FOR SPECIAL BLOCKS
                arg3, //SPACEHOLDER
                arg4, //SPACEHOLDER
                arg5; //SPACEHOLDER

        public GeneratorResultData(Material material, int amount_of_entries, double percentage) {
            this.result = material;
            this.amount_of_entries = amount_of_entries;
            this.percentage = percentage;
        }

        public GeneratorResultData(Material material, int amount_of_entries, double percentage, String arg1) {
            this.result = material;
            this.amount_of_entries = amount_of_entries;
            this.percentage = percentage;
            this.arg1 = arg1;
        }

        public GeneratorResultData(Material material, int amount_of_entries, double percentage, String arg1, String arg2) {
            this.result = material;
            this.amount_of_entries = amount_of_entries;
            this.percentage = percentage;
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        public GeneratorResultData(Material material, int amount_of_entries, double percentage, String arg1, String arg2, String arg3) {
            this.result = material;
            this.amount_of_entries = amount_of_entries;
            this.percentage = percentage;
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
        }

        public GeneratorResultData(Material material, int amount_of_entries, double percentage, String arg1, String arg2, String arg3, String arg4) {
            this.result = material;
            this.amount_of_entries = amount_of_entries;
            this.percentage = percentage;
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
            this.arg4 = arg4;
        }

        public GeneratorResultData(Material material, int amount_of_entries, double percentage, String arg1, String arg2, String arg3, String arg4, String arg5) {
            this.result = material;
            this.amount_of_entries = amount_of_entries;
            this.percentage = percentage;
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
            this.arg4 = arg4;
            this.arg5 = arg5;
        }

        public Material getResult() {
            return result;
        }

        public int getEntries() {
            return amount_of_entries;
        }

        public double getPercentage() {
            return percentage;
        }

        public String getArg1() {
            return arg1;
        }

        public String getArg2() {
            return arg2;
        }

        public String getArg3() {
            return arg3;
        }

        public String getArg4() {
            return arg4;
        }

        public String getArg5() {
            return arg5;
        }

        public boolean hasArg1() { return (arg1.equals("")); }
        public boolean hasArg2() { return (arg2.equals("")); }
        public boolean hasArg3() { return (arg3.equals("")); }
        public boolean hasArg4() { return (arg4.equals("")); }
        public boolean hasArg5() { return (arg5.equals("")); }

        public GeneratorResultData clone() {
            return new GeneratorResultData(result, amount_of_entries, percentage, arg1, arg2, arg3, arg4, arg5);
        }
    }

}