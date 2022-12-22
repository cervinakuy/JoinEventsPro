package me.cervinakuy.joineventspro.listener;

import me.cervinakuy.joineventspro.Game;
import me.cervinakuy.joineventspro.util.DebugMode;
import me.cervinakuy.joineventspro.util.Resource;
import me.cervinakuy.joineventspro.util.Resources;
import me.cervinakuy.joineventspro.util.Toolkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinMessage implements Listener {

	private final Resources resources;
	private final Resource config;
	private final DebugMode debug;

	public JoinMessage(Game plugin) {
		this.resources = plugin.getResources();
		this.config = plugin.getResources().getConfig();
		this.debug = plugin.getDebugMode();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		String joinType = Toolkit.getJoinType(p, debug);
		Resource joinConfig = resources.getResourceByName(joinType);
		String pathPrefix = joinType + ".Message";

		if (joinConfig.getBoolean(pathPrefix + ".Enabled") &&
				p.hasPermission("jep." + joinType.toLowerCase() + ".message")) {
			String joinMessageWithPlaceholders = Toolkit.addPlaceholdersIfPossible(p,
					joinConfig.fetchString(pathPrefix + ".Message").replace("%player%", p.getName()));
			e.setJoinMessage(joinMessageWithPlaceholders);
		} else {
			e.setJoinMessage("");
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (config.getBoolean("Leave.Message.Enabled") && p.hasPermission("jep.leave.message")) {
			String leaveMessageWithPlaceholders = Toolkit.addPlaceholdersIfPossible(p,
					config.fetchString("Leave.Message.Message").replace("%player%", p.getName()));
			e.setQuitMessage(leaveMessageWithPlaceholders);
		} else {
			e.setQuitMessage("");
		}
	}
	
}
