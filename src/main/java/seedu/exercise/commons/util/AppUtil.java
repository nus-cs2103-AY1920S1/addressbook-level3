package seedu.exercise.commons.util;

import static java.util.Objects.requireNonNull;

import javafx.scene.image.Image;
import seedu.exercise.MainApp;
import seedu.exercise.commons.core.State;

/**
 * A container for App specific utility functions
 */
public class AppUtil {

    public static final String UNEXPECTED_STATE = "State of program is not %1$s";

    public static Image getImage(String imagePath) {
        requireNonNull(imagePath);
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    /**
     * Checks that {@code condition} is true. Used for validating arguments to methods.
     *
     * @throws IllegalArgumentException if {@code condition} is false.
     */
    public static void checkArgument(Boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks that {@code condition} is true. Used for validating arguments to methods.
     *
     * @throws IllegalArgumentException with {@code errorMessage} if {@code condition} is false.
     */
    public static void checkArgument(Boolean condition, String errorMessage) {
        if (!condition) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Checks that {@code state} of the program is valid. Used for validating a particular
     * state of the program in a method.
     *
     * @throws IllegalStateException if {@code MainApp's} state is not as expected
     */
    public static void requireMainAppState(State state) {
        requireNonNull(state);
        if (MainApp.getState() != state) {
            throw new IllegalStateException(String.format(UNEXPECTED_STATE, state.toString()));
        }
    }
}
