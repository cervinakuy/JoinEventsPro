package me.cervinakuy.joineventspro.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public class Toolkit {
	
	public static void runCommands(Player p, String path) {
		
		if (Config.getConfiguration().contains(path + ".Commands")) {
			
			for (String list : Config.getConfiguration().getStringList(path + ".Commands")) {
				
				String[] command = list.split(":", 2);
				command[1] = command[1].trim();
				
			    if (command[0].equals("console")) {
					
					if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
						
						String withPlaceholders = PlaceholderAPI.setPlaceholders(p, command[1].replace("%player%", p.getName()));
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), withPlaceholders);
						
					} else {
						
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command[1].trim().replace("%player%", p.getName()));
						
					}
					
				} else if (command[0].equals("player")) {
					
					if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
						
						String withPlaceholders = PlaceholderAPI.setPlaceholders(p, command[1].trim().replace("%player%", p.getName()));
						p.performCommand(withPlaceholders);
						
					} else {
						
						p.performCommand(command[1].replace("%player%", p.getName()));
						
					}

				}
				
			}
			
		}
		
	}

}
