package me.cervinakuy.joineventspro.listener;

import com.cryptomorin.xseries.XMaterial;
import me.cervinakuy.joineventspro.Game;
import me.cervinakuy.joineventspro.util.DebugMode;
import me.cervinakuy.joineventspro.util.Resource;
import me.cervinakuy.joineventspro.util.Resources;
import me.cervinakuy.joineventspro.util.Toolkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class JoinBook implements Listener {

	private final Resources resources;
	private final DebugMode debug;

	public JoinBook(Game plugin) {
		this.resources = plugin.getResources();
		this.debug = plugin.getDebugMode();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		String joinType = (!p.hasPlayedBefore() || debug.isDebugUser(p.getName())) ? "FirstJoin" : "Join";
		Resource joinConfig = resources.getResourceByName(joinType);
		String pathPrefix = joinType + ".Book";

		if (joinConfig.getBoolean(pathPrefix + ".Enabled") && p.hasPermission("jep." + joinType.toLowerCase() + ".book")) {
			
			ItemStack book = XMaterial.WRITTEN_BOOK.parseItem();
			BookMeta bookMeta = (BookMeta) book.getItemMeta();
			int bookSlot = joinConfig.getInt(pathPrefix + ".Information.Slot");

			bookMeta.setTitle(joinConfig.getString(pathPrefix + ".Information.Title"));
			bookMeta.setAuthor(joinConfig.getString(pathPrefix + ".Information.Author"));
			bookMeta.setPages(Toolkit.replaceInList(joinConfig.getStringList(pathPrefix + ".Pages"), "%newline%", "\n"));
			
			book.setItemMeta(bookMeta);
			p.getInventory().setItem(bookSlot, book);
			
		}
		
	}
	
}
