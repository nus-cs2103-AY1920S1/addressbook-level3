package budgetbuddy.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.format.DateTimeFormatter;

import budgetbuddy.MainApp;
import javafx.scene.image.Image;

/**
 * A container for App specific utility functions
 */
public class AppUtil {
    private static final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("d/M/yyyy");

    public static Image getImage(String imagePath) {
        requireNonNull(imagePath);
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    /**
     * Returns a {@code DateTimeFormatter} for use in displaying/parsing dates.
     * The primary purpose of this method is to standardise date display across the app.
     */
    public static DateTimeFormatter getDateFormatter() {
        return dateFormatter;
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
}
