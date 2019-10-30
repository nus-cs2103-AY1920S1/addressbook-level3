package budgetbuddy.commons.util;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;

import budgetbuddy.MainApp;
import javafx.scene.image.Image;

/**
 * A container for App specific utility functions
 */
public class AppUtil {
    private static final ThreadLocal<SimpleDateFormat> dateFormat = ThreadLocal.withInitial(() -> {
        SimpleDateFormat format = new SimpleDateFormat("d/M/yy");
        format.setLenient(false);
        return format;
    });

    public static Image getImage(String imagePath) {
        requireNonNull(imagePath);
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    /**
     * Returns a {@code SimpleDateFormat} for use in displaying/parsing dates.
     * The primary purpose of this method is to standardise date display across the app.
     */
    public static SimpleDateFormat getDateFormat() {
        return dateFormat.get();
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
