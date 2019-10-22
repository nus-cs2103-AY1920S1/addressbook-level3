package seedu.algobase.commons.util;

import static java.util.Objects.requireNonNull;

import javafx.scene.image.Image;
import seedu.algobase.MainApp;

/**
 * A container for App specific utility functions
 */
public class AppUtil {

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

    public static String getClassStringField(Class targetClass, String fieldName) {
        try {
            String result = (String) targetClass.getField(fieldName).get("");
            return result;
        } catch (NoSuchFieldException e) {
            throw new AssertionError("Class " + targetClass.getName()
                + "doesn't have " + fieldName + " field.");
        } catch (IllegalAccessException e) {
            throw new AssertionError("Class " + targetClass.getName()
                + "has non-public " + fieldName + ".");
        }
    }
}
