package me.cervinakuy.joineventspro.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import me.cervinakuy.joineventspro.util.Config;

public class RefreshListener implements Listener {

	@EventHandler
	public void onPing(ServerListPingEvent e) {
		
		if (Config.getBoolean("Server.MOTD.Normal.Enabled")) {
			
			String type = Config.getBoolean("Server.MOTD.Maintenance.Enabled") ? "Maintenance" : "Normal";
			
			String line1 = Config.getString("Server.MOTD." + type + ".Line-1");
			String line2 = Config.getString("Server.MOTD." + type + ".Line-2");
			
			e.setMotd((line1 + "\n" + line2).replace("&", "\\u00A7"));
			
		}
		
		e.setMaxPlayers(Config.getBoolean("Server.Players.Unlimited") ? (Bukkit.getOnlinePlayers().size() + 1) : Config.getInteger("Server.Players.Max"));
		
	}
	
}
