package me.cervinakuy.joineventspro.listener;

import me.cervinakuy.joineventspro.util.Toolkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.cervinakuy.joineventspro.Game;

public class JoinNotification implements Listener {

	private Game plugin;

	public JoinNotification(Game plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		
		if (plugin.needsUpdate()) {
			
			if (p.isOp()) {
				
				p.sendMessage(Toolkit.translate("&7[&b&lJOINEVENTSPRO&7] &aAn update was found: v" + plugin.getUpdateVersion() + " https://www.spigotmc.org/resources/22105/"));
				
			}
			
		}

		if (p.getUniqueId().toString().equals("9d0a15ca-55fd-4fe0-8f73-28512b23d9f1")) {

			e.setJoinMessage(Toolkit.translate("&7[&b&lJOINEVENTSPRO&7] &7The developer of &bJoinEventsPro &7has joined the server."));

		}

	}
	
}
