package me.duy.minecraftauth.auth;

import me.duy.minecraftauth.database.DatabaseManager;
import me.duy.minecraftauth.share.ShareValue;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.*;

public class AuthManager {

//    private final Set<UUID> registeredUUID = new HashSet<>();
    private final Set<UUID> authenticatedUUID = new HashSet<>();
    private final Map<UUID, String> passwords = new HashMap<>();
    private final Map<UUID, Location> lastLocation = new HashMap<>();

    private final DatabaseManager databaseManager;

    public AuthManager(DatabaseManager databaseManager) throws SQLException {
        this.databaseManager = databaseManager;
//        UUID testUuid = ShareValue.TEST_UUID;
////        registeredUUID.add(testUuid);
//        passwords.put(testUuid, "test123");

        passwords.putAll(databaseManager.loadPasswordMap());

    }

    public boolean isRegistered(Player player){
        return passwords.containsKey(player.getUniqueId());
    }

    public boolean isAuthenticated(Player player){
        return authenticatedUUID.contains(player.getUniqueId());
    }

    public boolean register(Player player, String password){
        if(passwords.containsKey(player.getUniqueId())){
            return false;
        }
        else{
            boolean insertAccount = databaseManager.insertAccount(player.getUniqueId(), password, player.getName());

            if(!insertAccount){
                return false;
            }

            passwords.put(player.getUniqueId(), password);
//            registeredUUID.add(player.getUniqueId());
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

    public void saveReturnLocation(Player player, Location location){
        lastLocation.put(player.getUniqueId(), location);
    }
    public Location getReturnLocation(Player player){
        return lastLocation.get(player.getUniqueId());
    }
    public void removeLocation(Player player){
        lastLocation.remove(player.getUniqueId());
    }




}
