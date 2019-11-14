package organice.commons.util;

import static java.util.Objects.requireNonNull;
import static organice.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the phrase in {@code phrase}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "abc DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param phrase cannot be null, cannot be empty, can be a multi-word string
     */
    public static boolean containsPhraseIgnoreCase(String sentence, String phrase) {
        requireNonNull(sentence);
        requireNonNull(phrase);

        phrase = phrase.replace("\n", " ").trim();
        String[] wordsInPhrase = phrase.split("\\s+");
        checkArgument(!phrase.isEmpty() && wordsInPhrase.length > 0,
                "Words parameter cannot be empty");

        String preppedSentence = sentence.trim();
        String[] wordsInPreppedSentence = preppedSentence.split(",+|\\s+");

        return Arrays.stream(wordsInPhrase).reduce(true, (
                arePreviousWordsMatched, phraseWord) -> arePreviousWordsMatched && Arrays
                .stream(wordsInPreppedSentence).anyMatch(phraseWord::equalsIgnoreCase), (
                        arePrevWordsMatched, isNextWordHaveMatch) -> arePrevWordsMatched || isNextWordHaveMatch);
    }

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        checkArgument(word.trim()
                .split("\\s+").length == 1, "Word parameter should be a single word");
        return containsPhraseIgnoreCase(sentence, word);
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
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
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

    // Algorithm taken from https://semanti.ca/blog/?an-introduction-into-approximate-string-matching.
    // Java code is original work.
    /**
     * Calculates the Levenshtein Distance (edit distance) between the given {@code String firstString} and
     * {@code String secondString}. Levenshtein Distance is the number of character edits required to morph one
     * string into another.
     */
    public static int calculateLevenshteinDistance(String firstString, String secondString) {
        requireNonNull(firstString);
        requireNonNull(secondString);

        int firstStringLength = firstString.length();
        int secondStringLength = secondString.length();

        checkArgument(firstStringLength != 0 && secondStringLength != 0);

        char[] pkChars = firstString.toCharArray();
        char[] paChars = secondString.toCharArray();

        int[][] costMatrix = new int[firstStringLength][secondStringLength];
        for (int i = 0; i < firstStringLength; i++) {
            for (int j = 0; j < secondStringLength; j++) {
                costMatrix[i][j] = pkChars[i] == paChars[j] ? 0 : 1;
            }
        }

        int[][] levenshteinMatrix = new int [firstStringLength + 1][secondStringLength + 1];
        // Initialise first row and col to be in range 0..length
        for (int r = 0; r < firstStringLength + 1; r++) {
            levenshteinMatrix[r][0] = r;
        }
        for (int c = 0; c < secondStringLength + 1; c++) {
            levenshteinMatrix[0][c] = c;
        }

        // Setting the distance
        for (int r = 1; r < firstStringLength + 1; r++) {
            for (int c = 1; c < secondStringLength + 1; c++) {
                int cellAbovePlusOne = levenshteinMatrix[r - 1][c] + 1;
                int cellLeftPlusOne = levenshteinMatrix[r][c - 1] + 1;
                int cellLeftDiagPlusCost = levenshteinMatrix[r - 1][c - 1] + costMatrix[r - 1][c - 1];
                levenshteinMatrix[r][c] =
                        Integer.min(cellAbovePlusOne, Integer.min(cellLeftPlusOne, cellLeftDiagPlusCost));
            }
        }
        return levenshteinMatrix[firstStringLength][secondStringLength];
    }
}
