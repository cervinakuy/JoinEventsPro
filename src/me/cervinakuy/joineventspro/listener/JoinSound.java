package me.cervinakuy.joineventspro.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.cervinakuy.joineventspro.util.Config;
import me.cervinakuy.joineventspro.util.Sounds;

public class JoinSound implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		String joinType = (!p.hasPlayedBefore()) ? "FirstJoin" : "Join";
		
		if (Config.getBoolean(joinType + ".Sound.Enabled") && p.hasPermission("jep." + joinType.toLowerCase() + ".sound")) {
			
			for (Player all : Bukkit.getOnlinePlayers()) {
				
				all.playSound(p.getLocation(), Sounds.valueOf(Config.getString(joinType + ".Sound.Sound")).bukkitSound(), 1, Config.getInteger(joinType + ".Sound.Pitch"));
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		
		if (Config.getBoolean("Leave.Sound.Enabled") && p.hasPermission("jep.leave.sound")) {
			
			for (Player all : Bukkit.getOnlinePlayers()) {
				
				all.playSound(p.getLocation(), Sounds.valueOf(Config.getString("Leave.Sound.Sound")).bukkitSound(), 1, Config.getInteger("Leave.Sound.Pitch"));
				
			}
			
		}
		
	}

}
