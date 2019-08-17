package me.cervinakuy.joineventspro.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.cervinakuy.joineventspro.Game;
import me.cervinakuy.joineventspro.util.Config;

public class JoinNotification implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		
		if (Game.getInstance().needsUpdate()) {
			
			if (p.isOp()) {
				
				p.sendMessage(Config.translate("&7[&b&lJOINEVENTSPRO&7] &aAn update was found: v" + Game.getInstance().getUpdateVersion() + " https://www.spigotmc.org/resources/22105/"));
				
			}
			
		}
		
	}
	
}
