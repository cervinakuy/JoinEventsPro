package me.cervinakuy.joineventspro.util;

import me.cervinakuy.joineventspro.Game;

public class Resources {

    private Resource config;
    private Resource regularJoin;
    private Resource firstJoin;
    private Resource messages;

    public Resources(Game plugin) {

        this.config = new Resource(plugin, "config.yml");
        this.regularJoin = new Resource(plugin, "regularjoin.yml");
        this.firstJoin = new Resource(plugin, "firstjoin.yml");
        this.messages = new Resource(plugin, "messages.yml");

    }

    public void load() {

        config.load();
        regularJoin.load();
        firstJoin.load();
        messages.load();

        config.addCopyDefaultExemption("Server.MOTD.List.RandomYellow");
        config.addCopyDefaultExemption("Server.MOTD.List.RandomBlue");
        config.addCopyDefaultExemption("Server.MOTD.List.RandomGreen");
        config.copyDefaults();

        regularJoin.addCopyDefaultExemption("Join.MOTD.Lines");
        regularJoin.addCopyDefaultExemption("Join.Book.Information.Pages");
        regularJoin.addCopyDefaultExemption("Join.Items.Selector");
        regularJoin.addCopyDefaultExemption("Join.Items.Pickaxe");
        regularJoin.copyDefaults();

        firstJoin.addCopyDefaultExemption("FirstJoin.MOTD.Lines");
        firstJoin.addCopyDefaultExemption("FirstJoin.Book.Information.Pages");
        firstJoin.addCopyDefaultExemption("FirstJoin.Items.Diamond");
        firstJoin.copyDefaults();

        messages.copyDefaults();

    }

    public void reload() {

        load();

    }

    public Resource getResourceByName(String resourceName) {

        if (resourceName.equalsIgnoreCase("join")) {

            return regularJoin;

        } else if (resourceName.equalsIgnoreCase("firstjoin")) {

            return firstJoin;

        }

        return null;

    }
    public Resource getConfig() { return config; }

    public Resource getMessages() { return messages; }

}
