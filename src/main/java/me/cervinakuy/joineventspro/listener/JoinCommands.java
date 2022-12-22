package me.cervinakuy.joineventspro.listener;

import me.cervinakuy.joineventspro.Game;
import me.cervinakuy.joineventspro.util.DebugMode;
import me.cervinakuy.joineventspro.util.Resource;
import me.cervinakuy.joineventspro.util.Resources;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.cervinakuy.joineventspro.util.Toolkit;

import java.util.List;

public class JoinCommands implements Listener {

	private final Resources resources;
	private final DebugMode debug;

	public JoinCommands(Game plugin) {
		this.resources = plugin.getResources();
		this.debug = plugin.getDebugMode();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		String joinType = Toolkit.getJoinType(p, debug);
		Resource joinConfig = resources.getResourceByName(joinType);

		if (p.hasPermission("jep." + joinType.toLowerCase() + ".commands")) {
			Toolkit.runCommands(p, joinConfig.getStringList(joinType + ".Commands"));
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		if (p.hasPermission("jep.leave.commands")) {
			Toolkit.runCommands(p, resources.getConfig().getStringList("Leave.Commands"));
		}
	}
	
}
