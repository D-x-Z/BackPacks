package net.danh.backpacks.Events;

import net.danh.backpacks.Items.Handler;
import net.danh.backpacks.utils.BackPacks;
import net.danh.backpacks.utils.Files;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class InventoryClose implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClose(InventoryCloseEvent e) {

        if (!(BackPacks.isBackpack(e.getPlayer().getInventory().getItemInMainHand()) && e.getView().getTitle().equals(Files.getInstance().getconfig().getString("backpack.gui-title"))))
            return;

        Inventory playerInventory = Bukkit.createInventory(e.getPlayer(), 54, "");
        for (ItemStack itemStack : e.getInventory().getContents()) {
            if (itemStack != null) {
                playerInventory.addItem(itemStack);
            }
        }

        ArrayList<ItemStack> tidiedContents = new ArrayList<>();

        for (ItemStack itemStack : playerInventory.getContents()) {
            if (itemStack != null) {
                tidiedContents.add(itemStack);
            }
        }

        Handler.store((Player) e.getPlayer(), e.getPlayer().getInventory().getItemInMainHand(), tidiedContents);

    }

}
