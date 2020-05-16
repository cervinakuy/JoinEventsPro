package me.cervinakuy.joineventspro.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import me.cervinakuy.joineventspro.util.Config;

public class JoinLogin implements Listener {

	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		
		Player p = e.getPlayer();
			
		if (e.getResult() == PlayerLoginEvent.Result.KICK_FULL) {
				
			if (p.hasPermission("jep.server.full")) {

				e.allow();

			} else {

				e.setResult(Result.KICK_FULL);
				e.setKickMessage(Config.getString("Server.Messages.Full"));

			}
			
		}
		
		if (Config.getBoolean("Server.MOTD.Options.Maintenance")) {
			
			if (p.hasPermission("jep.server.maintenance")) {
				
				e.allow();
				
			} else {
				
				e.setResult(Result.KICK_OTHER);
				e.setKickMessage(Config.getString("Server.Messages.Maintenance"));
				
			}
			
		}
		
	}
	
}
