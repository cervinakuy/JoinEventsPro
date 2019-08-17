package me.cervinakuy.joineventspro.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import me.cervinakuy.joineventspro.Game;

public class Config {

	public static String translate(String string) {
		
		return ChatColor.translateAlternateColorCodes('&', string.replace("%prefix%", Game.getInstance().getConfig().getString("Messages.General.Prefix")));
		
	}

	public static List<String> translateList(List<String> list) {
		
		List<String> translatedList = new ArrayList<String>();
		
		for (String string: list) {
			
			translatedList.add(ChatColor.translateAlternateColorCodes('&', string.replace("%newline%", "\n")));
			
		}
		
		return translatedList;
		
	}
 
	public static String getString(String path) {
		
		return ChatColor.translateAlternateColorCodes('&', Game.getInstance().getConfig().getString(path).replace("%prefix%", Game.getInstance().getConfig().getString("Messages.General.Prefix")));
		
	}
	
	public static boolean getBoolean(String path) {
		
		return Game.getInstance().getConfig().getBoolean(path);
		
	}
	
	public static int getInteger(String path) {
		
		return Game.getInstance().getConfig().getInt(path);
		
	}
	
	public static double getDouble(String path) {
		
		return Game.getInstance().getConfig().getDouble(path);
		
	}
	
	public static List<String> getStringList(String path) {
		
		return Game.getInstance().getConfig().getStringList(path);
		
	}
	
	public static FileConfiguration getConfiguration() {
		
		return Game.getInstance().getConfig();
		
	}
	
}
