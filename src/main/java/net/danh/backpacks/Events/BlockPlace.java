package net.danh.backpacks.Events;

import net.danh.backpacks.utils.BackPacks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlace(BlockPlaceEvent e) {
        if (BackPacks.isBackpack(e.getPlayer().getInventory().getItemInMainHand())) e.setCancelled(true);
        if (BackPacks.isBackpack(e.getPlayer().getInventory().getItemInOffHand())) e.setCancelled(true);

    }

}