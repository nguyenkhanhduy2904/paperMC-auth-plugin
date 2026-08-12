package me.duy.minecraftauth.listener;

import io.papermc.paper.event.player.PlayerPickItemEvent;
import me.duy.minecraftauth.auth.AuthManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

public class PlayerRestrictedListener implements Listener {

    private final AuthManager authManager;

    public PlayerRestrictedListener(AuthManager authManager) {
        this.authManager = authManager;
    }

    public boolean isRestricted(Player player){
        return !(authManager.isAuthenticated(player));
    }




    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (isRestricted(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (isRestricted(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (isRestricted(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {//cant move + look around
        if (isRestricted(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamageByPlayer(EntityDamageByEntityEvent event) {//cant damage others( include mob)
        if (event.getDamager() instanceof Player player
                && isRestricted(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {//cant take damage (also from non mob like lava or suffocate)
        if (event.getEntity() instanceof Player player
                && isRestricted(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (authManager.isAuthenticated(player)) {
            return;
        }

        String message = event.getMessage().toLowerCase();

        if (message.startsWith("/login ")
                || message.equals("/login")
                || message.startsWith("/register ")
                || message.equals("/register")) {
            return;
        }

        event.setCancelled(true);
        player.sendMessage("You must login first.");
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if (event.getWhoClicked() instanceof Player player
                && isRestricted(player)) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event){
        if (event.getWhoClicked() instanceof Player player
                && isRestricted(player)) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event){
        if (event.getPlayer() instanceof Player player
                && isRestricted(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        if (isRestricted(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickupItem(EntityPickupItemEvent event) {//walk over pick up
        if (event.getEntity() instanceof Player player
                && isRestricted(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickItem(PlayerPickItemEvent event) {//pick by using middle mouse
        if (isRestricted(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onConsumeItem(PlayerItemConsumeEvent event){
        Player player = event.getPlayer();
        if (isRestricted(player)) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onVehicleEnter(VehicleEnterEvent event){
        if (event.getEntered() instanceof Player player
                && isRestricted(player)) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onVehicleDamage(VehicleDamageEvent event) {
        if (event.getAttacker() instanceof Player player
                && isRestricted(player)) {
            event.setCancelled(true);
        }
    }





}
