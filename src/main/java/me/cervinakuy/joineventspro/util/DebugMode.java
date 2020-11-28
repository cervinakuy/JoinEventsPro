package me.cervinakuy.joineventspro.util;

import java.util.ArrayList;
import java.util.List;

public class DebugMode {

    private List<String> debugUsers;

    public DebugMode() {
        this.debugUsers = new ArrayList<String>();
    }

    public void toggleDebugUser(String username) {

        if (isDebugUser(username)) {
            debugUsers.remove(username);
        } else {
            debugUsers.add(username);
        }

    }

    public boolean isDebugUser(String username) {

        return debugUsers.contains(username);

    }

}
