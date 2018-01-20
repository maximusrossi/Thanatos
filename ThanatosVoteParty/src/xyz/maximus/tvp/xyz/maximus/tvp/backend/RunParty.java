package xyz.maximus.tvp.xyz.maximus.tvp.backend;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.maximus.tvp.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Maximus on 02/05/2017.
 */
public class RunParty {

    private Main plugin;

    public RunParty(Main pl){
        plugin = pl;
    }

    public void runParty(){

        ArrayList<String> Randomlist = new ArrayList<>();
        Randomlist.clear();

        List<Player> playerList = new LinkedList<Player>();
        playerList.clear();
        playerList = (List<Player>) Bukkit.getServer().getOnlinePlayers();

        for (String key : plugin.getConfig().getStringList("Party Commands (Random Run)")) {
            Randomlist.add(key);
        }

        Collections.shuffle(Randomlist);

        if(Randomlist.get(0).contains("asa") || Randomlist.get(0).contains("scrates")){
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), Randomlist.get(0));
        } else {
            for(int i = 0; i < playerList.size(); i++){
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), Randomlist.get(0).replaceAll("%player%", playerList.get(i).getName().toString()));
            }
        }

        ArrayList<String> AlwaysList = new ArrayList<>();
        AlwaysList.clear();

        for (String key : plugin.getConfig().getStringList("Party Commands (Always Run)")) {
            AlwaysList.add(key);

        }

        for(int i = 0; i < playerList.size(); i++){
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), AlwaysList.get(0).replaceAll("%player%", playerList.get(i).getName().toString()));
        }


        ArrayList<String> messageList = new ArrayList<>();
        messageList.clear();

        for (String key : plugin.getConfig().getStringList("Vote Party (All)")) {
            messageList.add(key);
        }

        for(int i = 0; i < messageList.size(); i++){
            Bukkit.getServer().broadcastMessage(color(messageList.get(i)).replaceAll("%required%", plugin.getConfig().getString("Votes Required")));
        }

        resetPlayers();
    }

    public void runPartyWhoVoted(){

        ArrayList<String> list = new ArrayList<>();
        list.clear();

        List<String> playerList = new LinkedList<String>();
        playerList.clear();

        for (String key : plugin.getConfig().getConfigurationSection("Players").getKeys(false)) {
            playerList.add(key);
        }

        for (String key : plugin.getConfig().getStringList("Party Commands")) {
            list.add(key);
        }

        Collections.shuffle(list);

        List<Player> onlinePlayers = new LinkedList<Player>();
        onlinePlayers.clear();
        onlinePlayers = (List<Player>) Bukkit.getServer().getOnlinePlayers();

        for(int i = 0; i < playerList.size(); i++){
                if(onlinePlayers.toString().contains(playerList.get(i))) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), list.get(0).replaceAll("%player%", playerList.get(i)));
                }
        }

        ArrayList<String> messageList = new ArrayList<>();
        messageList.clear();

        for (String key : plugin.getConfig().getStringList("Vote Party (Who Voted Only)")) {
            messageList.add(key);
        }

        for(int i = 0; i < messageList.size(); i++){
            Bukkit.getServer().broadcastMessage(color(messageList.get(i)).replaceAll("%required%", plugin.getConfig().getString("Votes Required")));
        }

        resetPlayers();

    }

    public void resetPlayers(){
        plugin.getConfig().set("Vote Count", "0");
        plugin.getConfig().set("Players", null);
        plugin.saveConfig();
    }

    public String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}
