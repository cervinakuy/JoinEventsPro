package me.cervinakuy.joineventspro.listener;

import me.cervinakuy.joineventspro.Game;
import me.cervinakuy.joineventspro.util.DebugMode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.cervinakuy.joineventspro.util.Config;
import me.cervinakuy.joineventspro.util.XSound;

public class JoinSound implements Listener {

	private DebugMode debug;

	public JoinSound(Game plugin) {
		this.debug = plugin.getDebugMode();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		String joinType = (!p.hasPlayedBefore() || debug.isDebugUser(p.getName())) ? "FirstJoin" : "Join";
		
		if (Config.getBoolean(joinType + ".Sound.Enabled") && p.hasPermission("jep." + joinType.toLowerCase() + ".sound")) {
			
			for (Player all : Bukkit.getOnlinePlayers()) {
				
				all.playSound(p.getLocation(), XSound.matchXSound(Config.getString(joinType + ".Sound.Sound")).get().parseSound(), 1, Config.getInteger(joinType + ".Sound.Pitch"));
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		
		if (Config.getBoolean("Leave.Sound.Enabled") && p.hasPermission("jep.leave.sound")) {
			
			for (Player all : Bukkit.getOnlinePlayers()) {
				
				all.playSound(p.getLocation(), XSound.matchXSound(Config.getString("Leave.Sound.Sound")).get().parseSound(), 1, Config.getInteger("Leave.Sound.Pitch"));
				
			}
			
		}
		
	}

}
