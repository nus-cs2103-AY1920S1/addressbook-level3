package io.xpire.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
    //Reused from
    //https://github.com/crwohlfeil/damerau-levenshtein/blob/master/src/main/java/com/codeweasel/DamerauLevenshtein.java
    //with minor modifications
    /**
     * Returns the Levenshtein Distance between strings s1 and s2 (how different they are from each other).
     * If returns 0, the strings are same.
     * If returns 1, that means either a character is added, removed or replaced.
     * @param source the first string
     * @param target the second string
     * @return The Levenshtein Distance between the two strings.
     */
    public static int computeDistance (String source, String target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Parameter must not be null");
        }
        int sourceLength = source.length();
        int targetLength = target.length();
        if (sourceLength == 0) {
            return targetLength;
        }
        if (targetLength == 0) {
            return sourceLength;
        }
        int[][] dist = new int[sourceLength + 1][targetLength + 1];
        for (int i = 0; i < sourceLength + 1; i++) {
            dist[i][0] = i;
        }
        for (int j = 0; j < targetLength + 1; j++) {
            dist[0][j] = j;
        }
        for (int i = 1; i < sourceLength + 1; i++) {
            for (int j = 1; j < targetLength + 1; j++) {
                int cost = source.charAt(i - 1) == target.charAt(j - 1) ? 0 : 1;
                dist[i][j] = Math.min(Math.min(dist[i - 1][j] + 1, dist[i][j - 1] + 1), dist[i - 1][j - 1] + cost);
                if (i > 1 && j > 1
                        && source.charAt(i - 1) == target.charAt(j - 2)
                        && source.charAt(i - 2) == target.charAt(j - 1)) {
                    dist[i][j] = Math.min(dist[i][j], dist[i - 2][j - 2] + cost);
                }
            }
        }
        return dist[sourceLength][targetLength];
        //@@author
    }

    /**
     * Returns suggestions of correct alternatives for an invalid word entered.
     * @param invalidWord The invalid word entered.
     * @param set The set of correct alternatives.
     * @param limit The maximum degree of differences to which is accepted.
     * @return Suggestions that are the most appropriate replacements for the word entered.
     */
    public static String getSuggestions(String invalidWord, Set<String> set, int limit) {
        StringBuilder matches = new StringBuilder();
        TreeMap<Integer, LinkedList<String>> allMatches = new TreeMap<>();
        for (String s : set) {
            if (s.startsWith(invalidWord)) {
                allMatches.putIfAbsent(-1, new LinkedList<>());
                allMatches.get(-1).add(s);
            } else if (!allMatches.containsValue(s)) {
                int i = StringUtil.computeDistance(s.toLowerCase(), invalidWord.toLowerCase());
                if (i <= limit) {
                    allMatches.putIfAbsent(i, new LinkedList<>());
                    allMatches.get(i).add(s);
                }
            }
        }
        for (Map.Entry<Integer, LinkedList<String>> entry : allMatches.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue());
            matches.append(entry.getValue());
        }
        return matches.toString();
    }

    /**
     * Converts string to sentence-case (first character upper-case, the rest lower-case).
     *
     * @param string String to be converted.
     * @return new String in sentence case.
     */
    public static String convertToSentenceCase(String string) {
        requireNonNull(string);
        String newString;
        switch (string.length()) {
        case 0:
            newString = "";
            break;
        case 1:
            newString = string.toUpperCase();
            break;
        default:
            newString = Character.toUpperCase(string.charAt(0)) + string.substring(1).toLowerCase();
        }
        return newString;
    }
}
