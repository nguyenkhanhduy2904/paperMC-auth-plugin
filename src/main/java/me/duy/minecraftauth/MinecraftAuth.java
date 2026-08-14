package me.duy.minecraftauth;

import me.duy.minecraftauth.auth.AuthManager;
import me.duy.minecraftauth.command.LoginCommand;
import me.duy.minecraftauth.command.RegisterCommand;
import me.duy.minecraftauth.command.RestrictCommand;
import me.duy.minecraftauth.database.DatabaseManager;
import me.duy.minecraftauth.listener.PlayerConnectionListener;
import me.duy.minecraftauth.listener.PlayerRestrictedListener;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;

public final class MinecraftAuth extends JavaPlugin {

    private AuthManager authManager;
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {

        databaseManager = new DatabaseManager(getDataFolder());


        try {
            databaseManager.connect();
            getLogger().info("Connected to SQLite.");
            databaseManager.createTables();
            getLogger().info("Create table");
            authManager = new AuthManager(databaseManager);
        } catch (Exception e) {
            getLogger().severe("Could not initialize!");
            e.printStackTrace();


            getServer().getPluginManager().disablePlugin(this);
            getLogger().severe("Shutting down server...");
            getServer().shutdown();
            return;
        }


        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(authManager), this);
        getServer().getPluginManager().registerEvents(new PlayerRestrictedListener(authManager), this);

//        getWorldSpawn();

        Objects.requireNonNull(getCommand("login"))
                .setExecutor(new LoginCommand(authManager));

        Objects.requireNonNull(getCommand("register"))
                .setExecutor(new RegisterCommand(authManager));

        RestrictCommand restrictCommand = new RestrictCommand(authManager);

        Objects.requireNonNull(getCommand("restrict"))
                .setExecutor(restrictCommand);

        Objects.requireNonNull(getCommand("unrestrict"))
                .setExecutor(restrictCommand);

        getLogger().info("MinecraftAuth enable");
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if(databaseManager!=null){
            try{
               databaseManager.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void getWorldSpawn(){
        World world = getServer().getWorld("world");

        if (world != null) {
            Location spawn = world.getSpawnLocation();

            getLogger().info(
                    "World spawn: X=" + spawn.getBlockX()
                            + " Y=" + spawn.getBlockY()
                            + " Z=" + spawn.getBlockZ()
            );

            getLogger().info(
                    "Spawn chunk: X=" + spawn.getChunk().getX()
                            + " Z=" + spawn.getChunk().getZ()
            );
        }
    }
}
