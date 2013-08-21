package org.bukkit.command.defaults;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableList;

public class ExpCommand extends VanillaCommand {
    public ExpCommand() {
        super("xp");
        this.description = "Gives the specified player a certain amount of experience. Specify <amount>L to give levels instead, with a negative amount resulting in taking levels.";
        this.usageMessage = "/xp <amount> [player] OR /xp <amount>L [player]";
        this.setPermission("bukkit.command.xp");
    }

    @Override
    public SuccessType executeVanilla(CommandSender sender, String currentAlias, String[] args) {
        boolean commandSuccess = false;

        if (!testPermission(sender)) return SuccessType.getType(true, commandSuccess);

        if (args.length > 0) {
            String inputAmount = args[0];
            Player player = null;

            boolean isLevel = inputAmount.endsWith("l") || inputAmount.endsWith("L");
            if (isLevel && inputAmount.length() > 1) {
                inputAmount = inputAmount.substring(0, inputAmount.length() - 1);
            }

            int amount = getInteger(sender, inputAmount, Integer.MIN_VALUE, Integer.MAX_VALUE);
            boolean isTaking = amount < 0;

            if (isTaking) {
                amount *= -1;
            }

            if (args.length > 1) {
                player = Bukkit.getPlayer(args[1]);
            } else if (sender instanceof Player) {
                player = (Player) sender;
            }

            if (player != null) {
                if (isLevel) {
                    if (isTaking) {
                        player.giveExpLevels(-amount);
                        Command.broadcastCommandMessage(sender, "Taken " + amount + " level(s) from " + player.getName());
                    } else {
                        player.giveExpLevels(amount);
                        Command.broadcastCommandMessage(sender, "Given " + amount + " level(s) to " + player.getName());
                    }
                } else {
                    if (isTaking) {
                        sender.sendMessage(ChatColor.RED + "Taking experience can only be done by levels, cannot give players negative experience points");
                        return SuccessType.getType(false, commandSuccess);
                    } else {
                        player.giveExp(amount);
                        Command.broadcastCommandMessage(sender, "Given " + amount + " experience to " + player.getName());
                    }
                }

                commandSuccess = true;
            } else {
                sender.sendMessage("Can't find player, was one provided?\n" + ChatColor.RED + "Usage: " + usageMessage);
                return SuccessType.getType(false, commandSuccess);
            }

            return SuccessType.getType(true, commandSuccess);
        }

        sender.sendMessage(ChatColor.RED + "Usage: " + usageMessage);
        return SuccessType.getType(false, commandSuccess);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");

        if (args.length == 2) {
            return super.tabComplete(sender, alias, args);
        }
        return ImmutableList.of();
    }
}
