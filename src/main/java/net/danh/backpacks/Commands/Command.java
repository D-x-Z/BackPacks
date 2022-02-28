package net.danh.backpacks.Commands;

import net.danh.backpacks.Items.BackPacksItems;
import net.danh.backpacks.utils.Files;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Command implements CommandExecutor {

    public final String noPermission = ChatColor.RED + "You don't have permission to do that.";
    public final String usage = ChatColor.RED + "/backpacks <get|reload|give> [player]";

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

        if (args.length == 0 || args.length > 2) {

            sender.sendMessage(usage);

            return true;
        }

        if ("get".equalsIgnoreCase(args[0])) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "You have to be a player to obtain a backpack!");
                return true;
            }

            Player p = (Player) sender;

            if (!p.hasPermission("backpacks.getcommand")) {
                p.sendMessage(noPermission);
                return true;
            }

            p.getInventory().addItem(BackPacksItems.makenewget());
        } else if ("give".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("backpacks.givecommand")) {
                sender.sendMessage(noPermission);
                return true;
            }

            if (args.length != 2) {
                sender.sendMessage(usage);
                return true;
            }

            if (Bukkit.getPlayer(args[1]) == null) {
                sender.sendMessage(ChatColor.RED + "Unknown player: " + args[1]);
                return true;
            }

            sender.sendMessage(ChatColor.GREEN + "Gave a backpack to " + args[1] + "!");

            Objects.requireNonNull(Bukkit.getPlayer(args[1])).getInventory().addItem(BackPacksItems.makenewget());
        } else if ("reload".equalsIgnoreCase(args[0])) {
            if (!sender.hasPermission("backpacks.reload")) {
                sender.sendMessage(noPermission);
                return true;
            }

            Files.getInstance().reloadconfig();

            sender.sendMessage(ChatColor.GREEN + "Backpacks has been reloaded!");
        } else {
            sender.sendMessage(usage);
        }


        return false;
    }

}