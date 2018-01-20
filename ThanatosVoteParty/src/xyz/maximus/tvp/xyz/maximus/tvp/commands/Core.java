package xyz.maximus.tvp.xyz.maximus.tvp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.maximus.tvp.Main;

/**
 * Created by Maximus on 01/05/2017.
 */
public class Core implements CommandExecutor {

    private Main plugin;

    public Core(Main pl){
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if(args.length == 0 || args.length > 2){
            return false;
        }

        if(args[0].equalsIgnoreCase("reset")){
            if(!(sender.hasPermission("thanatosvoteparty.admin"))){
                sender.sendMessage(color(plugin.getConfig().getString("No Permission")));
                return true;
            }
            plugin.getConfig().set("Vote Count", "0");
            plugin.getConfig().set("Players", null);
            plugin.saveConfig();

            sender.sendMessage(color(plugin.getConfig().getString("Reset Votes Message")));
        }

        if(args[0].equalsIgnoreCase("reload")){

            if(!(sender.hasPermission("thanatosvoteparty.admin"))){
                sender.sendMessage(color(plugin.getConfig().getString("No Permission")));
                return true;
            }

           sender.sendMessage(color(plugin.getConfig().getString("Reload Message")));
           plugin.reloadConfig();
           plugin.saveConfig();
           return true;
        }

        if(args[0].equalsIgnoreCase("set")){
            if(!(sender.hasPermission("thanatosvoteparty.admin"))){
                sender.sendMessage(color(plugin.getConfig().getString("No Permission")));
                return true;
            }
            try {
                Integer.parseInt(args[1]);

                plugin.getConfig().set("Votes Required", args[1]);
                plugin.saveConfig();

                sender.sendMessage(color(plugin.getConfig().getString("Vote Count Set")).replaceAll("%count%", args[1]));

            } catch (NumberFormatException nfe) {
                sender.sendMessage(color("&4Enter a number"));
                return false;
            }

        }

        return false;
    }

    public String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
