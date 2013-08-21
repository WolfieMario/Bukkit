package org.bukkit.command.defaults;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MeCommand extends VanillaCommand {
    public MeCommand() {
        super("me");
        this.description = "Performs the specified action in chat";
        this.usageMessage = "/me <action>";
        this.setPermission("bukkit.command.me");
    }

    @Override
    public SuccessType executeVanilla(CommandSender sender, String currentAlias, String[] args) {
        boolean commandSuccess = false;

        if (!testPermission(sender)) return SuccessType.getType(true, commandSuccess);
        if (args.length < 1)  {
            sender.sendMessage(ChatColor.RED + "Usage: " + usageMessage);
            return SuccessType.getType(false, commandSuccess);
        }

        StringBuilder message = new StringBuilder();
        message.append(sender.getName());

        for (String arg : args) {
            message.append(" ");
            message.append(arg);
        }

        Bukkit.broadcastMessage("* " + message.toString());

        commandSuccess = true;

        return SuccessType.getType(true, commandSuccess);
    }
}
