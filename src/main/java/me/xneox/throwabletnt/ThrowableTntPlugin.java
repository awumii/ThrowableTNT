package me.xneox.throwabletnt;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Collectors;

public class ThrowableTntPlugin extends JavaPlugin {
    private ItemStack throwableTntItem;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.loadItemStack();

        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(this), this);

        PluginCommand command = this.getCommand("throwabletnt");
        if (command != null) {
            command.setExecutor(new ThrowableTntCommand(this));
            command.setTabCompleter(new ThrowableTntCommand.Completer());
        }
    }

    public ItemStack getThrowableTntItem() {
        return throwableTntItem;
    }

    // Constructs the throwable tnt ItemStack from the config.
    public void loadItemStack() {
        FileConfiguration cfg = this.getConfig();
        ItemStack stack = new ItemStack(Material.valueOf(cfg.getString("item-type")));

        ItemMeta meta = stack.getItemMeta();
        meta.displayName(constructComponent(cfg.getString("item-name")));
        meta.lore(cfg.getStringList("item-lore")
                .stream()
                .map(this::constructComponent)
                .collect(Collectors.toList()));

        stack.setItemMeta(meta);
        this.throwableTntItem = stack;
    }

    // Constructs an Adventure component from provided string, also uses legacy color codes.
    private Component constructComponent(String string) {
        return LegacyComponentSerializer.legacy('&').deserialize(string);
    }
}
