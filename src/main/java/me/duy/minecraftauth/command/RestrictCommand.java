package me.duy.minecraftauth.command;

import me.duy.minecraftauth.auth.AuthManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RestrictCommand implements CommandExecutor {

    private final AuthManager authManager;

    public RestrictCommand(AuthManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
//        if (!sender.isOp()) {
//            sender.sendMessage("You don't have permission.");
//            return true;
//        }

        if (args.length != 1) {
            sender.sendMessage("Usage: /" + label + " <player>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null) {
            sender.sendMessage("Player not found.");
            return true;
        }

        if (command.getName().equalsIgnoreCase("restrict")) {
            authManager.restrict(target);
            sender.sendMessage("Restricted " + target.getName());
        }

        else if (command.getName().equalsIgnoreCase("unrestrict")) {
            authManager.unrestrict(target);
            sender.sendMessage("Unrestricted " + target.getName());
        }

        return true;
    }
}
