package net.danh.backpacks.utils;

import net.danh.backpacks.Backpacks;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BackPacksChecker {

    public static boolean isBackpack(ItemStack is) {

        if (is == null) return false;
        if (is.getType() == Material.AIR) return false;

        return hasKey(is, "backpacks", PersistentDataType.STRING);
    }

    public static boolean isnewBackpack(ItemStack is) {

        if (is == null) return false;
        if (is.getType() == Material.AIR) return false;

        return hasKey(is, "backpacks-new", PersistentDataType.STRING);
    }

    public static boolean hasKey(@NotNull ItemStack is, String targetKey, PersistentDataType<String, String> type) {
        NamespacedKey key = new NamespacedKey(Backpacks.get(), targetKey);
        ItemMeta itemMeta = is.getItemMeta();

        PersistentDataContainer container = Objects.requireNonNull(itemMeta).getPersistentDataContainer();

        boolean foundKey = false;

        if (container.has(key, type)) {
            foundKey = container.get(key, type) != null;
        }

        return foundKey;
    }

}
