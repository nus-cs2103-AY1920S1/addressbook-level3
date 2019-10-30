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
     * Returns true if the {@code sentence} contains any word in {@code words}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "abc DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param words cannot be null, cannot be empty, can be a multi-word string
     */
    public static boolean containsWordsIgnoreCase(String sentence, String words) {
        requireNonNull(sentence);
        requireNonNull(words);

        words = words.replace("\n", " ").trim();
        String[] preppedWords = words.split("\\s+");
        checkArgument(!words.isEmpty() && preppedWords.length > 0, "Words parameter cannot be empty");

        String preppedSentence = sentence.trim();
        String[] wordsInPreppedSentence = preppedSentence.split(",+|\\s+");

        return Arrays.stream(preppedWords).reduce(false, (isAnyWordsMatched, word) -> isAnyWordsMatched
                || Arrays.stream(wordsInPreppedSentence).anyMatch(word::equalsIgnoreCase), (
                        isAnyWordsMatched, isNextWordHaveMatch) -> isAnyWordsMatched || isNextWordHaveMatch);
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
        return containsWordsIgnoreCase(sentence, word);
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
}
