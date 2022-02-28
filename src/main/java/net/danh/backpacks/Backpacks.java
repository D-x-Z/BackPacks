package net.danh.backpacks;

import net.danh.backpacks.Commands.Command;
import net.danh.backpacks.Events.*;
import net.danh.backpacks.Items.BackPacksItems;
import net.danh.backpacks.utils.Files;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import preponderous.ponder.minecraft.bukkit.abs.PonderBukkitPlugin;
import preponderous.ponder.minecraft.bukkit.nms.NMSAssistant;
import preponderous.ponder.minecraft.bukkit.tools.EventHandlerRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;

public class Backpacks extends PonderBukkitPlugin implements Listener {

    private static Backpacks main;

    public static Backpacks get() {
        return main;
    }

    public void onEnable() {

        main = this;
        performNMSChecks();
        Objects.requireNonNull(getCommand("backpacks")).setExecutor(new Command());
        registerEventHandlers();
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


    private void performNMSChecks() {
        final NMSAssistant nmsAssistant = new NMSAssistant();
        if (nmsAssistant.isVersionGreaterThan(13)) {
            getLogger().log(Level.INFO, "Loading data matching server version " + nmsAssistant.getNMSVersion().toString());
        } else {
            getLogger().warning("The server version is not suitable to load the plugin");
            getLogger().warning("Support version 1.14.x - 1.18.x");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
        }
    }

    private ArrayList<Listener> initializeListeners() {
        return new ArrayList<>(Arrays.asList(
                new BlockPlace(),
                new InventoryClick(),
                new InventoryClose(),
                new PlayerInteract(),
                new PrepareAnvil(),
                new PrepareItemCraft()
        ));
    }

    /**
     * Registers the event handlers of the plugin using Ponder.
     */
    private void registerEventHandlers() {
        ArrayList<Listener> listeners = initializeListeners();
        EventHandlerRegistry eventHandlerRegistry = new EventHandlerRegistry();
        eventHandlerRegistry.registerEventHandlers(listeners, this);
    }
}