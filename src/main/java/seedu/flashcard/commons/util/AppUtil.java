package seedu.flashcard.commons.util;

/**
 * A container for App specified utility functions
 */
public class AppUtil {

    /**
     * Checks that {@code condition} is true. Used for validating arguments to methods.
     * @throws IllegalArgumentException if {@code condition} is false.
     */
    public static void checkArgument(boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks that {@code condition} is true. Used for validating arguments to methods.
     * @throws IllegalArgumentException with {@code errorMessage} if {@code condition} is false.
     */
    public static void checkArgument(boolean condition, String errorMessage) {
        if (!condition) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
