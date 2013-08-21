package org.bukkit.command.defaults;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableList;

public class SeedCommand extends VanillaCommand {
    public SeedCommand() {
        super("seed");
        this.description = "Shows the world seed";
        this.usageMessage = "/seed";
        this.setPermission("bukkit.command.seed");
    }

    @Override
    public SuccessType executeVanilla(CommandSender sender, String currentAlias, String[] args) {
        boolean commandSuccess = false;

        if (!testPermission(sender)) return SuccessType.getType(true, commandSuccess);
        long seed;
        if (sender instanceof Player) {
            seed = ((Player) sender).getWorld().getSeed();
        } else {
            seed = Bukkit.getWorlds().get(0).getSeed();
        }
        sender.sendMessage("Seed: " + seed);
        commandSuccess = true;
        return SuccessType.getType(true, commandSuccess);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");

        return ImmutableList.of();
    }
}
