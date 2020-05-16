package me.cervinakuy.joineventspro.listener;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import me.cervinakuy.joineventspro.util.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RefreshListener implements Listener {

	private List<String> identifiers;

	public RefreshListener() {
		this.identifiers = new ArrayList<String>();

		ConfigurationSection section = Config.getConfiguration().getConfigurationSection("Server.MOTD.List");

		for (String identifier : section.getKeys(false)) {
			if (!identifier.equals("Maintenance")) {
				identifiers.add(identifier);
			}
		}

	}

	@EventHandler
	public void onPing(ServerListPingEvent e) {

		if (Config.getBoolean("Server.MOTD.Options.Enabled")) {

			if (!Config.getBoolean("Server.MOTD.Options.Maintenance")) {

				e.setMotd(getMOTDFromConfig("Server.MOTD.List." + identifiers.get(new Random().nextInt(identifiers.size()))));

			} else {

				e.setMotd(getMOTDFromConfig("Server.MOTD.List.Maintenance"));

			}

		}
		
		e.setMaxPlayers(Config.getBoolean("Server.Players.Unlimited") ? (Bukkit.getOnlinePlayers().size() + 1) : Config.getInteger("Server.Players.Max"));
		
	}

	private String getMOTDFromConfig(String path) {

		String line1 = Config.getString(path + ".Line-1");
		String line2 = Config.getString(path + ".Line-2");

		return (line1 + "\n" + line2).replace("&", "\\u00A7");

	}
	
}
