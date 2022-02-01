package me.cervinakuy.joineventspro.listener;

import java.util.List;

import com.cryptomorin.xseries.XMaterial;
import me.cervinakuy.joineventspro.Game;
import me.cervinakuy.joineventspro.util.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class JoinItems implements Listener {

	private Resources resources;
	private DebugMode debug;

	public JoinItems(Game plugin) {
		this.resources = plugin.getResources();
		this.debug = plugin.getDebugMode();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		String joinType = (!p.hasPlayedBefore() || debug.isDebugUser(p.getName())) ? "FirstJoin" : "Join";
		Resource joinConfig = resources.getResourceByName(joinType);

		if (joinConfig.getBoolean(joinType + ".Items.Enabled") && p.hasPermission("jep." + joinType.toLowerCase() + ".items")) {
			
			ConfigurationSection section = joinConfig.getConfigurationSection(joinType + ".Items");
			
			for (String items : section.getKeys(false)) {
				
				String itemPath = joinType + ".Items." + items;
				
				if (!joinConfig.contains(itemPath + ".Name")) {
				
					break;
					
				} else {
					
					if (p.hasPermission(joinConfig.getString(itemPath + ".Permission"))) {

						String material = joinConfig.getString(itemPath + ".Material");
						int amount = joinConfig.getInt(itemPath + ".Amount");
						ItemStack item = new ItemStack(XMaterial.matchXMaterial(material).get().parseMaterial(), amount);

						ItemMeta itemMeta = item.getItemMeta();
						itemMeta.setDisplayName(joinConfig.getString(itemPath + ".Name"));
						itemMeta.setLore(joinConfig.getStringList(itemPath + ".Lore"));
						item.setItemMeta(itemMeta);

						p.getInventory().setItem(joinConfig.getInt(itemPath + ".Slot"), XMaterial.AIR.parseItem());
						p.getInventory().setItem(joinConfig.getInt(itemPath + ".Slot"), item);
						
					}
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		String joinType = (!p.hasPlayedBefore() || debug.isDebugUser(p.getName())) ? "FirstJoin" : "Join";
		Resource joinConfig = resources.getResourceByName(joinType);

		if (joinConfig.getBoolean(joinType + ".Items.Enabled")) {
			
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {

				ItemStack mainHand = Toolkit.getMainHandItem(p);

				if (!mainHand.hasItemMeta()) {
					return;
				}

				ConfigurationSection section = joinConfig.getConfigurationSection(joinType + ".Items");

				for (String itemIdentifier : section.getKeys(false)) {

					if (!itemIdentifier.equals("Enabled")) {

						String pathPrefix = joinType + ".Items." + itemIdentifier;

						if (mainHand.getType() == XMaterial.matchXMaterial(joinConfig.getString(pathPrefix + ".Material")).get().parseMaterial()) {

							if (mainHand.getItemMeta().hasDisplayName() &&
									mainHand.getItemMeta().getDisplayName().equals(joinConfig.getString(pathPrefix + ".Name"))) {

								List<String> commands = joinConfig.getStringList(pathPrefix + ".Commands");
								Toolkit.runCommands(p, commands);

							}

						}

					}
					
				}
				
			}
			
		}
		
	}

}
