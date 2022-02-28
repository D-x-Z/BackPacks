package net.danh.backpacks.Items;

import net.danh.backpacks.Backpacks;
import net.danh.backpacks.utils.Chat;
import net.danh.backpacks.utils.Files;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

public class Handler {

    public static void store(Player p, ItemStack backpack, List<ItemStack> contents) {

        if (!backpack.hasItemMeta()) return;

        ItemMeta itemMeta = backpack.getItemMeta();
        PersistentDataContainer data = Objects.requireNonNull(itemMeta).getPersistentDataContainer();

        if (!data.has(new NamespacedKey(Backpacks.get(), "backpacks"), PersistentDataType.STRING)) {
            data.set(new NamespacedKey(Backpacks.get(), "backpacks"), PersistentDataType.STRING, "");
        }

        if (contents.size() == 0) {
            data.set(new NamespacedKey(Backpacks.get(), "backpacks"), PersistentDataType.STRING, "");

            ArrayList<String> lore = new ArrayList<>();

            for (String loreLine : Files.getInstance().getconfig().getStringList("backpack.lore.empty")) {
                if (p.hasPermission("bp.1")) {
                    lore.add(Chat.colorize(loreLine.replace("%slots%", "0")).replace("%max%", Integer.toString(9)));
                } else if (p.hasPermission("bp.2")) {
                    lore.add(Chat.colorize(loreLine.replace("%slots%", "0")).replace("%max%", Integer.toString(18)));
                } else if (p.hasPermission("bp.3")) {
                    lore.add(Chat.colorize(loreLine.replace("%slots%", "0")).replace("%max%", Integer.toString(27)));
                } else if (p.hasPermission("bp.4")) {
                    lore.add(Chat.colorize(loreLine.replace("%slots%", "0")).replace("%max%", Integer.toString(36)));
                } else if (p.hasPermission("bp.5")) {
                    lore.add(Chat.colorize(loreLine.replace("%slots%", "0")).replace("%max%", Integer.toString(45)));
                } else if (p.hasPermission("bp.6")) {
                    lore.add(Chat.colorize(loreLine.replace("%slots%", "0")).replace("%max%", Integer.toString(54)));
                } else {
                    lore.add(Chat.colorize(loreLine.replace("%slots%", "0")).replace("%max%", Integer.toString(0)));
                }
            }

            itemMeta.setLore(lore);
            backpack.setItemMeta(itemMeta);

            return;
        }

        try {

            ByteArrayOutputStream io = new ByteArrayOutputStream();
            BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);

            os.writeInt(contents.size());

            for (ItemStack item : contents) {
                os.writeObject(item);
            }

            os.flush();

            byte[] rawData = io.toByteArray();

            String encodedData = Base64.getEncoder().encodeToString(rawData);

            data.set(new NamespacedKey(Backpacks.get(), "backpacks"), PersistentDataType.STRING, encodedData);

            ArrayList<String> contentsPreview = new ArrayList<>();

            int previewSize = Files.getInstance().getconfig().getInt("backpack.lore.preview-slots-size");
            int counter = 0;
            for (int i = 0; i < previewSize; i++) {
                try {
                    contents.get(i);
                } catch (Exception ignored) {
                    continue;
                }

                if (contents.get(i).getType() == Material.AIR) return;

                counter++;

                contentsPreview.add(Chat.colorize(Objects.requireNonNull(Files.getInstance().getconfig().getString("backpack.lore.contents-preview")).replace("%amount%", Integer.toString(contents.get(i).getAmount())).replace("%name%", contents.get(i).getType().toString().replaceAll("_", " ").toLowerCase())));

            }

            if (contents.size() > previewSize) {
                for (String loreLine : Files.getInstance().getconfig().getStringList("backpack.lore.preview-overflow")) {
                    contentsPreview.add(Chat.colorize(loreLine).replace("%more%", Integer.toString((contents.size() - counter))));
                }
            }

            ArrayList<String> lore = new ArrayList<>();

            int index = 0;
            for (String loreLine : Files.getInstance().getconfig().getStringList("backpack.lore.storing")) {
                if (loreLine.contains("%preview%")) {
                    lore.addAll(index, contentsPreview);
                    continue;
                }
                if (p.hasPermission("bp.1")) {
                    lore.add(Chat.colorize(loreLine.replace("%slots%", Integer.toString(contents.size()))).replace("%max%", Integer.toString(9)));
                } else if (p.hasPermission("bp.2")) {
                    lore.add(Chat.colorize(loreLine.replace("%slots%", Integer.toString(contents.size()))).replace("%max%", Integer.toString(18)));
                } else if (p.hasPermission("bp.3")) {
                    lore.add(Chat.colorize(loreLine.replace("%slots%", Integer.toString(contents.size()))).replace("%max%", Integer.toString(27)));
                } else if (p.hasPermission("bp.4")) {
                    lore.add(Chat.colorize(loreLine.replace("%slots%", Integer.toString(contents.size()))).replace("%max%", Integer.toString(36)));
                } else if (p.hasPermission("bp.5")) {
                    lore.add(Chat.colorize(loreLine.replace("%slots%", Integer.toString(contents.size()))).replace("%max%", Integer.toString(45)));
                } else if (p.hasPermission("bp.6")) {
                    lore.add(Chat.colorize(loreLine.replace("%slots%", Integer.toString(contents.size()))).replace("%max%", Integer.toString(54)));
                } else {
                    lore.add(Chat.colorize(loreLine.replace("%slots%", Integer.toString(contents.size()))).replace("%max%", Integer.toString(0)));
                }
                index++;
            }

            itemMeta.setLore(lore);
            backpack.setItemMeta(itemMeta);

            os.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static ArrayList<ItemStack> get(ItemStack backpack) {

        if (!backpack.hasItemMeta()) new ArrayList<ItemStack>();

        ItemMeta itemMeta = backpack.getItemMeta();
        PersistentDataContainer data = Objects.requireNonNull(itemMeta).getPersistentDataContainer();

        ArrayList<ItemStack> items = new ArrayList<>();

        String encodedItems = data.get(new NamespacedKey(Backpacks.get(), "backpacks"), PersistentDataType.STRING);

        if (!Objects.requireNonNull(encodedItems).isEmpty()) {

            byte[] rawData = Base64.getDecoder().decode(encodedItems);

            try {

                ByteArrayInputStream io = new ByteArrayInputStream(rawData);
                BukkitObjectInputStream in = new BukkitObjectInputStream(io);

                int count = in.readInt();

                for (int i = 0; i < count; i++) {
                    items.add((ItemStack) in.readObject());
                }

                in.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        return items;
    }

}