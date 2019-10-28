package seedu.algobase.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

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

    /**
     * Returns true if two lists have the same content with the same order.
     * @param first list to compare
     * @param second list to compare
     */
    public static boolean compareTwoLists(List first, List second) {
        if (first.size() != second.size()) {
            return false;
        } else {
            for (int i = 0; i < first.size(); i = i + 1) {
                if (!first.get(i).equals(second.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Returns true if two {@code Optional} are both present and have the same content or are both empty.
     * @param first optional object
     * @param second optional object
     */
    public static boolean optionalEquals(Optional first, Optional second) {
        if (first.isPresent()) {
            return second.isPresent() && first.get().equals(second.get());
        } else {
            return second.isEmpty();
        }
    }
}
