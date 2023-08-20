package me.cervinakuy.joineventspro.util;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.cervinakuy.joineventspro.Game;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import static net.md_5.bungee.api.ChatColor.COLOR_CHAR;

public class Resource extends YamlConfiguration {

    private final String name;
    private final File file;
    private final List<String> copyDefaultExemptions;

    private final Plugin plugin;
    private final String path;

    public Resource(Plugin plugin, String path) {
        this.plugin = plugin;
        this.path = path;

        this.file = new File(plugin.getDataFolder() + "/" + Paths.get(path));
        this.name = Paths.get(path).getFileName().toString();
        this.copyDefaultExemptions = new ArrayList<>();
    }

    public void load() {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (!file.exists()) {
            if (plugin.getResource(path) == null) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                plugin.saveResource(path, true);
            }
        }

        try {
            super.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    public void copyDefaults() {
        Reader defaultConfigSearchResult = null;

        if (plugin.getResource(path) != null) {
            try {
                defaultConfigSearchResult = new InputStreamReader(plugin.getResource(path), "UTF8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        if (defaultConfigSearchResult != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultConfigSearchResult);

            for (String valuePath : defaultConfig.getValues(true).keySet()) {
                if (!contains(valuePath)) {
                    if (!Toolkit.containsAnyThatStartWith(copyDefaultExemptions, valuePath)) {
                        this.set(valuePath, defaultConfig.get(valuePath));
                    }
                }
            }
            save();
        }

    }

    public void addCopyDefaultExemption(String path) {
        copyDefaultExemptions.add(path);
    }

    public void save() {
        try {
            super.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String fetchString(String path) {
        String string = super.getString(path);

        string = translateHexColorCodes(string);
        //string.replace("%prefix%", Game.getPrefix() == null ? "" : Game.getPrefix());

        return string;
    }

    @Override
    public List<String> getStringList(String path) {
        List<String> originalList = super.getStringList(path);

        if (originalList != null) {
            List<String> colorizedList = new ArrayList<>();
            for (String line : originalList) {
                colorizedList.add(translateHexColorCodes(line));
            }
            return colorizedList;
        }

        return originalList;

    }
    public String translateHexColorCodes(String message)
    {
        Pattern pattern = Pattern.compile("<#[a-fA-F0-9]{6}>");
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, ChatColor.of(color) + "");
            matcher = pattern.matcher(message);
        }
        System.out.println(matcher);
        System.out.println(message);
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String getName() { return name; }

    public File getFile() { return file; }

}