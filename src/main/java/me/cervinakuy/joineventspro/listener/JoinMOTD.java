package me.cervinakuy.joineventspro.listener;

import me.cervinakuy.joineventspro.Game;
import me.cervinakuy.joineventspro.util.DebugMode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.cervinakuy.joineventspro.util.Config;
import me.clip.placeholderapi.PlaceholderAPI;

public class JoinMOTD implements Listener {

	private DebugMode debug;

	public JoinMOTD(Game plugin) {
		this.debug = plugin.getDebugMode();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		
		String joinType = (!p.hasPlayedBefore() || debug.isDebugUser(p.getName())) ? "FirstJoin" : "Join";
		
		if (Config.getBoolean(joinType + ".MOTD.Enabled") && p.hasPermission("jep." + joinType.toLowerCase() + ".motd")) {
			
			if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
				
				for (String lines : Config.getConfiguration().getStringList(joinType + ".MOTD.Lines")) {
					
					p.sendMessage(Config.translate(PlaceholderAPI.setPlaceholders(p, lines.replace("%player%", p.getName()))));
					
				}
				
			} else {
				
				for (String lines : Config.getConfiguration().getStringList(joinType + ".MOTD.Lines")) {
					
					p.sendMessage(Config.translate(lines.replace("%player%", p.getName())));
					
				}
				
			}

		}
		
	}
	
}
