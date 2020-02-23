package com.hyppocrate.utilities;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;


public class Utils {


    public static <T, V> V callIfDeployed(@NotNull final Function<T, V> function, final V defaultValue, final T parameter) {
        if (Utils.isDeployed()) {
            return function.apply(parameter);
        }
        return defaultValue;
    }
    // Implementation with generic Object
    public static <V> V callIfDeployed(final V value, final V defaultValue) {
        if (Utils.isDeployed()) {
            if (value == null) return defaultValue;
            return value;
        }
        return defaultValue;
    }
    // Implementation with int
    public static int callIfDeployed(final int value, final int defaultValue) {
        if (Utils.isDeployed()) {
            return value;
        }
        return defaultValue;
    }
    // Implementation with boolean
    public static boolean callIfDeployed(final boolean value, final boolean defaultValue) {
        if (Utils.isDeployed()) {
            return value;
        }
        return defaultValue;
    }

    private static boolean isDeployed() {
        return (CONSTANTS.projectVersion > 0);
    }

    // Get the value only if the map is not null, usefull to get informations from not yet implemented class
    public static <K,V> V tryGet(final HashMap<K,V> map, final K key) {
        if (map == null) return null;
        return map.get(key);
    }

    public static <V> String UnitTest(@NotNull final ThrowableSupplier<V> function, final V attemptedResult, final ArrayList<Exception> exceptions){
        try{
            final V result=function.get();

            if(attemptedResult.equals(result)){
                return "true";
            }else{
                return  "false, Not Same Value :\n\t attemptedResult: "+attemptedResult+"\n\t result: "+result;
            }
        }catch (final Exception e){
            if(exceptions!=null){
                for (final Exception except:exceptions) {
                    if(e.getClass()==except.getClass()){
                        return  "true";
                    }
                }
            }

            return "false, exception: "+e.getMessage()+e.getClass();
        }
    }
}
