package xyz.maximus.tvp.xyz.maximus.tvp.events;

import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xyz.maximus.tvp.Main;
import xyz.maximus.tvp.xyz.maximus.tvp.backend.AddPlayer;
import xyz.maximus.tvp.xyz.maximus.tvp.backend.RunParty;

/**
 * Created by Maximus on 01/05/2017.
 */
public class OnVote implements Listener{

    private Main plugin;

    public OnVote(Main pl){
        plugin = pl;
    }

    @EventHandler
    public void onVote(VotifierEvent event){
        int newVoteCount = Integer.parseInt(plugin.getConfig().getString("Vote Count")) + 1;
        plugin.getConfig().set("Vote Count", newVoteCount);
        plugin.saveConfig();

        Bukkit.getServer().broadcastMessage(color(plugin.getConfig().getString("On Vote Message")).replaceAll("%amount%", plugin.getConfig().getString("Vote Count")).replaceAll("%required%", plugin.getConfig().getString("Votes Required")));

        RunParty runParty = new RunParty(plugin);
        AddPlayer addPlayer = new AddPlayer(plugin);

        if(plugin.getConfig().getString("Require vote to receive party command").equalsIgnoreCase("True")){

            addPlayer.addPlayer(event.getVote().getUsername());

            if(plugin.getConfig().getString("Vote Count").contentEquals(plugin.getConfig().getString("Votes Required"))){
                runParty.runPartyWhoVoted();
            }
        } else {

            addPlayer.addPlayer(event.getVote().getUsername());

            if(plugin.getConfig().getString("Vote Count").contentEquals(plugin.getConfig().getString("Votes Required"))){
                runParty.runParty();

            }

        }

    }

    public String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
