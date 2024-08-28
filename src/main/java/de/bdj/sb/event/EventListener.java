package de.bdj.sb.event;

import de.bdj.sb.SB;
import de.bdj.sb.event.gui.GuiClickListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {

    public EventListener() {
        SB.getInstance().getServer().getPluginManager().registerEvents(this, SB.getInstance());
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
    public void onSpawn(EntitySpawnEvent e) {
        MobSpawnListener.onSpawn(e);
    }
    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        GuiClickListener.onInventoryClick(e);
    }



}
