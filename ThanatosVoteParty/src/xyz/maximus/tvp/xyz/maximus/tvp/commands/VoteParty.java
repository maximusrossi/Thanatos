package xyz.maximus.tvp.xyz.maximus.tvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import xyz.maximus.tvp.Main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Maximus on 06/05/2017.
 */
public class VoteParty implements CommandExecutor{

    private Main plugin;

    public VoteParty(Main pl){
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }

        if (!(sender.hasPermission("thanatosvoteparty.gui"))) {
            sender.sendMessage(color(plugin.getConfig().getString("No Permission")));
            return false;
        }
        String requiredStat = plugin.getConfig().getString("Votes Required");
        String currentStat = plugin.getConfig().getString("Vote Count");

        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.YELLOW + "Vote Party " + currentStat + "/" + requiredStat);

        List<String> playerList = new LinkedList<String>();
        playerList.clear();

        try {

        for (String key : plugin.getConfig().getConfigurationSection("Players").getKeys(false)) {
            playerList.add(key);
        }
            for (int count = 0; count < playerList.size(); count++)
            {
                ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
                SkullMeta im = (SkullMeta) itemStack.getItemMeta();
                im.setOwner(playerList.get(count));
                im.setDisplayName(color("&e" + playerList.get(count) + " has voted!"));

                ArrayList<String> lore = new ArrayList<String>();
                lore.add(color("&c&o" + plugin.getConfig().getString("Players." + playerList.get(count)) + " votes"));
                im.setLore(lore);
                itemStack.setItemMeta(im);
                inventory.setItem(count + 9, itemStack);
            }

        }catch(Exception e){
            sender.sendMessage(color(plugin.getConfig().getString("No Votes")));
        }

        for(int count = 0; count < 45 - playerList.size(); count ++){
            ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta im = (SkullMeta) itemStack.getItemMeta();
            im.setOwner("MHF_Question");
            im.setDisplayName(color("&4You need more votes!"));
            itemStack.setItemMeta(im);
            inventory.setItem(count + playerList.size() + 9, itemStack);
        }


        ItemStack blockGold = nameItem(Material.GOLD_BLOCK, color("&e&lThanatos Vote Party!"));
        ArrayList<String> lore = new ArrayList<String>();
        ItemMeta itemBlock = blockGold.getItemMeta();
        lore.add(color("&e&oGet " + requiredStat + "/" + requiredStat + " for an op vote party!"));
        itemBlock.setLore(lore);
        blockGold.setItemMeta(itemBlock);

        for(int i = 0; i < 9; i++){
            inventory.setItem(i, blockGold);
        }

        ((Player) sender).openInventory(inventory);

        return false;
    }

    public String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    private ItemStack nameItem(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);

        item.setItemMeta(meta);
        return item;
    }

    private ItemStack nameItem(Material item, String name) {
        return nameItem(new ItemStack(item), name);
    }

}
