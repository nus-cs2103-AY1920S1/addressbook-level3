package seedu.exercise.commons.core;

/**
 * Encapsulates the different validation regex that help to check if an input is correct.
 */
public class ValidationRegex {
    public static final String ONLY_NON_NEGATIVE_INTEGER = "\\d+";
    public static final String ONLY_NON_NEGATIVE_NUMBERS = "\\d+(\\.\\d+)?";
    public static final String ONLY_ALPHABETS_AND_SPACE = "^[ A-Za-z]+$";
    public static final String ONLY_ALPHABETS = "^[A-Za-z]+$";
    public static final String ONLY_ALPHABETS_NUMBERS_AND_SPACE = "^[ A-Za-z0-9]+$";
}
