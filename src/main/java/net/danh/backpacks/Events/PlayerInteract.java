package net.danh.backpacks.Events;

import net.danh.backpacks.Backpacks;
import net.danh.backpacks.Items.BackPacks;
import net.danh.backpacks.Items.Handler;
import net.danh.backpacks.utils.BackPacksChecker;
import net.danh.backpacks.utils.Chat;
import net.danh.backpacks.utils.Files;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class PlayerInteract implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(@NotNull PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!e.hasItem()) return;

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            ItemStack is = e.getPlayer().getInventory().getItemInMainHand();

            if (BackPacksChecker.isnewBackpack(is)) {

                is.setAmount(is.getAmount() - 1);

                e.getPlayer().getInventory().addItem(BackPacks.makeNew(e.getPlayer()));

                String unboxMsg = Files.getconfig().getString("backpack.messages.open");
                if (!Objects.requireNonNull(unboxMsg).isEmpty()) {
                    e.getPlayer().sendMessage(Chat.colorize(Files.getconfig().getString("backpack.messages.open")));
                }

                return;
            }

            if (BackPacksChecker.isBackpack(is)) {

                ItemMeta im = is.getItemMeta();

                is.setType(Material.valueOf(Files.getconfig().getString("backpack.material")));

                if (BackPacksChecker.hasKey(is, "backpacks-custom-name", PersistentDataType.STRING)) {
                    Objects.requireNonNull(im).setDisplayName(Chat.colorize(Objects.requireNonNull(Files.getconfig().getString("backpack.name.renamed")).replace("%name%", Objects.requireNonNull(im.getPersistentDataContainer().get(new NamespacedKey(Backpacks.get(), "backpacks-custom-name"), PersistentDataType.STRING)))));
                }

                is.setItemMeta(im);

                ArrayList<ItemStack> contents = Handler.get(is);

                if (p.hasPermission("bp.1")) {
                    Inventory backpack = Bukkit.createInventory(e.getPlayer(), (9), Objects.requireNonNull(Files.getconfig().getString("backpack.gui-title")));

                    ArrayList<ItemStack> item = new ArrayList<>();

                    boolean canHoldShulkerBoxes = Files.getconfig().getBoolean("backpack.allow-shulker-boxes-in-backpacks");

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
                } else if (p.hasPermission("bp.2")) {
                    Inventory backpack = Bukkit.createInventory(e.getPlayer(), (18), Objects.requireNonNull(Files.getconfig().getString("backpack.gui-title")));

                    ArrayList<ItemStack> item = new ArrayList<>();

                    boolean canHoldShulkerBoxes = Files.getconfig().getBoolean("backpack.allow-shulker-boxes-in-backpacks");

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
                } else if (p.hasPermission("bp.3")) {
                    Inventory backpack = Bukkit.createInventory(e.getPlayer(), (27), Objects.requireNonNull(Files.getconfig().getString("backpack.gui-title")));

                    ArrayList<ItemStack> item = new ArrayList<>();

                    boolean canHoldShulkerBoxes = Files.getconfig().getBoolean("backpack.allow-shulker-boxes-in-backpacks");

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
                } else if (p.hasPermission("bp.4")) {
                    Inventory backpack = Bukkit.createInventory(e.getPlayer(), (36), Objects.requireNonNull(Files.getconfig().getString("backpack.gui-title")));

                    ArrayList<ItemStack> item = new ArrayList<>();

                    boolean canHoldShulkerBoxes = Files.getconfig().getBoolean("backpack.allow-shulker-boxes-in-backpacks");

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
                } else if (p.hasPermission("bp.5")) {
                    Inventory backpack = Bukkit.createInventory(e.getPlayer(), (45), Objects.requireNonNull(Files.getconfig().getString("backpack.gui-title")));

                    ArrayList<ItemStack> item = new ArrayList<>();

                    boolean canHoldShulkerBoxes = Files.getconfig().getBoolean("backpack.allow-shulker-boxes-in-backpacks");

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
                } else if (p.hasPermission("bp.6")) {
                    Inventory backpack = Bukkit.createInventory(e.getPlayer(), (54), Objects.requireNonNull(Files.getconfig().getString("backpack.gui-title")));

                    ArrayList<ItemStack> item = new ArrayList<>();

                    boolean canHoldShulkerBoxes = Files.getconfig().getBoolean("backpack.allow-shulker-boxes-in-backpacks");

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
                } else {
                    p.sendMessage(Chat.colorize(Files.getconfig().getString("backpack.messages.no-permission")));
                }
            }

        }
    }

}