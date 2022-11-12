package me.cervinakuy.joineventspro.listener;

import me.cervinakuy.joineventspro.Game;
import me.cervinakuy.joineventspro.util.DebugMode;
import me.cervinakuy.joineventspro.util.Resource;
import me.cervinakuy.joineventspro.util.Resources;
import me.cervinakuy.joineventspro.util.Toolkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinLocation implements Listener {

	private final Resources resources;
	private final DebugMode debug;

	public JoinLocation(Game plugin) {
		this.resources = plugin.getResources();
		this.debug = plugin.getDebugMode();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		String joinType = (!p.hasPlayedBefore() || debug.isDebugUser(p.getName())) ? "FirstJoin" : "Join";
		Resource joinConfig = resources.getResourceByName(joinType);
		String pathPrefix = joinType + ".Spawn";

		if (joinConfig.getBoolean(pathPrefix + ".Enabled")) {
			if (joinConfig.contains(pathPrefix + ".World")) {
				if (p.hasPermission("jep." + joinType.toLowerCase() + ".location")) {
					p.teleport(Toolkit.getLocationFromResource(joinConfig, pathPrefix));
				}
			} else {
				p.sendMessage(resources.getMessages().fetchString("Messages.Error.Spawn")
						.replace("%type%", joinType.equals("FirstJoin") ? "First Join" : "Join"));
			}
		}
	}
	
}
