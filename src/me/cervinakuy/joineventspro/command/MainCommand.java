package me.cervinakuy.joineventspro.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.cervinakuy.joineventspro.util.Config;
import me.cervinakuy.joineventspro.util.Sounds;

public class MainCommand implements CommandExecutor {

	private Plugin plugin;
	
	public MainCommand(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		
		if (command.getName().equalsIgnoreCase("joineventspro")) {
			
			if (sender instanceof Player) {
				
				Player p = (Player) sender;
				
				if (args.length == 0) {
					
					p.sendMessage(Config.translate("&7[&b&lJOINEVENTSPRO&7]"));
					p.sendMessage(Config.translate("&7Version: &b" + plugin.getDescription().getVersion()));
					p.sendMessage(Config.translate("&7Developer: &bCervinakuy"));
					p.sendMessage(Config.translate("&7Commands: &b/jep help"));
					p.sendMessage(Config.translate("&7Download: &bbit.ly/JoinEventsPro"));
					
				} else if (args.length == 1) {
				
					if (p.hasPermission("jep.commands.admin")) {
						
						if (args[0].equalsIgnoreCase("help")) {
							
							p.sendMessage(Config.translate("&7&m                                     &r &b&lJOINEVENTSPRO &7&m                                &7&r"));
							p.sendMessage(Config.translate("&7- &b/jep &7View information about JoinEventsPro."));
							p.sendMessage(Config.translate("&7- &b/jep help &7Lists all available commands."));
							p.sendMessage(Config.translate("&7- &b/jep reload &7Reloads the configuration."));
							p.sendMessage(Config.translate("&7- &b/jep setfirstjoinlocation &7Sets the First Join Location."));
							p.sendMessage(Config.translate("&7- &b/jep setjoinlocation &7Sets the Join Location."));
							p.sendMessage(Config.translate("&7- &b/jep firstjoinlocation &7Teleports you to the First Join Location."));
							p.sendMessage(Config.translate("&7- &b/jep joinlocation &7Teleports you to the Join Location."));
							p.sendMessage(Config.translate("&7- &b/jep firstjoinmotd &7Displays the First Join MOTD."));
							p.sendMessage(Config.translate("&7- &b/jep joinmotd &7Displays the Join MOTD."));
							p.sendMessage(Config.translate("&7- &b/jep servermotd &7Displays the Server MOTD."));
							p.sendMessage(Config.translate("&7- &b/jep maintenance &7Toggles the built-in Maintenance Mode."));
							p.sendMessage(Config.translate("&7- &b/jep setmaxplayers <amount> &7Sets the maximum player limit."));
							p.sendMessage(Config.translate("&7&m                                                                                                  "));
							
						} else if (args[0].equalsIgnoreCase("reload")) {
							
							plugin.reloadConfig();
							
							p.sendMessage(Config.getString("Messages.Commands.Reload"));
							p.playSound(p.getLocation(), Sounds.ORB_PICKUP.bukkitSound(), 1, 1);
							
						} else if (args[0].equalsIgnoreCase("firstjoinmotd")) {
							
							for (String lines : Config.getStringList("FirstJoin.MOTD.Lines")) {
								p.sendMessage(Config.translate(lines));
							}
							
							p.playSound(p.getLocation(), Sounds.ITEM_PICKUP.bukkitSound(), 1, 1);
							
						} else if (args[0].equalsIgnoreCase("joinmotd")) {
							
							for (String lines : Config.getStringList("Join.MOTD.Lines")) {
								p.sendMessage(Config.translate(lines));
							}
							
							p.playSound(p.getLocation(), Sounds.ITEM_PICKUP.bukkitSound(), 1, 1);
							
						} else if (args[0].equalsIgnoreCase("setfirstjoinlocation")) {
							
							plugin.getConfig().set("Spawn.First.World", p.getWorld().getName());
							plugin.getConfig().set("Spawn.First.X", p.getLocation().getBlockX());
							plugin.getConfig().set("Spawn.First.Y", p.getLocation().getBlockY());
							plugin.getConfig().set("Spawn.First.Z", p.getLocation().getBlockZ());
							plugin.getConfig().set("Spawn.First.Yaw", p.getLocation().getYaw());
							plugin.getConfig().set("Spawn.First.Pitch", p.getLocation().getPitch());
							plugin.saveConfig();
							
							p.sendMessage(Config.getString("Messages.Commands.Spawn").replace("%type%", "First Join Location"));
							p.playSound(p.getLocation(), Sounds.LEVEL_UP.bukkitSound(), 1, 1);
							
						} else if (args[0].equalsIgnoreCase("setjoinlocation")) {
							
							plugin.getConfig().set("Spawn.Join.World", p.getWorld().getName());
							plugin.getConfig().set("Spawn.Join.X", p.getLocation().getBlockX());
							plugin.getConfig().set("Spawn.Join.Y", p.getLocation().getBlockY());
							plugin.getConfig().set("Spawn.Join.Z", p.getLocation().getBlockZ());
							plugin.getConfig().set("Spawn.Join.Yaw", p.getLocation().getYaw());
							plugin.getConfig().set("Spawn.Join.Pitch", p.getLocation().getPitch());
							plugin.saveConfig();
							
							p.sendMessage(Config.getString("Messages.Commands.Spawn").replace("%type%", "Join Location"));
							p.playSound(p.getLocation(), Sounds.LEVEL_UP.bukkitSound(), 1, 1);
							
						} else if (args[0].equalsIgnoreCase("firstjoinlocation")) {	
							
							if (Config.getConfiguration().contains("Spawn.First.World")) {
								
								Location spawn = new Location(Bukkit.getWorld(Config.getString("Spawn.First.World")),
										Config.getInteger("Spawn.First.X") + 0.5,
										Config.getInteger("Spawn.First.Y"),
										Config.getInteger("Spawn.First.Z") + 0.5,
										Config.getInteger("Spawn.First.Yaw"),
										Config.getInteger("Spawn.First.Pitch"));
								p.teleport(spawn);
								
								p.playSound(p.getLocation(), Sounds.ENDERMAN_TELEPORT.bukkitSound(), 1, 1);
								p.sendMessage(Config.getString("Messages.Commands.Teleported").replace("%type%", "First Join Location"));
								
							} else {
								
								p.sendMessage(Config.getString("Messages.Error.Spawn").replace("%type%", "First Join"));
								p.playSound(p.getLocation(), Sounds.ENDERDRAGON_HIT.bukkitSound(), 1, 1);
								
							}
							
						} else if (args[0].equalsIgnoreCase("joinlocation")) {	
							
							if (Config.getConfiguration().contains("Spawn.Join.World")) {
								
								Location spawn = new Location(Bukkit.getWorld(Config.getString("Spawn.Join.World")),
										Config.getInteger("Spawn.Join.X") + 0.5,
										Config.getInteger("Spawn.Join.Y"),
										Config.getInteger("Spawn.Join.Z") + 0.5,
										Config.getInteger("Spawn.Join.Yaw"),
										Config.getInteger("Spawn.Join.Pitch"));
								p.teleport(spawn);
								
								p.playSound(p.getLocation(), Sounds.ENDERMAN_TELEPORT.bukkitSound(), 1, 1);
								p.sendMessage(Config.getString("Messages.Commands.Teleported").replace("%type%", "Join Location"));
								
							} else {
								
								p.sendMessage(Config.getString("Messages.Error.Spawn").replace("%type%", "Join"));
								p.playSound(p.getLocation(), Sounds.ENDERDRAGON_HIT.bukkitSound(), 1, 1);
								
							}
							
						} else if (args[0].equalsIgnoreCase("servermotd")) {	
							
							p.sendMessage(" " + Config.getString("Server.MOTD.Normal.Line-1"));
							p.sendMessage(" " + Config.getString("Server.MOTD.Normal.Line-2"));
							
							p.playSound(p.getLocation(), Sounds.ITEM_PICKUP.bukkitSound(), 1, 1);
							
						} else if (args[0].equalsIgnoreCase("maintenance")) {
							
							boolean maintenance = Config.getBoolean("Server.MOTD.Maintenance.Enabled");
							
							plugin.getConfig().set("Server.MOTD.Maintenance.Enabled", !maintenance);
							plugin.saveConfig();
							
							p.sendMessage(Config.getString(maintenance ? "Messages.Commands.MaintenanceOff" : "Messages.Commands.MaintenanceOn"));
							p.playSound(p.getLocation(), Sounds.IRONGOLEM_HIT.bukkitSound(), 1, -1);

						} else {
							
							p.sendMessage(Config.getString("Messages.Commands.Unknown"));
							p.playSound(p.getLocation(), Sounds.ENDERDRAGON_HIT.bukkitSound(), 1, 1);
							
						}
						
					} else {
						
						p.sendMessage(Config.getString("Messages.General.Permission"));
						p.playSound(p.getLocation(), Sounds.ENDERDRAGON_HIT.bukkitSound(), 1, 1);
						
					}
					
				} else if (args.length == 2) {
					
					if (args[0].equalsIgnoreCase("setmaxplayers")) {
						
						if (StringUtils.isNumeric(args[1])) {
							
							plugin.getConfig().set("Server.Players.Max", args[1]);
							plugin.saveConfig();
							
							p.sendMessage(Config.getString("Messages.Commands.Players").replace("%number%", args[1]));
							p.playSound(p.getLocation(), Sounds.ORB_PICKUP.bukkitSound(), 1, 1);
							
						} else {
							
							p.sendMessage(Config.getString("Messages.Error.Number"));
							p.playSound(p.getLocation(), Sounds.ENDERDRAGON_HIT.bukkitSound(), 1, 1);
							
						}
						
					}
					
				} else {
						
					p.sendMessage(Config.getString("Messages.Commands.Unknown"));
					p.playSound(p.getLocation(), Sounds.ENDERDRAGON_HIT.bukkitSound(), 1, 1);
					
				}
				
			} else {
				
				sender.sendMessage(Config.getString("Messages.General.Player"));
				
			}
				
		}
		
		return false;
		
	}
	
}
