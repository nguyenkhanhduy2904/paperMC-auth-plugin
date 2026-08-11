package me.duy.minecraftauth.auth;

import me.duy.minecraftauth.share.ShareValue;
import org.bukkit.entity.Player;

import java.util.*;

public class AuthManager {
    private final Set<UUID> registeredUUID = new HashSet<>();
    private final Set<UUID> authenticatedUUID = new HashSet<>();
    private final Map<UUID, String> passwords = new HashMap<>();

    public AuthManager(){
        UUID testUuid = ShareValue.TEST_UUID;

        registeredUUID.add(testUuid);
        passwords.put(testUuid, "test123");
    }

    public boolean isRegistered(Player player){
        return registeredUUID.contains(player.getUniqueId());
    }

    public boolean isAuthenticated(Player player){
        return authenticatedUUID.contains(player.getUniqueId());
    }

    public Boolean register(Player player, String password){
        if(registeredUUID.contains(player.getUniqueId())){
            return false;
        }
        else{
            passwords.put(player.getUniqueId(), password);
            registeredUUID.add(player.getUniqueId());
            return true;
        }
    }
    public boolean signin(Player player, String password){
        if(!isRegistered(player)){
            return false;
        }

        if(isAuthenticated(player)){
            return false;
        }
        String playerPassword = passwords.get(player.getUniqueId());

        if(!password.equals(playerPassword)){
            return false;
        }
        authenticate(player);
        return true;
    }

    public void authenticate(Player player){
        authenticatedUUID.add(player.getUniqueId());
    }
    public void unauthenticate(Player player){
        authenticatedUUID.remove(player.getUniqueId());
    }




}
