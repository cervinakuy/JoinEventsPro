package me.cervinakuy.joineventspro;

import me.cervinakuy.joineventspro.util.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.cervinakuy.joineventspro.command.MainCommand;
import me.cervinakuy.joineventspro.listener.JoinBook;
import me.cervinakuy.joineventspro.listener.JoinCommands;
import me.cervinakuy.joineventspro.listener.JoinFirework;
import me.cervinakuy.joineventspro.listener.JoinItems;
import me.cervinakuy.joineventspro.listener.JoinLocation;
import me.cervinakuy.joineventspro.listener.JoinLogin;
import me.cervinakuy.joineventspro.listener.JoinMOTD;
import me.cervinakuy.joineventspro.listener.JoinMessage;
import me.cervinakuy.joineventspro.listener.JoinNotification;
import me.cervinakuy.joineventspro.listener.JoinSound;
import me.cervinakuy.joineventspro.listener.RefreshListener;

public class Game extends JavaPlugin {

	private Resources resources;
	private DebugMode debugMode;

	private static String prefix;
	private String updateVersion = "Error";
	private boolean needsUpdate = false;

	@Override
	public void onEnable() {

		this.resources = new Resources(this);
		this.debugMode = new DebugMode();

		resources.load();
		prefix = resources.getMessages().fetchString("Messages.General.Prefix");

		Toolkit.printToConsole("[&b&lJOINEVENTSPRO&7] &7Loading &bJoinEventsPro &7version &b" +
				this.getDescription().getVersion() + "&7...");

		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new JoinMessage(this), this);
		pm.registerEvents(new JoinSound(this), this);
		pm.registerEvents(new JoinMOTD(this), this);
		pm.registerEvents(new JoinCommands(this), this);
		pm.registerEvents(new JoinItems(this), this);
		pm.registerEvents(new JoinFirework(this), this);
		pm.registerEvents(new JoinLocation(this), this);
		pm.registerEvents(new JoinBook(this), this);
		pm.registerEvents(new JoinLogin(this), this);
		pm.registerEvents(new JoinNotification(this), this);
		pm.registerEvents(new RefreshListener(this), this);
		
		getCommand("joineventspro").setExecutor(new MainCommand(this));
		
		new Metrics(this);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				checkUpdates();
			}
		}.runTaskAsynchronously(this);
		
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			Toolkit.printToConsole("[&b&lJOINEVENTSPRO&7] &7Discovered &bPlaceholderAPI&7, now hooking into it.");
		}

		Toolkit.printToConsole("[&b&lJOINEVENTSPRO&7] &aSuccessfully loaded the plugin.");
		
	}
	
	private void checkUpdates() {
		Updater.of(this).resourceId(22105).handleResponse((versionResponse, version) -> {
			switch (versionResponse) {
				case FOUND_NEW:
					Bukkit.getConsoleSender().sendMessage(Toolkit.translate("&7[&b&lJOINEVENTSPRO&7] &aNew version found! Please update to v" + version + " on the Spigot page."));
					needsUpdate = true;
					updateVersion = version;
					break;
				case LATEST:
					Bukkit.getConsoleSender().sendMessage(Toolkit.translate("&7[&b&lJOINEVENTSPRO&7] &7No new update found. You are on the latest version."));
					break;
				case UNAVAILABLE:
					Bukkit.getConsoleSender().sendMessage(Toolkit.translate("&7[&b&lJOINEVENTSPRO&7] &cUnable to perform an update check."));
					break;
			}
		}).check();
	}
	
	public boolean needsUpdate() { return needsUpdate; }

	public String getUpdateVersion() { return updateVersion; }

	public DebugMode getDebugMode() { return debugMode; }

	public Resources getResources() { return resources; }

	public static String getPrefix() { return prefix; }
	
}
