package me.duy.minecraftauth.listener;

import me.duy.minecraftauth.auth.AuthManager;
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
        authManager.unauthenticate(event.getPlayer());
        if(authManager.isRegistered(event.getPlayer())){
            event.getPlayer().sendMessage("Please login using /login <password>");
//            authManager.authenticate(event.getPlayer());
        }
        else {
            event.getPlayer().sendMessage("You are not registered yet. Use /register <password>");
        }

    }

}
