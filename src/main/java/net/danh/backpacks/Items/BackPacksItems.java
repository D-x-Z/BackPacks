package net.danh.backpacks.Items;

import net.danh.backpacks.Backpacks;
import net.danh.backpacks.utils.Chat;
import net.danh.backpacks.utils.Files;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class BackPacksItems {

    public static ItemStack makenewget() {

        ItemStack backpack = new ItemStack(Material.valueOf(Files.getInstance().getconfig().getString("backpack.material")));
        ItemMeta itemMeta = backpack.getItemMeta();

        Objects.requireNonNull(itemMeta).getPersistentDataContainer().set(new NamespacedKey(Backpacks.get(), "backpacks-new"), PersistentDataType.STRING, "");
        itemMeta.setDisplayName(Chat.colorize(Files.getInstance().getconfig().getString("backpack.name.new")));

        ArrayList<String> lore = new ArrayList<>();

        for (String loreLine : Files.getInstance().getconfig().getStringList("backpack.lore.new")) {
            lore.add(Chat.colorize(loreLine));
        }

        itemMeta.setLore(lore);
        backpack.setItemMeta(itemMeta);

        return backpack;
    }

    public static ItemStack makeNew() {

        ItemStack backpack = new ItemStack(Material.valueOf(Files.getInstance().getconfig().getString("backpack.material")));
        ItemMeta itemMeta = backpack.getItemMeta();

        Objects.requireNonNull(itemMeta).getPersistentDataContainer().set(new NamespacedKey(Backpacks.get(), "backpacks"), PersistentDataType.STRING, "");
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(Backpacks.get(), UUID.randomUUID().toString()), PersistentDataType.STRING, "");

        itemMeta.setDisplayName(Chat.colorize(Files.getInstance().getconfig().getString("backpack.name.regular")));

        ArrayList<String> lore = new ArrayList<>();

        for (String loreLine : Files.getInstance().getconfig().getStringList("backpack.lore.empty")) {
            lore.add(Chat.colorize(loreLine.replace("%slots%", "0")).replace("%max%", Integer.toString(Files.getInstance().getconfig().getInt("backpack.rows") * 9)));
        }

        itemMeta.setLore(lore);
        backpack.setItemMeta(itemMeta);

        return backpack;
    }

}