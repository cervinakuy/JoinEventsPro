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

public class JoinMOTD implements Listener {

	private final Resources resources;
	private final DebugMode debug;

	public JoinMOTD(Game plugin) {
		this.resources = plugin.getResources();
		this.debug = plugin.getDebugMode();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		String joinType = Toolkit.getJoinType(p, debug);
		Resource joinConfig = resources.getResourceByName(joinType);
		
		if (joinConfig.getBoolean(joinType + ".MOTD.Enabled") &&
				p.hasPermission("jep." + joinType.toLowerCase() + ".motd")) {
			for (String lines : joinConfig.getStringList(joinType + ".MOTD.Lines")) {
				p.sendMessage(Toolkit.addPlaceholdersIfPossible(p, lines.replace("%player%", p.getName())));
			}
		}
	}
	
}
