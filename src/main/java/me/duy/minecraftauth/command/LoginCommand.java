package me.duy.minecraftauth.command;

import me.duy.minecraftauth.auth.AuthManager;
import me.duy.minecraftauth.database.DatabaseManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LoginCommand implements CommandExecutor {

    private final AuthManager authManager;


    public LoginCommand(AuthManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        if(args.length!=1){
            player.sendMessage("Usage: /login <password>");
            return true;
        }
        if(authManager.signin(player, args[0])){
            player.sendMessage("Login success!");
            player.sendMessage("Welcome back: "+ player.getName());
            player.teleport(authManager.getReturnLocation(player));


            authManager.removeLocation(player);

        }else{
            player.sendMessage("Login failed!");
        }
        return true;
    }
}
