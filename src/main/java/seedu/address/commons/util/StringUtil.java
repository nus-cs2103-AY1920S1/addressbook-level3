package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Set;

import seedu.address.model.quiz.tag.Tag;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

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
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     * @param allowTypo whether the user may type a typo word.
     */
    public static boolean containsQuizWordMatch(String sentence, String word, boolean allowTypo) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        if (Arrays.stream(wordsInPreppedSentence).anyMatch(preppedWord::equalsIgnoreCase)) {
            return true;
        } else if (allowTypo) {
            String firstLetter = Character.toString(preppedWord.charAt(0)).toLowerCase();
            String lastLetter = Character.toString(preppedWord.charAt(preppedWord.length() - 1)).toLowerCase();

            for (int i = 0; i < wordsInPreppedSentence.length; i++) {
                wordsInPreppedSentence[i] = wordsInPreppedSentence[i].replaceAll("[^A-Za-z0-9]", "");
            }

            if (Arrays.stream(wordsInPreppedSentence)
                .anyMatch(text -> text.startsWith(firstLetter) && text.endsWith(lastLetter))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns true if the {@code tagList} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param tagList cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     * @param allowTypo whether the user may type a typo word.
     */
    public static boolean containsTagQuizIgnoreCase(Set<Tag> tagList, String word, boolean allowTypo) {
        for (Tag myTag : tagList) {
            String preppedWord = word.trim();
            String comparedWord = myTag.tagName.toLowerCase();

            if (comparedWord.equals(preppedWord.toLowerCase())) {
                return true;
            } else if (allowTypo) {
                String firstLetter = Character.toString(preppedWord.charAt(0)).toLowerCase();
                String lastLetter = Character.toString(preppedWord.charAt(preppedWord.length() - 1)).toLowerCase();
                if (comparedWord.startsWith(firstLetter) && comparedWord.endsWith(lastLetter)) {
                    return true;
                }
            }
        }
        return false;
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
