package me.duy.minecraftauth;

import me.duy.minecraftauth.listener.PlayerConnectionListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftAuth extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(), this);
        getLogger().info("MinecraftAuth enable");
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
