package me.cervinakuy.joineventspro.command;

import me.cervinakuy.joineventspro.Game;
import me.cervinakuy.joineventspro.util.*;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {

	private Game plugin;
	private Resources resources;
	private Resource config;
	private Resource messages;
	
	public MainCommand(Game plugin) {
		this.plugin = plugin;
		this.resources = plugin.getResources();
		this.config = resources.getConfig();
		this.messages = resources.getMessages();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

		if (args.length == 0) {

			sender.sendMessage(Toolkit.translate("&7[&b&lJOINEVENTSPRO&7]"));
			sender.sendMessage(Toolkit.translate("&7Version: &b" + plugin.getDescription().getVersion()));
			sender.sendMessage(Toolkit.translate("&7Developer: &bCervinakuy"));
			sender.sendMessage(Toolkit.translate("&7Commands: &b/jep help"));
			sender.sendMessage(Toolkit.translate("&7Download: &bbit.ly/JoinEventsPro"));
			return true;

		} else if (args.length == 1) {

			if (args[0].equalsIgnoreCase("help")) {

				sender.sendMessage(Toolkit.translate("&7&m                    &r &b&lJOINEVENTSPRO &7&m                    &7&r"));
				sender.sendMessage(Toolkit.translate("&7- &b/jep &7View information about JoinEventsPro."));
				sender.sendMessage(Toolkit.translate("&7- &b/jep help &7Lists all available commands."));
				sender.sendMessage(Toolkit.translate("&7- &b/jep reload &7Reloads the configuration."));
				sender.sendMessage(Toolkit.translate("&7- &b/jep firstjoindebug &7Temporarily test as a first join player."));
				sender.sendMessage(Toolkit.translate("&7- &b/jep setfirstjoinlocation &7Sets the First Join Location."));
				sender.sendMessage(Toolkit.translate("&7- &b/jep setjoinlocation &7Sets the Join Location."));
				sender.sendMessage(Toolkit.translate("&7- &b/jep firstjoinlocation &7Teleport to the First Join Location."));
				sender.sendMessage(Toolkit.translate("&7- &b/jep joinlocation &7Teleport to the Join Location."));
				sender.sendMessage(Toolkit.translate("&7- &b/jep firstjoinmotd &7Displays the First Join MOTD."));
				sender.sendMessage(Toolkit.translate("&7- &b/jep joinmotd &7Displays the Join MOTD."));
				sender.sendMessage(Toolkit.translate("&7- &b/jep maintenance &7Toggles the built-in Maintenance Mode."));
				sender.sendMessage(Toolkit.translate("&7- &b/jep setmaxplayers <amount> &7Sets the maximum player limit."));
				sender.sendMessage(Toolkit.translate("&7&m                                                                 "));
				return true;

			} else if (args[0].equalsIgnoreCase("reload")
					&& hasPermission(sender, "jep.commands.admin")) {

				resources.reload();

				sender.sendMessage(messages.getString("Messages.Commands.Reload"));
				return true;

			} else if ((args[0].equalsIgnoreCase("firstjoinmotd") || args[0].equalsIgnoreCase("joinmotd"))
					&& hasPermission(sender, "jep.commands.admin")) {

				String joinType = args[0].equalsIgnoreCase("firstjoinmotd") ? "FirstJoin" : "Join";
				Resource joinConfig = resources.getResourceByName(joinType);

				for (String lines : joinConfig.getStringList(joinType + ".MOTD.Lines")) {
					sender.sendMessage(Toolkit.translate(lines));
				}

				return true;

			} else if (args[0].equalsIgnoreCase("maintenance")
					&& hasPermission(sender, "jep.commands.admin")) {

				boolean maintenance = config.getBoolean("Server.MOTD.Options.Maintenance");

				config.set("Server.MOTD.Options.Maintenance", !maintenance);
				config.save();

				for (Player player : Bukkit.getOnlinePlayers()) {
					if (!player.hasPermission("jep.server.maintenance")) {
						player.kickPlayer(config.getString("Server.Messages.Maintenance"));
					}
				}

				sender.sendMessage(messages.getString(maintenance ? "Messages.Commands.MaintenanceOff" : "Messages.Commands.MaintenanceOn"));
				return true;

			}

		} else if (args.length == 2) {

			if (args[0].equalsIgnoreCase("setmaxplayers")
					&& hasPermission(sender, "jep.commands.admin")) {

				if (StringUtils.isNumeric(args[1])) {

					if (config.getBoolean("Server.Players.Unlimited")) {
						config.set("Server.Players.Unlimited", false);
						config.save();
					}

					config.set("Server.Players.Max", Integer.parseInt(args[1]));
					config.save();

					sender.sendMessage(messages.getString("Messages.Commands.Players").replace("%number%", args[1]));

				} else {

					sender.sendMessage(messages.getString("Messages.Error.Number"));

				}

				return true;

			}

		}

		if (sender instanceof Player) {

			Player p = (Player) sender;

			if (args.length == 1) {

				if (args[0].equalsIgnoreCase("firstjoindebug")
						&& hasPermission(p, "jep.commands.admin")) {

					plugin.getDebugMode().toggleDebugUser(p.getName());

					p.sendMessage(messages.getString(plugin.getDebugMode().isDebugUser(p.getName()) ? "Messages.Commands.DebugOn" : "Messages.Commands.DebugOff"));
					return true;

				} else if ((args[0].equalsIgnoreCase("setfirstjoinlocation") || args[0].equalsIgnoreCase("setjoinlocation"))
						&& hasPermission(sender, "jep.commands.admin")) {

					String joinType = args[0].equalsIgnoreCase("setfirstjoinlocation") ? "FirstJoin" : "Join";

					Toolkit.saveLocationToResource(resources.getResourceByName(joinType), joinType + ".Spawn", p.getLocation());

					p.sendMessage(messages.getString("Messages.Commands.Spawn").replace("%type%", joinType.equals("FirstJoin") ? "First Join Location" : "Join Location"));
					return true;

				} else if ((args[0].equalsIgnoreCase("firstjoinlocation") || args[0].equalsIgnoreCase("joinlocation"))
						&& hasPermission(sender, "jep.commands.admin")) {

					String joinType = args[0].equalsIgnoreCase("firstjoinlocation") ? "FirstJoin" : "Join";
					Resource joinConfig = resources.getResourceByName(joinType);

					if (joinConfig.contains(joinType + ".Spawn.World")) {

						Location spawn = Toolkit.getLocationFromResource(joinConfig, joinType + ".Spawn.");
						p.teleport(spawn);

						p.sendMessage(messages.getString("Messages.Commands.Teleported").replace("%type%", joinType.equals("FirstJoin") ? "First Join Location" : "Join Location"));

					} else {

						p.sendMessage(messages.getString("Messages.Error.Spawn").replace("%type%", joinType.equals("FirstJoin") ? "First Join Location" : "Join Location"));

					}

					return true;

				}

			}

		} else {

			sender.sendMessage(messages.getString("Messages.General.Player"));
			return true;

		}

		return false;
		
	}

	private boolean hasPermission(CommandSender sender, String permission) {

		if (sender.hasPermission(permission)) {
			return true;
		}
		sender.sendMessage(messages.getString("Messages.General.Permission"));
		return false;

	}
	
}
