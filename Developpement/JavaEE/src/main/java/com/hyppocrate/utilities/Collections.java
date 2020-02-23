package com.hyppocrate.utilities;

import java.util.*;

public class Collections {
    /**
     * Return one random item of the list
     *
     * @param lst the list
     * @param <T> the type of items in the list
     * @return a random item in this list
     */
    public static <T> T getRandom(final List<T> lst) {
        return lst.get((int) (Math.random() * lst.size()));
    }

    public static <T> ArrayList<T> getRandoms(final List<T> lst, final int amount) {
        final ArrayList<T> resultLst = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            resultLst.add(Collections.getRandom(lst));
        }
        return resultLst;
    }
}