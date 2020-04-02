package com.hyppocrate.components;

import java.sql.SQLException;
import java.util.HashMap;

public class AuthentificationModule {

    private static String login;

    public AuthentificationModule() {
        AuthentificationModule.login = null;
    }

    public String getConnected() {
        return AuthentificationModule.login;
    }
    /*public HashMap<String, Object> getUser() {
        if (login == null) {
            return null;
        }
        return null;
    }*/
    public boolean connect(final String id, final String password) throws SQLException {
        if (id == null || password == null) {
            return false;
        }
        if (password.equals("")) {
            return false; //do nothing
        }
        if (AuthentificationModule.login != null) {
            return false; //already connected so no need to connect again
        }
        if (SQLManager.getInstance().connect(id, password) == null) {
            return false; //user not found
        }
        AuthentificationModule.login = id;
        return true;
    }

    public void disconnect() {
        AuthentificationModule.login = null;
    }

}