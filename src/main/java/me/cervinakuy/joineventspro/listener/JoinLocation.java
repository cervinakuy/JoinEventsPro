package me.cervinakuy.joineventspro.listener;

import me.cervinakuy.joineventspro.Game;
import me.cervinakuy.joineventspro.util.DebugMode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.cervinakuy.joineventspro.util.Config;

public class JoinLocation implements Listener {

	private DebugMode debug;

	public JoinLocation(Game plugin) {
		this.debug = plugin.getDebugMode();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		String joinType = (!p.hasPlayedBefore() || debug.isDebugUser(p.getName())) ? "FirstJoin" : "Join";
		
		if (Config.getBoolean("Spawn." + joinType + ".Enabled")) {
			
			if (Config.getConfiguration().contains("Spawn." + joinType + ".World")) {

				if (p.hasPermission("jep." + joinType.toLowerCase() + ".location")) {

					Location spawn = new Location(Bukkit.getWorld(Config.getString("Spawn." + joinType + ".World")),
							Config.getInteger("Spawn." + joinType + ".X") + 0.5,
							Config.getInteger("Spawn." + joinType + ".Y"),
							Config.getInteger("Spawn." + joinType + ".Z") + 0.5,
							(float) Config.getDouble("Spawn." + joinType + ".Yaw"),
							(float) Config.getDouble("Spawn." + joinType + ".Pitch"));

					p.teleport(spawn);

				}
				
			} else {
			
				p.sendMessage(Config.getString("Messages.Error.Spawn").replace("%type%", joinType.equals("FirstJoin") ? "First Join" : "Join"));
				
			}
			
		}
		
	}
	
}
