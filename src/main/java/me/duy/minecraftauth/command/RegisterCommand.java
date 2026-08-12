package me.duy.minecraftauth.command;

import me.duy.minecraftauth.auth.AuthManager;
import me.duy.minecraftauth.database.DatabaseManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RegisterCommand implements CommandExecutor {

    private final AuthManager authManager;


    public RegisterCommand(AuthManager authManager) {
        this.authManager = authManager;

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player player)){
            sender.sendMessage("Only player can use this");
            return true;
        }
        if(args.length!=1){
            player.sendMessage("Usage: /register <password>");
            return true;
        }
        if(authManager.register(player, args[0])){
            player.sendMessage("Registration successful! Now login using /login <password>");

        }else{
            player.sendMessage("Register failed!");
        }
        return true;
    }
}
