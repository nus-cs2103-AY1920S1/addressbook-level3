package io.xpire.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {
    /**
     * Returns true if the {@code sentence} contains the {@code phrase}.
     *   Ignores case, allows partial phrase match.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABcdef", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "abc d") == true
     *       </pre>
     * @param sentence cannot be null
     * @param phrase cannot be null and cannot be empty.
     */
    public static boolean containsPhraseIgnoreCase(String sentence, String phrase) {
        requireNonNull(sentence);
        requireNonNull(phrase);

        String trimmedPhrase = phrase.trim();
        AppUtil.checkArgument(!trimmedPhrase.isEmpty(), "Phrase parameter cannot be empty");

        return sentence.toLowerCase().contains(trimmedPhrase.toLowerCase());
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer.
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>.
     * Will return false for any other non-null string input.
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters).
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Returns true if {@code s} represents a non-negative integer.
     * e.g. 0, 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>.
     * Will return false for any other non-null string input.
     * e.g. empty string, "-1", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonNegativeInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value >= 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    //@@author febee99-reused
    //Reused from https://bakedcircuits.wordpress.com/2013/08/10/simple-spell-checker-in-java/
    /**
     * Returns the Levenshtein Distance between strings s1 and s2 (how different they are from each other).
     * If returns 0, the strings are same.
     * If returns 1, that means either a character is added, removed or replaced.
     * @param s1 the first string
     * @param s2 the second string
     * @return The Levenshtein Distance between the two strings.
     */
    public static int computeDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }

    /**
     * Returns suggestions of correct alternatives for an invalid word entered.
     * @param invalidWord The invalid word entered.
     * @param list The list of correct alternatives.
     * @return Suggestions that are the most appropriate replacements for the word entered.
     */
    public static String getSuggestions(String invalidWord, String[] list) {
        StringBuilder matches = new StringBuilder();
        TreeMap<Integer, LinkedList<String>> allMatches = new TreeMap<>();
        for (String s: list) {
            if (s.contains(invalidWord)) {
                allMatches.putIfAbsent(-1, new LinkedList<>());
                allMatches.get(-1).add(s);
            } else {
                int i = StringUtil.computeDistance(s, invalidWord);
                if (i < 3 && !allMatches.containsValue(s)) {
                    allMatches.putIfAbsent(i, new LinkedList<>());
                    allMatches.get(i).add(s);
                }
            }
        }
        for (Map.Entry<Integer, LinkedList<String>> entry : allMatches.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue());
            matches.append(entry.getValue()).append(" / ");
        }
        if (matches.length() > 0) {
            matches.setLength(matches.length() - 3);
        }
        return matches.toString();
    }
}
