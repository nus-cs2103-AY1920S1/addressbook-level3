package com.dukeacademy.commons.util;

import static com.dukeacademy.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import com.dukeacademy.commons.exceptions.IllegalValueException;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {
    private static final String VALID_WORD_REGEX = "[^\\s].*";

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word     cannot be null, cannot be empty, must be a single word
     * @return the boolean
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     *
     * @param t the t
     * @return the details
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
     *
     * @param s the s
     * @return the boolean
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
     * Returns the first word of a string.
     *
     * @param s the string to be processed.
     * @return the first word of the string.
     * @throws IllegalValueException if the string does not contain a valid word.
     */
    public static String getFirstWord(String s) throws IllegalValueException {
        String stripped = s.stripLeading();

        if (!stripped.matches(VALID_WORD_REGEX)) {
            throw new IllegalValueException("String is not a valid word.");
        }
        return stripped.split("[\\s]+", 2)[0].trim();
    }

    /**
     * Returns the a new string with the first word removed.
     *
     * @param s the string to be processed.
     * @return the remainder of the string after the first word is removed.
     * @throws IllegalValueException if the string does not contain a valid word.
     */
    public static String removeFirstWord(String s) throws IllegalValueException {
        String stripped = s.stripLeading();

        if (!stripped.matches(VALID_WORD_REGEX)) {
            throw new IllegalValueException("String is not a valid word.");
        }

        if (stripped.split("[\\s]+", 2).length == 1) {
            return "";
        } else {
            return stripped.split("[\\s]+", 2)[1].stripLeading();
        }
    }
}
