package net.danh.backpacks.Events;

import net.danh.backpacks.utils.BackPacksChecker;
import net.danh.backpacks.utils.Chat;
import net.danh.backpacks.utils.Files;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class InventoryClick implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(@NotNull InventoryClickEvent e) {

        if (e.getView().getTitle().equals(Files.getconfig().getString(Chat.colorize("backpack.gui-title")))) {
            if (e.getClick() == ClickType.NUMBER_KEY) e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getType().toString().contains("SHULKER_BOX") && !Files.getconfig().getBoolean("backpack.allow-shulker-boxes-in-backpacks"))
                e.setCancelled(true);
        }

        if (BackPacksChecker.isBackpack(e.getCurrentItem())) {
            if (e.getView().getTitle().equals(Files.getconfig().getString(Chat.colorize("backpack.gui-title"))))
                e.setCancelled(true);
            if (e.getInventory().getType() == InventoryType.SHULKER_BOX) {
                if (e.getClick() == ClickType.NUMBER_KEY) e.setCancelled(true);
                if (Objects.requireNonNull(e.getClickedInventory()).getType() == InventoryType.SHULKER_BOX)
                    return; // allow taking backpacks out of shulker boxes in case of settings change
                if (!Files.getconfig().getBoolean("backpack.allow-backpacks-in-shulker-boxes"))
                    e.setCancelled(true);
            }
        }
        if (!(e.getWhoClicked() instanceof Player)) return;

        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || e.getInventory().getType() != InventoryType.ANVIL)
            return;

        if (e.getSlotType() == InventoryType.SlotType.RESULT && BackPacksChecker.isnewBackpack(e.getCurrentItem()))
            e.setCancelled(true);

    }

}