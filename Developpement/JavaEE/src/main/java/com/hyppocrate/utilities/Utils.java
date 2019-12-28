package com.hyppocrate.utilities;

import java.util.HashMap;
import java.util.function.Function;

public class Utils {

    public static <T, V> V callIfDeployed(Function<T, V> function, V defaultValue, T parameter) {
        if (isDeployed()) {
            return function.apply(parameter);
        }
        return defaultValue;
    }

    public static <V> V callIfDeployed(V value, V defaultValue) {
        if (isDeployed()) {
            if (value == null) return defaultValue;
            return value;
        }
        return defaultValue;
    }
    public static int callIfDeployed(int value, int defaultValue) {
        if (isDeployed()) {
            return value;
        }
        return defaultValue;
    }
    public static boolean callIfDeployed(boolean value, boolean defaultValue) {
        if (isDeployed()) {
            return value;
        }
        return defaultValue;
    }

    private static boolean isDeployed() {
        return (CONSTANTS.projectVersion > 0);
    }

    // Get the value only if the map is not null, usefull to get informations from not yet implemented class
    public static <K,V> V tryGet(HashMap<K,V> map, K key) {
        if (map == null) return null;
        return map.get(key);
    }


}
