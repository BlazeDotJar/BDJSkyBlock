package de.bdj.sb.event;

import de.bdj.sb.SB;
import de.bdj.sb.event.gui.GuiClickListener;
import de.bdj.sb.event.gui.InventoryCloseListener;
import de.bdj.sb.island.generator.CobbleGeneratorRenewed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;

public class EventListener implements Listener {

    public EventListener() {
        SB.getInstance().getServer().getPluginManager().registerEvents(this, SB.getInstance());
        new CobbleGeneratorRenewed();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        JoinQuitListener.onJoin(e);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        JoinQuitListener.onQuit(e);
    }

    @EventHandler
    public void asyncChat(AsyncPlayerChatEvent e) {
        AsyncChatListener.onAsyncChat(e);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        PlayerMoveListener.onMove(e);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        BlockListener.onBlockBreak(e);
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        BlockListener.onBlockPlace(e);
    }
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        EntityDamageEntityListener.onDamage(e);
    }
    @EventHandler
    public void onExplosion(ExplosionPrimeEvent e) {
        TNTListener.onExplosion(e);
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        EntityDamageListener.onDamage(e);
    }
    @EventHandler
    public void onFireSpread(BlockSpreadEvent e) {
        FireSpreadListener.onFireSpread(e);
    }
    @EventHandler
    public void onBlockBurn(BlockBurnEvent e) {
        FireSpreadListener.onBurn(e);
    }
    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent e) {
        FireSpreadListener.onIgnite(e);
    }
    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        MobSpawnListener.onSpawn(e);
    }
    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        GuiClickListener.onInventoryClick(e);
    }
    @EventHandler
    public void onInvClose(InventoryCloseEvent e) {
        InventoryCloseListener.onInventoryClose(e);
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        PlayerDeathListener.onPlayerRespawn(e);
    }
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        PlayerDeathListener.onPlayerRespawn(e);
    }



}
