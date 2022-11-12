package me.cervinakuy.joineventspro.listener;

import me.cervinakuy.joineventspro.Game;
import me.cervinakuy.joineventspro.util.Resource;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RefreshListener implements Listener {

	private final Resource config;
	private final List<String> identifiers;
	private final Random random;

	public RefreshListener(Game plugin) {
		this.config = plugin.getResources().getConfig();
		this.identifiers = new ArrayList<>();
		this.random = new Random();

		ConfigurationSection section = config.getConfigurationSection("Server.MOTD.List");

		for (String identifier : section.getKeys(false)) {
			if (!identifier.equals("Maintenance")) {
				identifiers.add(identifier);
			}
		}
	}

	@EventHandler
	public void onPing(ServerListPingEvent e) {
		if (config.getBoolean("Server.MOTD.Options.Enabled")) {
			String motdType = config.getBoolean("Server.MOTD.Options.Maintenance") ?
					"Maintenance" : identifiers.get(random.nextInt(identifiers.size()));
			e.setMotd(getMOTDFromConfig("Server.MOTD.List." + motdType));
		}

		e.setMaxPlayers(config.getBoolean("Server.Players.Unlimited") ?
				(Bukkit.getOnlinePlayers().size() + 1) : config.getInt("Server.Players.Max"));
	}

	private String getMOTDFromConfig(String path) {
		String line1 = config.fetchString(path + ".Line-1");
		String line2 = config.fetchString(path + ".Line-2");
		return (line1 + "\n" + line2).replace("&", "\\u00A7");
	}
	
}
