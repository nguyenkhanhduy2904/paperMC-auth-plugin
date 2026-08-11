package me.duy.minecraftauth.listener;

import me.duy.minecraftauth.auth.AuthManager;
import me.duy.minecraftauth.share.ShareValue;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnectionListener implements Listener {
    private final AuthManager authManager;

    public PlayerConnectionListener(AuthManager authManager) {
        this.authManager = authManager;
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
//        event.getPlayer().sendMessage("You need to login");
        Player player = event.getPlayer();
        authManager.unauthenticate(player);

        Location originalLocation = player.getLocation().clone();
        authManager.saveReturnLocation(player, originalLocation);


        Location authLocation = new Location(
                Bukkit.getWorld("world"),
                ShareValue.authVector.getX(),
                ShareValue.authVector.getY(),
                ShareValue.authVector.getZ(),
                player.getYaw(),
                player.getPitch()
        );

        player.teleport(authLocation);




        if(authManager.isRegistered(player)){
            player.sendMessage("Please login using /login <password>");
//            authManager.authenticate(event.getPlayer());
        }
        else {
            player.sendMessage("You are not registered yet. Use /register <password>");
        }

    }



}
