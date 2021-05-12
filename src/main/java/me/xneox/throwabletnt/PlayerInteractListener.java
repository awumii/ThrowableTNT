package me.xneox.throwabletnt;

import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInteractListener implements Listener {
    private final ThrowableTntPlugin plugin;

    public PlayerInteractListener(ThrowableTntPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack stack = player.getInventory().getItemInMainHand();
        ItemMeta meta = stack.getItemMeta();

        if (meta == null || !meta.hasDisplayName()) {
            return;
        }

        if (this.plugin.getThrowableTntItem().getItemMeta().displayName().equals(meta.displayName())) {
            TNTPrimed tnt = player.getWorld().spawn(player.getLocation(), TNTPrimed.class);
            tnt.setVelocity(player.getLocation().getDirection().multiply(this.plugin.getConfig().getDouble("velocity")));
            tnt.setFuseTicks(this.plugin.getConfig().getInt("fuse-ticks"));
            tnt.setSource(player);

            if (stack.getAmount() == 1) {
                player.getInventory().setItemInMainHand(null);
            } else {
                stack.setAmount(stack.getAmount() - 1);
            }

            // Cancelling the event so the tnt won't be actually placed.
            event.setCancelled(true);
        }
    }
}
