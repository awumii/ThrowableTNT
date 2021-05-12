package me.xneox.throwabletnt;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class ThrowableTntCommand implements CommandExecutor {
    private final ThrowableTntPlugin plugin;

    public ThrowableTntCommand(ThrowableTntPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2) {
            sender.sendMessage(Component.text("/" + label + " <get/give> <amount> [player]").color(NamedTextColor.RED));
            return true;
        }

        ItemStack stack = this.plugin.getThrowableTntItem().clone();
        stack.setAmount(Integer.parseInt(args[1]));

        switch (args[0]) {
            case "get":
                if (sender instanceof Player) {
                    ((Player) sender).getInventory().addItem(stack);
                    sender.sendMessage(Component.text("You have received the throwable tnt(s)!").color(NamedTextColor.GREEN));
                }
                break;
            case "give":
                if (args.length < 3) {
                    sender.sendMessage(Component.text("You need to specify the player!").color(NamedTextColor.RED));
                    return true;
                }

                Player target = Bukkit.getPlayer(args[2]);
                if (target != null) {
                    target.getInventory().addItem(stack);
                    sender.sendMessage(Component.text("You gave " + target.getName() + " the throwable tnt(s)!").color(NamedTextColor.GREEN));
                    return true;
                }

                sender.sendMessage(Component.text("Player not found").color(NamedTextColor.RED));
                break;
            default:
                sender.sendMessage(Component.text("/" + label + " <get/give> <amount> [player]").color(NamedTextColor.RED));
        }
        return true;
    }

    public static class Completer implements TabCompleter {

        @Override
        public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
            if (args.length == 1) {
                return Arrays.asList("give", "get");
            }
            return null;
        }
    }
}
