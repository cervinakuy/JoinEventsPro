package me.cervinakuy.joineventspro.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import me.cervinakuy.joineventspro.util.Config;

public class JoinBook implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		String joinType = (!p.hasPlayedBefore()) ? "FirstJoin" : "Join";
		
		if (Config.getBoolean(joinType + ".Book.Enabled") && p.hasPermission("jep." + joinType.toLowerCase() + ".book")) {
			
			ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
			BookMeta bookMeta = (BookMeta) book.getItemMeta();
			
			bookMeta.setTitle(Config.getString(joinType + ".Book.Information.Title"));
			bookMeta.setAuthor(Config.getString(joinType + ".Book.Information.Author"));
			bookMeta.setPages(Config.translateList(Config.getConfiguration().getStringList(joinType + ".Book.Pages")));
			
			book.setItemMeta(bookMeta);
			p.getInventory().setItem(Config.getInteger(joinType + ".Book.Information.Slot"), book);
			
		}
		
	}
	
}
