package net.danh.backpacks.Events;

import net.danh.backpacks.utils.BackPacksChecker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

public class BlockPlace implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlace(@NotNull BlockPlaceEvent e) {
        if (BackPacksChecker.isBackpack(e.getPlayer().getInventory().getItemInMainHand())) e.setCancelled(true);
        if (BackPacksChecker.isBackpack(e.getPlayer().getInventory().getItemInOffHand())) e.setCancelled(true);

    }

}