package com.hyppocrate.components;

import com.hyppocrate.utilities.Str;

import java.sql.SQLException;

public class LangManager {

    public String getString(final String idString, final String idLangue) {

        if (Str.isNullOrEmpty(idString) || Str.isNullOrEmpty(idLangue)) {
            throw new IllegalArgumentException();
        }

        try {
            return SQLManager.getInstance().getString(idString, idLangue);
        } catch (final SQLException e) {
            return e.toString();
        }
    }

    // FIXME: 18/01/2020
    public String getString(final String idString) {
        return this.getString(idString, "en");
    }

    // TODO: 18/01/2020
    public String getLangue() {
        return null;
    }
}
