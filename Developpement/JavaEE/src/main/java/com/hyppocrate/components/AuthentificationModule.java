package com.hyppocrate.components;

import java.sql.SQLException;
import java.util.HashMap;

public class AuthentificationModule {

    private static String login;

    public AuthentificationModule() {
        login = null;
    }

    public String getConnected() {
        return login;
    }
    public HashMap<String, Object> getUser() {
        if (login == null) {
            return null;
        }
        return null;
    }
    public static boolean connect(String id, String password) throws SQLException {
        if (login == null) {
            return false;
        }
        if (SQLManager.getInstance().connect(id, password) == null) {
            return false;
        }
        login = id;
        return true;
    }

    public static void disconnect() {
        login = null;
    }

}
