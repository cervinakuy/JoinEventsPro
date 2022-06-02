package me.cervinakuy.joineventspro.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import me.clip.placeholderapi.PlaceholderAPI;

import java.util.ArrayList;
import java.util.List;

public class Toolkit {

	public static void runCommands(Player p, List<String> commands) {

		if (commands == null) return;

		for (String commandString : commands) {

			String[] commandPhrase = commandString.split(":", 2);
			commandPhrase[1] = commandPhrase[1].trim();

			String sender = commandPhrase[0];
			String command = commandPhrase[1];

			if (sender.equals("console")) {

				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), addPlaceholdersIfPossible(p, command.replace("%player%", p.getName())));

			} else if (sender.equals("player")) {

				p.performCommand(addPlaceholdersIfPossible(p, command.replace("%player%", p.getName())));

			}

		}

	}

	public static String addPlaceholdersIfPossible(Player player, String text) {

		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			text = PlaceholderAPI.setPlaceholders(player, text);
		}

		return Toolkit.translate(text);

	}
	
 	private static int versionToNumber() {
 		String version = Bukkit.getVersion();

 		if (version.contains("1.8")) {
 			return 18;
 		} else if (version.contains("1.9")) {
 			return 19;
 		} else if (version.contains("1.10")) {
 			return 110;
 		} else if (version.contains("1.11")) {
 			return 111;
 		} else if (version.contains("1.12")) {
 			return 112;
 		} else if (version.contains("1.13")) {
 			return 113;
 		} else if (version.contains("1.14")) {
 			return 114;
 		} else if (version.contains("1.15")) {
 			return 115;
 		} else if (version.contains("1.16")) {
 			return 116;
		} else if (version.contains("1.17")) {
 			return 117;
		} else if (version.contains("1.18")) {
 			return 118;
		}
 		
 		return 500;
 		
 	}
	
 	@SuppressWarnings("deprecation")
 	public static ItemStack getHandItemForInteraction(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (versionToNumber() == 18) {
			return p.getItemInHand();
		}

		EquipmentSlot slot = e.getHand() != null ? e.getHand() : EquipmentSlot.HAND;
		return p.getInventory().getItem(slot);
	}

	public static String translate(String s) {

		return ChatColor.translateAlternateColorCodes('&', s);

	}

	public static List<String> replaceInList(List<String> list, String find, String replace) {

		List<String> newList = new ArrayList<>();

		for (String string : list) {
			newList.add(string.replace(find, replace));
		}

		return newList;

	}

	public static void saveLocationToResource(Resource resource, String path, Location location) {

		resource.set(path + ".World", location.getWorld().getName());
		resource.set(path + ".X", location.getBlockX());
		resource.set(path + ".Y", location.getBlockY());
		resource.set(path + ".Z", location.getBlockZ());
		resource.set(path + ".Yaw", location.getYaw());
		resource.set(path + ".Pitch", location.getPitch());
		resource.save();

	}

	public static Location getLocationFromResource(Resource resource, String path) {

		return new Location(Bukkit.getWorld(resource.getString(path + ".World")),
				(float) resource.getInt(path + ".X") + 0.5,
				(float) resource.getInt(path + ".Y"),
				(float) resource.getInt(path + ".Z") + 0.5,
				(float) resource.getDouble(path + ".Yaw"),
				(float) resource.getDouble(path + ".Pitch"));

	}

	public static boolean containsAnyThatStartWith(List<String> list, String valueToTest) {

		for (String string : list) {

			if (valueToTest.startsWith(string)) {

				return true;

			}

		}

		return false;

	}

}
