package me.cervinakuy.joineventspro.listener;

import me.cervinakuy.joineventspro.Game;
import me.cervinakuy.joineventspro.util.DebugMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.cervinakuy.joineventspro.util.Config;
import me.cervinakuy.joineventspro.util.Toolkit;

public class JoinCommands implements Listener {

	private DebugMode debug;

	public JoinCommands(Game plugin) {
		this.debug = plugin.getDebugMode();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		String joinType = (!p.hasPlayedBefore() || debug.isDebugUser(p.getName())) ? "FirstJoin" : "Join";
		
		if (Config.getBoolean(joinType + ".Commands.Enabled") && p.hasPermission("jep." + joinType.toLowerCase() + ".commands")) {
			
			Toolkit.runCommands(p, joinType + ".Commands");
			
		}
		
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		
		if (Config.getBoolean("Leave.Commands.Enabled") && p.hasPermission("jep.leave.commands")) {
			
			Toolkit.runCommands(p, "Leave.Commands");
			
		}
		
	}
	
}
