package me.cervinakuy.joineventspro.listener;

import me.cervinakuy.joineventspro.Game;
import me.cervinakuy.joineventspro.util.Resource;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class JoinLogin implements Listener {

	private Resource config;

	public JoinLogin(Game plugin) {
		this.config = plugin.getResources().getConfig();
	}

	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		
		Player p = e.getPlayer();
			
		if (e.getResult() == PlayerLoginEvent.Result.KICK_FULL) {
				
			if (p.hasPermission("jep.server.full")) {

				e.allow();

			} else {

				e.setResult(Result.KICK_FULL);
				e.setKickMessage(config.getString("Server.Messages.Full"));

			}
			
		}
		
		if (config.getBoolean("Server.MOTD.Options.Maintenance")) {
			
			if (p.hasPermission("jep.server.maintenance")) {
				
				e.allow();
				
			} else {
				
				e.setResult(Result.KICK_OTHER);
				e.setKickMessage(config.getString("Server.Messages.Maintenance"));
				
			}
			
		}
		
	}
	
}
