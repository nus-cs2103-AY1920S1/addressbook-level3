package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

import seedu.address.MainApp;

/**
 * A container for App specific utility functions
 */
public class AppUtil {

    public static Image getImage(String imagePath) {
        requireNonNull(imagePath);
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    /**
     * Returns an {@link Image} with a stored absoluteImagePath url.
     *
     * @param absoluteImagePath The absolute image path to use.
     * @return The javafx {@link Image} instance.
     * @throws IllegalArgumentException If the provided {@code absoluteImagePath} is invalid.
     */
    public static Image getAbsoluteImage(String absoluteImagePath) throws FileNotFoundException {
        requireNonNull(absoluteImagePath);
        FileInputStream fileInputStream = new FileInputStream(absoluteImagePath);
        return new Image(fileInputStream);
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
     * Checks for null equality or object equality between two {@link Object}s.
     *
     * @param obj1 First object to check.
     * @param obj2 Second object to check.
     * @return True if both objects are null or {@code obj1.equals(obj2) == true}.
     */
    public static boolean isBothNullOrEqual(Object obj1, Object obj2) {
        return obj1 == null ? obj2 == null : obj1.equals(obj2);
    }
}
