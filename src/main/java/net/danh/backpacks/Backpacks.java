package net.danh.backpacks;

import net.danh.backpacks.Commands.Command;
import net.danh.backpacks.Events.*;
import net.danh.backpacks.Items.BackPacksItems;
import net.danh.backpacks.utils.Files;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Objects;

public class Backpacks extends JavaPlugin {

    private static Backpacks main;

    public static Backpacks get() {
        return main;
    }

    public void onEnable() {

        main = this;

        Objects.requireNonNull(getCommand("backpacks")).setExecutor(new Command());

        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new InventoryClose(), this);
        getServer().getPluginManager().registerEvents(new BlockPlace(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new PrepareAnvil(), this);
        getServer().getPluginManager().registerEvents(new PrepareItemCraft(), this);

        Files.getInstance().createconfig();
        createRecipe();

    }

    public void onDisable() {
        Files.getInstance().saveconfig();
    }

    public void createRecipe() {

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "backpacks"), BackPacksItems.makenewget());

        recipe.shape(Objects.requireNonNull(Files.getInstance().getconfig().getString("backpack.recipe.shape.top")), Objects.requireNonNull(Files.getInstance().getconfig().getString("backpack.recipe.shape.mid")), Objects.requireNonNull(Files.getInstance().getconfig().getString("backpack.recipe.shape.btm")));

        for (String ingredientKey : Objects.requireNonNull(Files.getInstance().getconfig().getConfigurationSection("backpack.recipe.key")).getKeys(false)) {

            ArrayList<Material> choices = new ArrayList<>();
            for (String choice : Files.getInstance().getconfig().getStringList("backpack.recipe.key." + ingredientKey)) {
                choices.add(Material.valueOf(choice));
            }

            recipe.setIngredient(ingredientKey.charAt(0), new RecipeChoice.MaterialChoice(choices));

        }

        Bukkit.addRecipe(recipe);

    }

}