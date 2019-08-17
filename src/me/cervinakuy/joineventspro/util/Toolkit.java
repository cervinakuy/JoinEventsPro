package me.cervinakuy.joineventspro.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
	
 	private static int versionToNumber() {
 		
 		if (Bukkit.getVersion().contains("1.8")) {
 			
 			return 18;
 			
 		} else if (Bukkit.getVersion().contains("1.9")) {
 			
 			return 19;
 			
 		} else if (Bukkit.getVersion().contains("1.10")) {
 			
 			return 110;
 			
 		} else if (Bukkit.getVersion().contains("1.11")) {
 			
 			return 111;
 			
 		} else if (Bukkit.getVersion().contains("1.12")) {
 			
 			return 112;
 			
 		} else if (Bukkit.getVersion().contains("1.13")) {
 			
 			return 113;
 			
 		} else if (Bukkit.getVersion().contains("1.14")) {
 			
 			return 114;
 			
 		}
 		
 		return -1;
 		
 	}
	
 	@SuppressWarnings("deprecation")
	public static ItemStack getMainHandItem(Player p) {
 		
 		if (versionToNumber() == 18) {
 			
 			return p.getItemInHand();
 			
 		} else if (versionToNumber() > 18) {
 			
 			return p.getInventory().getItemInMainHand();
 			
 		}
 		
 		return p.getItemInHand();
 		
 	}

}
