package me.cervinakuy.joineventspro.listener;

import com.cryptomorin.xseries.XSound;
import me.cervinakuy.joineventspro.Game;
import me.cervinakuy.joineventspro.util.DebugMode;
import me.cervinakuy.joineventspro.util.Resource;
import me.cervinakuy.joineventspro.util.Resources;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinSound implements Listener {

	private Resources resources;
	private Resource config;
	private DebugMode debug;

	public JoinSound(Game plugin) {
		this.resources = plugin.getResources();
		this.config = plugin.getResources().getConfig();
		this.debug = plugin.getDebugMode();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		String joinType = (!p.hasPlayedBefore() || debug.isDebugUser(p.getName())) ? "FirstJoin" : "Join";
		Resource joinConfig = resources.getResourceByName(joinType);
		String pathPrefix = joinType + ".Sound";
		
		if (joinConfig.getBoolean(pathPrefix + ".Enabled") && p.hasPermission("jep." + joinType.toLowerCase() + ".sound")) {
			
			for (Player all : Bukkit.getOnlinePlayers()) {

				XSound.play(all, joinConfig.getString(pathPrefix + ".Sound") + ", 1, " + joinConfig.getInt(pathPrefix + ".Pitch"));
			}
			
		}
		
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();

		if (config.getBoolean("Leave.Sound.Enabled") && p.hasPermission("jep.leave.sound")) {
			
			for (Player all : Bukkit.getOnlinePlayers()) {

				XSound.play(all, config.getString("Leave.Sound.Sound") + ", 1, " + config.getInt("Leave.Sound.Pitch"));

			}
			
		}
		
	}

}
