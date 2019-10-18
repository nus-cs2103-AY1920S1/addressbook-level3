package com.typee.game;

/**
 * Words being used in typing game. Contains placeholder words for now.
 */
public class Words {

    public static final int SIZE = 5;

    private static final String[] words = { "Lorem", "ipsum", "is", "placeholder", "text" };

    public static String get(int index) {
        return words[index];
    }
}
