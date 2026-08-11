package me.duy.minecraftauth;

import me.duy.minecraftauth.auth.AuthManager;
import me.duy.minecraftauth.command.LoginCommand;
import me.duy.minecraftauth.command.RegisterCommand;
import me.duy.minecraftauth.listener.PlayerConnectionListener;
import me.duy.minecraftauth.listener.PlayerRestrictedListener;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MinecraftAuth extends JavaPlugin {

    private AuthManager authManager;

    @Override
    public void onEnable() {
        authManager = new AuthManager();
        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(authManager), this);
        getServer().getPluginManager().registerEvents(new PlayerRestrictedListener(authManager), this);

        getWorldSpawn();

        Objects.requireNonNull(getCommand("login"))
                .setExecutor(new LoginCommand(authManager));

        Objects.requireNonNull(getCommand("register"))
                .setExecutor(new RegisterCommand(authManager));

        getLogger().info("MinecraftAuth enable");
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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
