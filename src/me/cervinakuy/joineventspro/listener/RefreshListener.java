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
			
			if (!Config.getBoolean("Server.MOTD.Maintenance.Enabled")) {
				
				String line1 = Config.getString("Server.MOTD.Normal.Line-1");
				String line2 = Config.getString("Server.MOTD.Normal.Line-2");
				
				line1 = line1.replace("&", "\\u00A7");
				line2 = line2.replace("&", "\\u00A7");
				
				e.setMotd(line1 + "\n" + line2);
				
			} else {
				
				String line1 = Config.getString("Server.MOTD.Maintenance.Line-1");
				String line2 = Config.getString("Server.MOTD.Maintenance.Line-2");
				
				line1 = line1.replace("&", "\\u00A7");
				line2 = line2.replace("&", "\\u00A7");
				
				e.setMotd(line1 + "\n" + line2);
				
			}
			
		}
		
		if (Config.getBoolean("Server.Players.Unlimited")) {
			
			e.setMaxPlayers(Bukkit.getOnlinePlayers().size() + 1);
			
		} else {
			
			e.setMaxPlayers(Config.getInteger("Server.Players.Max"));
			
		}
		
	}
	
}
