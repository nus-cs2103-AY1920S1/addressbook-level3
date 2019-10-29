package com.typee.game;

/**
 * Words being used in typing game. Contains placeholder words for now.
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
                                           "other", "alert", "area", "should", "hello", "fix", "rice", "type", "chicken",
                                           "there", "and", "back", "ring", "blink", "grass", "fly", "sprint", "attack"};

    public static String get(int index) {
        return words[index];
    }
}
