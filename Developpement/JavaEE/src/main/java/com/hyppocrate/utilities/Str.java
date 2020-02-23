package com.hyppocrate.utilities;

        import java.util.Arrays;
        import java.util.Collection;

public class Str {

    public static String randomString(final int length) {

        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(Str.randomChar());
        }
        return builder.toString();
    }

    public static String randomChar() {
        final String totalChars = Str.totalAlphabet + Str.numbers;
        return Character.toString(totalChars.charAt((int) (Math.random() * totalChars.length())));
    }

    // A utiliser à la place de Strings.isNullOrEmpty (il y a une erreur de chargement du jdk)
    public static boolean isNullOrEmpty(final String str) {
        return str == null || str.equals("");
    }

    private static final String alphabet  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String totalAlphabet  = Str.alphabet + Str.alphabet.toLowerCase();
    private static final String numbers = "123456789";
}
