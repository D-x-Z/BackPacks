package net.danh.backpacks.Events;

import net.danh.backpacks.utils.BackPacks;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class PrepareItemCraft implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCraft(PrepareItemCraftEvent e) {

        for (int i = 1; i <= 9; i++) {
            if (e.getInventory().getItem(i) == null) continue;

            ItemStack item = e.getInventory().getItem(i);

            if (BackPacks.isBackpack(item) || BackPacks.isnewBackpack(item)) {
                e.getInventory().setResult(new ItemStack(Material.AIR));
                break;
            }

        }

    }

}