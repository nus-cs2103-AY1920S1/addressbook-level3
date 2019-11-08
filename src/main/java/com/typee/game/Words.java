package com.typee.game;

/**
 * Words being used in typing game.
 */
public class Words {
    public static final int SIZE = 83;

    private static final String[] words = {"get", "time", "come", "men", "have", "his", "show", "high", "learn", "kind",
                                           "number", "me", "small", "red", "very", "land", "small", "below", "form",
                                           "said", "why", "seem", "white", "really", "than", "run", "fire", "over",
                                           "can", "at", "follow", "too", "its", "example", "of", "me", "mile", "also",
                                           "was", "set", "at", "oil", "grow", "need", "call", "no", "said", "hard",
                                           "almost", "house", "idea", "form", "just", "something", "help", "every",
                                           "again", "in", "the", "earth", "one", "came", "up", "very", "people",
                                           "other", "alert", "area", "should", "hello", "fix", "rice", "type",
                                           "chicken", "there", "and", "back", "ring", "blink", "grass", "fly", "sprint",
                                           "attack"};

    /**
     * Returns the word in the array given the {@code index}
     * @param index The index of the word in the array
     */
    public static String get(int index) {
        return words[index];
    }
}
