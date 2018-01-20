package xyz.maximus.tvp.xyz.maximus.tvp.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Created by Maximus on 06/05/2017.
 */
public class onInventoryClick implements Listener {

    @EventHandler
    public void InventoryClick(InventoryClickEvent event){

        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();

        if(inv.getTitle().contains(ChatColor.YELLOW + "Vote Party")){
            try {

                event.setCancelled(true);
                player.closeInventory();

            }catch(Exception e){
                // catch errors
            }
        }else{
           //do nothing
            return;
        }


    }

}
