package me.cervinakuy.joineventspro.listener;

import java.util.List;

import me.cervinakuy.joineventspro.Game;
import me.cervinakuy.joineventspro.util.DebugMode;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cervinakuy.joineventspro.util.Config;
import me.cervinakuy.joineventspro.util.Toolkit;
import me.cervinakuy.joineventspro.util.XMaterial;

public class JoinItems implements Listener {

	private DebugMode debug;

	public JoinItems(Game plugin) {
		this.debug = plugin.getDebugMode();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		String joinType = (!p.hasPlayedBefore() || debug.isDebugUser(p.getName())) ? "FirstJoin" : "Join";
		
		if (Config.getBoolean(joinType + ".Items.Enabled") && p.hasPermission("jep." + joinType.toLowerCase() + ".items")) {
			
			ConfigurationSection section = Config.getConfiguration().getConfigurationSection(joinType + ".Items");
			
			for (String items : section.getKeys(false)) {
				
				String itemPath = joinType + ".Items." + items;
				
				if (!Config.getConfiguration().contains(itemPath + ".Name")) {
				
					break;
					
				} else {
					
					if (p.hasPermission(Config.getString(itemPath + ".Permission"))) {
						
						ItemStack item = new ItemStack(XMaterial.matchXMaterial(Config.getString(itemPath + ".Material")).get().parseMaterial(), Config.getInteger(itemPath + ".Amount"));
						ItemMeta itemMeta = item.getItemMeta();
						
						itemMeta.setDisplayName(Config.getString(itemPath + ".Name"));
						
						List<String> lore = Config.translateList(Config.getConfiguration().getStringList(itemPath + ".Lore"));
						itemMeta.setLore(lore);
						
						item.setItemMeta(itemMeta);
						
						if (!Config.getConfiguration().contains(itemPath + ".Override") || Config.getBoolean(itemPath + ".Override")) {
							p.getInventory().setItem(Config.getInteger(itemPath + ".Slot"), XMaterial.AIR.parseItem());
							p.getInventory().setItem(Config.getInteger(itemPath + ".Slot"), item);
						} else {
							if (p.getInventory().getItem(Config.getInteger(itemPath + ".Slot")) != null) {
								if (Config.getBoolean(itemPath + ".Override") == false) {
									int emptySlot = getEmptySlot(p.getInventory());
									if (emptySlot != -1) {
										p.getInventory().setItem(emptySlot, XMaterial.AIR.parseItem());
										p.getInventory().setItem(emptySlot, item);
									} else {
										p.sendMessage(Config.getString("Messages.Error.Slot"));
									}
								}
							} else {
								p.getInventory().setItem(Config.getInteger(itemPath + ".Slot"), XMaterial.AIR.parseItem());
								p.getInventory().setItem(Config.getInteger(itemPath + ".Slot"), item);
							}

						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		String joinType = (!p.hasPlayedBefore() || debug.isDebugUser(p.getName())) ? "FirstJoin" : "Join";
		
		if (Config.getBoolean(joinType + ".Items.Enabled")) {
			
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
				
				if (Toolkit.getMainHandItem(p).hasItemMeta()) {
					
					ConfigurationSection section = Config.getConfiguration().getConfigurationSection(joinType + ".Items");
					
					for (String items : section.getKeys(false)) {
						
						if (!(items.equals("Enabled"))) {
							
							if (Toolkit.getMainHandItem(p).getType() == XMaterial.matchXMaterial(Config.getString(joinType + ".Items." + items + ".Material")).get().parseMaterial()) {
								
								if (Toolkit.getMainHandItem(p).getItemMeta().getDisplayName().equals(Config.getString(joinType + ".Items." + items + ".Name"))) {
									
									Toolkit.runCommands(p, joinType + ".Items." + items);
									
								}
							
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	private int getEmptySlot(Inventory inventory) {
		for (int i = 0; i < 36; i++) {
			if (inventory.getItem(i) == null) {
				return i;
			}
		}
		return -1;
		
	}

}
