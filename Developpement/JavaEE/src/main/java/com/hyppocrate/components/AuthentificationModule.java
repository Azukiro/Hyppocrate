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
    /*public HashMap<String, Object> getUser() {
        if (login == null) {
            return null;
        }
        return null;
    }*/
    public boolean connect(String id, String password) throws SQLException {
        if (id == null || password == null) {
            return false;
        }
        if (password.equals("")) {
            return false; //do nothing
        }
        if (login != null) {
            return false; //already connected so no need to connect again
        }
        if (SQLManager.getInstance().connect(id, password) == null) {
            return false; //user not found
        }
        login = id;
        return true;
    }

    public void disconnect() {
        login = null;
    }

}
