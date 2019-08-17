package me.cervinakuy.joineventspro;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import me.cervinakuy.joineventspro.util.Metrics;
import me.cervinakuy.joineventspro.util.Updater;

public class Game extends JavaPlugin {

	private String version = "Error";
	private boolean needsUpdate = false;
	
	private static Game instance;
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "[&b&lJOINEVENTSPRO&7] &7Loading &bJoinEventsPro &7version &b" + this.getDescription().getVersion() + "&7..."));
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new JoinMessage(), this);
		pm.registerEvents(new JoinSound(), this);
		pm.registerEvents(new JoinMOTD(), this);
		pm.registerEvents(new JoinCommands(), this);
		pm.registerEvents(new JoinItems(), this);
		pm.registerEvents(new JoinFirework(), this);
		pm.registerEvents(new JoinLocation(), this);
		pm.registerEvents(new JoinLogin(), this);
		pm.registerEvents(new JoinBook(), this);
		pm.registerEvents(new JoinNotification(), this);
		pm.registerEvents(new RefreshListener(), this);
		
		getCommand("joineventspro").setExecutor(new MainCommand(this));
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		new Metrics(this);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				checkUpdates();
				
			}
			
		}.runTaskAsynchronously(this);
		
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "[&b&lJOINEVENTSPRO&7] &7Discovered &bPlaceholderAPI&7, now hooking into it."));
		}
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "[&b&lJOINEVENTSPRO&7] &aSuccessfully loaded the plugin."));
		
	}
	
	private void checkUpdates() {
		
		Updater updater = new Updater(this, 22105, false);
		Updater.UpdateResult result = updater.getResult();
		
		switch (result) {
		
		case UPDATE_AVAILABLE:
			
			needsUpdate = true;
			version = updater.getVersion();
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&b&lJOINEVENTSPRO&7] &aNew version found! Please update to v" + updater.getVersion() + " on the Spigot page."));
			break;
		
		case NO_UPDATE:
			
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&b&lJOINEVENTSPRO&7] &7No new update found. You are on the latest version."));
			break;
			
		case DISABLED:
			
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&b&lJOINEVENTSPRO&7] &cChecking for updates is currently disabled."));
			break;
			
		case FAIL_SPIGOT:
			
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&b&lJOINEVENTSPRO&7] &cThere was a problem reaching Spigot to check for updates."));
			break;
			
		case FAIL_NOVERSION:
			
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&b&lJOINEVENTSPRO&7] &cThe version could not be fetched from Spigot."));
			break;
			
		case BAD_RESOURCEID:
			
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&b&lJOINEVENTSPRO&7] &cThe updater resource ID is invalid."));
			break;
		
		}
		
	}
	
	public boolean needsUpdate() { return needsUpdate; }
	
	public String getUpdateVersion() { return version; }
	
	public static Game getInstance() { return instance; }
	
}
