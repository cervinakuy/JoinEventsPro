package me.cervinakuy.joineventspro.listener;

import com.cryptomorin.xseries.XMaterial;
import me.cervinakuy.joineventspro.Game;
import me.cervinakuy.joineventspro.util.*;
import org.bukkit.Material;
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

	private final Resources resources;
	private final DebugMode debug;

	public JoinItems(Game plugin) {
		this.resources = plugin.getResources();
		this.debug = plugin.getDebugMode();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		String joinType = Toolkit.getJoinType(p, debug);
		Resource joinConfig = resources.getResourceByName(joinType);

		if (joinConfig.getBoolean(joinType + ".Items.Enabled") &&
				p.hasPermission("jep." + joinType.toLowerCase() + ".items")) {
			
			ConfigurationSection section = joinConfig.getConfigurationSection(joinType + ".Items");
			
			for (String items : section.getKeys(false)) {
				String itemPath = joinType + ".Items." + items;
				
				if (!joinConfig.contains(itemPath + ".Name")) {
					break;
					
				} else {
					if (p.hasPermission(joinConfig.fetchString(itemPath + ".Permission"))) {
						String material = joinConfig.fetchString(itemPath + ".Material");
						int amount = joinConfig.getInt(itemPath + ".Amount");
						ItemStack item = Toolkit.safeItemStack(material, amount);

						ItemMeta itemMeta = item.getItemMeta();
						itemMeta.setDisplayName(joinConfig.fetchString(itemPath + ".Name"));
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
		String joinType = Toolkit.getJoinType(p, debug);
		Resource joinConfig = resources.getResourceByName(joinType);

		if (!joinConfig.getBoolean(joinType + ".Items.Enabled")) {
			return;
		}

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			ItemStack handItem = Toolkit.getItemForInteraction(e);
			if (!handItem.hasItemMeta()) {
				return;
			}

			ConfigurationSection section = joinConfig.getConfigurationSection(joinType + ".Items");

			for (String itemIdentifier : section.getKeys(false)) {
				if (itemIdentifier.equals("Enabled")) {
					continue;
				}

				String pathPrefix = joinType + ".Items." + itemIdentifier;
				Material joinItemMaterial = XMaterial.matchXMaterial(
						joinConfig.fetchString(pathPrefix + ".Material")).get().parseMaterial();

				if (handItem.getType() == joinItemMaterial) {
					if (handItem.getItemMeta().hasDisplayName() &&
							handItem.getItemMeta().getDisplayName()
									.equals(joinConfig.fetchString(pathPrefix + ".Name"))) {
						Toolkit.runCommands(p, joinConfig.getStringList(pathPrefix + ".Commands"));
					}
				}
			}
		}
	}

}
