package com.innowise.textHandler.util;

public class utils {
    public static String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) return word;
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }

    public static String lowercaseFirstLetter(String word) {
        if (word == null || word.isEmpty()) return word;
        return Character.toLowerCase(word.charAt(0)) + word.substring(1);
    }
}
