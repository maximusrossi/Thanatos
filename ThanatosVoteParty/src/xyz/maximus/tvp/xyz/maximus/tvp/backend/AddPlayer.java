package xyz.maximus.tvp.xyz.maximus.tvp.backend;

import xyz.maximus.tvp.Main;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Maximus on 01/05/2017.
 */
public class AddPlayer {

    private Main plugin;

    public AddPlayer(Main pl){
        plugin = pl;
    }

    public void addPlayer(String playerName){

        try {
            List<String> playerList = new LinkedList<String>();
            playerList.clear();

            for (String key : plugin.getConfig().getConfigurationSection("Players").getKeys(false)) { playerList.add(key); }

            int i = 0;
            for(String key : playerList){
                if(playerList.get(i).toLowerCase().contains(playerName.toLowerCase())){
                    incrementValue(playerList.get(i));
                    return;
                }
                i++;
            }
        }catch (Exception e){
            registerPlayer(playerName);
        }
        registerPlayer(playerName);
    }

    public void registerPlayer(String playerName){
        plugin.getConfig().set("Players." + playerName, 1);
        plugin.saveConfig();
    }

    public void incrementValue(String incrementName){
        int temp = Integer.parseInt(plugin.getConfig().getString("Players." + incrementName));
        temp++;

        plugin.getConfig().set("Players." + incrementName, temp);
        plugin.saveConfig();
    }
}
