package me.cervinakuy.joineventspro.listener;

import me.cervinakuy.joineventspro.Game;
import me.cervinakuy.joineventspro.util.DebugMode;
import me.cervinakuy.joineventspro.util.Resource;
import me.cervinakuy.joineventspro.util.Resources;
import me.cervinakuy.joineventspro.util.Toolkit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinSound implements Listener {

	private final Resources resources;
	private final Resource config;
	private final DebugMode debug;

	public JoinSound(Game plugin) {
		this.resources = plugin.getResources();
		this.config = plugin.getResources().getConfig();
		this.debug = plugin.getDebugMode();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		String joinType = Toolkit.getJoinType(p, debug);
		Resource joinConfig = resources.getResourceByName(joinType);
		String pathPrefix = joinType + ".Sound";
		
		if (joinConfig.getBoolean(pathPrefix + ".Enabled") &&
				p.hasPermission("jep." + joinType.toLowerCase() + ".sound")) {
			for (Player all : Bukkit.getOnlinePlayers()) {
				Toolkit.playSoundToPlayer(all, joinConfig.fetchString(pathPrefix + ".Sound"),
						joinConfig.getInt(pathPrefix + ".Pitch"));
			}
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		if (config.getBoolean("Leave.Sound.Enabled") && p.hasPermission("jep.leave.sound")) {
			for (Player all : Bukkit.getOnlinePlayers()) {
				Toolkit.playSoundToPlayer(all, config.fetchString("Leave.Sound.Sound"),
						config.getInt("Leave.Sound.Pitch"));
			}
		}
	}

}
