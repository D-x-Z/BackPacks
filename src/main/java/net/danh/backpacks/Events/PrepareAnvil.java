package net.danh.backpacks.Events;

import net.danh.backpacks.Backpacks;
import net.danh.backpacks.utils.BackPacksChecker;
import net.danh.backpacks.utils.Chat;
import net.danh.backpacks.utils.Files;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PrepareAnvil implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPrepare(@NotNull PrepareAnvilEvent e) {

        if (!BackPacksChecker.isBackpack(e.getResult())) return;

        ItemMeta im = e.getResult().getItemMeta();

        Objects.requireNonNull(im).setDisplayName(Chat.colorize(Objects.requireNonNull(Files.getconfig().getString("backpack.name.renamed")).replace("%name%", Objects.requireNonNull(e.getInventory().getRenameText()))));
        im.getPersistentDataContainer().set(new NamespacedKey(Backpacks.get(), "backpacks-custom-name"), PersistentDataType.STRING, e.getInventory().getRenameText());

        ItemStack result = e.getResult();

        result.setItemMeta(im);
        e.setResult(result);

    }

}
