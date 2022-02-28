package net.danh.backpacks.Events;

import net.danh.backpacks.Backpacks;
import net.danh.backpacks.Items.BackPacksItems;
import net.danh.backpacks.Items.Handler;
import net.danh.backpacks.utils.BackPacks;
import net.danh.backpacks.utils.Chat;
import net.danh.backpacks.utils.Files;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Objects;

public class PlayerInteract implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent e) {

        if (!e.hasItem()) return;

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            ItemStack is = e.getPlayer().getInventory().getItemInMainHand();

            if (BackPacks.isnewBackpack(is)) {

                is.setAmount(is.getAmount() - 1);

                e.getPlayer().getInventory().addItem(BackPacksItems.makeNew());

                String unboxMsg = Files.getInstance().getconfig().getString("backpack.messages.unboxed");
                if (!Objects.requireNonNull(unboxMsg).isEmpty()) {
                    e.getPlayer().sendMessage(Chat.colorize(Files.getInstance().getconfig().getString("backpack.messages.unboxed")));
                }

                return;
            }

            if (BackPacks.isBackpack(is)) {

                ItemMeta im = is.getItemMeta();

                is.setType(Material.valueOf(Files.getInstance().getconfig().getString("backpack.material")));

                if (BackPacks.hasKey(is, "backpacks-custom-name", PersistentDataType.STRING)) {
                    Objects.requireNonNull(im).setDisplayName(Chat.colorize(Objects.requireNonNull(Files.getInstance().getconfig().getString("backpack.name.renamed")).replace("{CUSTOM_NAME}", Objects.requireNonNull(im.getPersistentDataContainer().get(new NamespacedKey(Backpacks.get(), "backpacks-custom-name"), PersistentDataType.STRING)))));
                }

                is.setItemMeta(im);

                ArrayList<ItemStack> contents = Handler.get(is);

                Inventory backpack = Bukkit.createInventory(e.getPlayer(), (Files.getInstance().getconfig().getInt("backpack.rows") * 9), Objects.requireNonNull(Files.getInstance().getconfig().getString("backpack.gui-title")));

                ArrayList<ItemStack> item = new ArrayList<>();

                boolean canHoldShulkerBoxes = Files.getInstance().getconfig().getBoolean("backpack.allow-shulker-boxes-in-backpacks");

                for (ItemStack itemStack : contents) {
                    if (!canHoldShulkerBoxes && itemStack.getType().toString().contains("SHULKER_BOX")) {
                        item.add(itemStack);
                        continue;
                    }
                    if (backpack.addItem(itemStack).isEmpty()) continue;
                    item.add(itemStack);
                }

                for (ItemStack itemStack : item) {
                    e.getPlayer().getWorld().dropItem(e.getPlayer().getLocation(), itemStack);
                }

                e.getPlayer().openInventory(backpack);

            }

        }
    }

}