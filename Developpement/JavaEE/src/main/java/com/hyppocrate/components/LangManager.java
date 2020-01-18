package com.hyppocrate.components;

import com.hyppocrate.utilities.Str;

public class LangManager {

    public String getString(String idString, String idLangue) {

        if (Str.isNullOrEmpty(idString) || Str.isNullOrEmpty(idLangue)) {
            throw new IllegalArgumentException();
        }

        return SQLManager.getInstance().getString(idString, idLangue);
    }

    // FIXME: 18/01/2020
    public String getString(String idString) {
        return getString(idString, "en");
    }

    // TODO: 18/01/2020
    public String getLangue() {
        return null;
    }
}
