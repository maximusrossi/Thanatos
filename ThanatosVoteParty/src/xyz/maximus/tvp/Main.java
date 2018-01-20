package xyz.maximus.tvp;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.maximus.tvp.xyz.maximus.tvp.commands.Core;
import xyz.maximus.tvp.xyz.maximus.tvp.commands.VoteParty;
import xyz.maximus.tvp.xyz.maximus.tvp.events.OnVote;
import xyz.maximus.tvp.xyz.maximus.tvp.events.onInventoryClick;

import java.util.logging.Logger;

/**
 * Created by Maximus on 01/05/2017.
 */
public class Main extends JavaPlugin{

    public void onEnable(){
        PluginDescriptionFile pdf = getDescription();
        Logger logger = getLogger();
        logger.info(pdf.getName() + " has been enabled! (v" + pdf.getVersion() + ")!");

        registerCommands();
        registerEvents();
        registerConfig();
    }

    public void onDisable(){
        PluginDescriptionFile pdf = getDescription();
        Logger logger = getLogger();
        logger.info(pdf.getName() + " has been disabled! (v" + pdf.getVersion() + ")!");
    }

    public void registerCommands(){
        getCommand("thanatosvoteparty").setExecutor(new Core(this));
        getCommand("voteparty").setExecutor(new VoteParty(this));
    }

    public void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new OnVote(this), this);
        pm.registerEvents(new onInventoryClick(), this);
    }

    public void registerConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
