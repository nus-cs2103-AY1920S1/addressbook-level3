package io.xpire.commons.util;

import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import io.xpire.commons.core.Messages;
import io.xpire.model.item.Name;
import io.xpire.model.tag.Tag;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    private static final String UNSIGNED_NUMERIC_VALIDATION_REGEX = "^[1-9][0-9]*$";
    private static final String NUMERIC_VALIDATION_REGEX = "^[+-]*[0-9]*$";


    //@@author JermyTan
    /**
     * Returns true if the {@code sentence} contains the {@code phrase}.
     *   Ignores case, allows partial phrase match.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABcdef", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "abc d") == true
     *       </pre>
     *
     * @param sentence cannot be null
     * @param phrase cannot be null and cannot be empty.
     */
    public static boolean containsPhraseIgnoreCase(String sentence, String phrase) {
        requireAllNonNull(sentence, phrase);

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

    //@author liawsy
    /**
     * Returns true if {@code s} is an unsigned number with no leading 0s.
     *
     * @return true if {@code s} matches validation regex.
     */
    public static boolean isUnsignedNumericWithoutLeadingZeroes(String s) {
        return s.matches(UNSIGNED_NUMERIC_VALIDATION_REGEX);
    }

    /**
     * Returns true if {@code s} is numeric.
     *
     * @return true if {@code s} matches validation regex.
     */
    public static boolean isNumeric(String s) {
        return s.matches(NUMERIC_VALIDATION_REGEX);
    }
    //@author

    /**
     * Returns true if {@code s} represents a non-negative integer.
     * e.g. 0, 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>.
     * Will return false for any other non-null string input.
     * e.g. empty string, "-1", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     *
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

    /**
     * Returns the byte array representing the QR code-encoded text.
     *
     * @param text The string to be encoded.
     * @param length The size of the QR code
     * @return The byte array of the QR code-encoded text
     */
    public static byte[] getQrCode(String text, int length) {
        requireNonNull(text);
        assert !text.isEmpty() : "Text cannot be empty";
        assert length > 0 : "Length must be more than 0";

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, length, length);
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", pngOutputStream);
            return pngOutputStream.toByteArray();
        } catch (WriterException | IOException e) {
            return new byte[0];
        }
    }

    //@@author

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer.
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>.
     * Will return false for any other non-null string input.
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters).
     *
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
     * Returns true if {@code s} represents an integer smaller than or equal to the given maximum value {@code max}.
     * Returns false for any other non-null string input.
     *
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isExceedingMaxValue(String s, int max) {
        try {
            int value = Integer.parseInt(s);
            return value > max;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    //@@author Kalsyc
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

    //@@author febee99-reused
    //Reused from
    //https://github.com/crwohlfeil/damerau-levenshtein/blob/master/src/main/java/com/codeweasel/DamerauLevenshtein.java
    //with minor modifications
    /**
     * Returns the Levenshtein Distance between strings source and target.
     * How many edits are needed to change source into target.
     * If returns 0, the strings are same.
     * If returns 1, that means either a character is added, removed, replaced or swapped.
     *
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
                //minimum cost between insertion, deletion, replacement
                dist[i][j] = Math.min(Math.min(dist[i - 1][j] + 1, dist[i][j - 1] + 1), dist[i - 1][j - 1] + cost);
                //check for transpositions
                if (i > 1 && j > 1
                        && source.charAt(i - 1) == target.charAt(j - 2)
                        && source.charAt(i - 2) == target.charAt(j - 1)) {
                    dist[i][j] = Math.min(dist[i][j], dist[i - 2][j - 2] + cost);
                }
            }
        }
        return dist[sourceLength][targetLength];
    }

    //@@author febee99
    /**
     * Returns suggestions of alternatives for an invalid word entered.
     *
     * @param invalidWord The invalid word entered.
     * @param set The set of alternative words.
     * @param limit The maximum degree of differences between words compared which is accepted.
     * @return Suggestions that are the most appropriate replacements for the word entered.
     */
    public static String getSuggestions(String invalidWord, Set<String> set, int limit) {
        StringBuilder matches = new StringBuilder();
        TreeMap<Integer, TreeSet<String>> allMatches = new TreeMap<>();
        for (String s : set) {
            if (s.startsWith(invalidWord)) {
                allMatches.putIfAbsent(-1, new TreeSet<>());
                allMatches.get(-1).add(s);
            } else if (!allMatches.containsValue(s)) {
                int i = StringUtil.computeDistance(s.toLowerCase(), invalidWord.toLowerCase());
                if (i <= limit) {
                    allMatches.putIfAbsent(i, new TreeSet<>());
                    allMatches.get(i).add(s);
                }
            }
        }
        for (Map.Entry<Integer, TreeSet<String>> entry : allMatches.entrySet()) {
            //System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue());
            matches.append(entry.getValue());
        }
        return matches.toString();
    }

    /**
     * Returns a formatted string containing similar words to the word specified.
     *
     * @param word The word specified to find similar words for.
     * @param allWordsToCompare The set that contains all words to compare the word to.
     * @param limit The maximum degree of polarity between words acceptable.
     * @return The string which contains all similar words.
     */
    public static String findSimilar(String word, Set<String> allWordsToCompare, int limit) {
        if (!StringUtil.getSuggestions(word, allWordsToCompare, limit).isEmpty()) {
            return String.format(Messages.MESSAGE_SUGGESTIONS, getSuggestions(word, allWordsToCompare, limit));
        }
        return "";
    }

    /**
     * Returns all similar tags to the tag keyword specified.
     *
     * @param word The tag keyword specified to find similar tags for.
     * @param allTags The set that contains all tags to compare the word to.
     * @return The string which contains all similar tags.
     */
    public static String findSimilarItemTags(String word, Set<Tag> allTags) {
        return StringUtil.findSimilar(word, allTags.stream()
                                                   .map(Tag::toString)
                                                   .collect(Collectors.toSet()), 3);
    }

    /**
     * Returns all similar xpireItem names to the search keyword specified.
     *
     * @param word The keyword specified to find similar items for.
     * @param allNames The set that contains all names to compare the word to.
     * @return The string which contains all similar names.
     */
    public static String findSimilarItemNames(String word, Set<Name> allNames) {
        return StringUtil.findSimilar(word, allNames.stream()
                                                    .map(Name::toString)
                                                    .map(x -> x.split("\\s+"))
                                                    .flatMap(Arrays::stream)
                                                    .collect(Collectors.toSet()), 1);
    }

}
