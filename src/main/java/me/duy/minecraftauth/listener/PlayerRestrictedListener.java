package me.duy.minecraftauth.listener;

import me.duy.minecraftauth.auth.AuthManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerRestrictedListener implements Listener {

    private final AuthManager authManager;

    public PlayerRestrictedListener(AuthManager authManager) {
        this.authManager = authManager;
    }

    public boolean isRestricted(Player player){
        return !(authManager.isAuthenticated(player));
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {// cant move + look
        if (!authManager.isAuthenticated(event.getPlayer())) {
            event.setCancelled(true);
        }
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
    public void onDamageByPlayer(EntityDamageByEntityEvent event) {// dealing dmg to others
        if (event.getDamager() instanceof Player player
                && !authManager.isAuthenticated(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {// taking dmg, inlcude lava or non entity/mob dmg
        if (event.getEntity() instanceof Player player
                && !authManager.isAuthenticated(player)) {
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




}
