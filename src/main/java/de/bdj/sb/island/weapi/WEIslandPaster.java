package de.bdj.sb.island.weapi;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import de.bdj.sb.SB;
import de.bdj.sb.utlility.Chat;
import org.bukkit.Location;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class WEIslandPaster {

    public static void pasteSchematic(Location loc, String schematicName) {
        File schematicFile = new File("plugins/" + SB.name() + "/schematics/" + schematicName + ".schem");
        if(!schematicFile.exists()) {
            SB.getInstance().saveResource("schematics/" + schematicName + ".schem", false);
            File f = new File("plugins/" + SB.name() + "/" + schematicName + ".schem");
            f.renameTo(new File("plugins/" + SB.name() + "/schematics/" + schematicName + ".schem"));
            schematicFile = new File("plugins/" + SB.name() + "/schematics/" + schematicName + ".schem");
        }

        if(!schematicFile.exists()) {
            Chat.sendOperatorMessage("Konnte insel nicht pasten!!");
            return;
        }
        com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(loc.getWorld());

        try (EditSession editSession = com.sk89q.worldedit.WorldEdit.getInstance().newEditSession(adaptedWorld)) {
            ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);
            if (format != null) {
                try (ClipboardReader reader = format.getReader(new FileInputStream(schematicFile))) {
                    Clipboard clipboard = reader.read();

                    ForwardExtentCopy copy = new ForwardExtentCopy(
                            clipboard,
                            clipboard.getRegion(),
                            clipboard.getOrigin(),
                            editSession,
                            BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())
                    );
                    Operations.complete(copy);
                    editSession.flushSession();
                } catch (WorldEditException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
