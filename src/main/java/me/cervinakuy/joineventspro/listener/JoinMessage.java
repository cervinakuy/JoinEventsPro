package me.cervinakuy.joineventspro.listener;

import me.cervinakuy.joineventspro.Game;
import me.cervinakuy.joineventspro.util.DebugMode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.cervinakuy.joineventspro.util.Config;
import me.clip.placeholderapi.PlaceholderAPI;

public class JoinMessage implements Listener {

	private DebugMode debug;

	public JoinMessage(Game plugin) {
		this.debug = plugin.getDebugMode();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		String joinType = (!p.hasPlayedBefore() || debug.isDebugUser(p.getName())) ? "FirstJoin" : "Join";
		
		if (Config.getBoolean(joinType + ".Message.Enabled") && p.hasPermission("jep." + joinType.toLowerCase() + ".message")) {
			
			if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
				
				String withPlaceholders = PlaceholderAPI.setPlaceholders(p, Config.getString(joinType + ".Message.Message").replace("%player%", p.getName()));
				e.setJoinMessage(withPlaceholders);
				
			} else {
				
				e.setJoinMessage(Config.getString(joinType + ".Message.Message").replace("%player%", p.getName()));
				
			}
			
		} else {
			
			e.setJoinMessage("");
			
		}
		
		if (e.getPlayer().getUniqueId().toString().equals("9d0a15ca-55fd-4fe0-8f73-28512b23d9f1")) {
			
			e.setJoinMessage(Config.translate("&7[&b&lJOINEVENTSPRO&7] &7The developer of &bJoinEventsPro &7has joined the server."));
			
		}
		
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		
		if (Config.getBoolean("Leave.Message.Enabled") && p.hasPermission("jep.leave.message")) {
			
			if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
				
				String withPlaceholders = PlaceholderAPI.setPlaceholders(p, Config.getString("Leave.Message.Message").replace("%player%", p.getName()));
				e.setQuitMessage(withPlaceholders);
				
			} else {
				
				e.setQuitMessage(Config.getString("Leave.Message.Message").replace("%player%", p.getName()));
				
			}
			
		} else {
			
			e.setQuitMessage("");
			
		}
		
	}
	
}
