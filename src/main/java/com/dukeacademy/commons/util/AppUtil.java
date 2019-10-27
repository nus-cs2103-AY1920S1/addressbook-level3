package com.dukeacademy.commons.util;

import static java.util.Objects.requireNonNull;

import com.dukeacademy.MainApp;

import javafx.scene.image.Image;

/**
 * A container for App specific utility functions
 */
public class AppUtil {

    /**
     * Gets image.
     *
     * @param imagePath the image path
     * @return the image
     */
    public static Image getImage(String imagePath) {
        requireNonNull(imagePath);
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    /**
     * Checks that {@code condition} is true. Used for validating arguments to methods.
     *
     * @param condition the condition
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
     * @param condition    the condition
     * @param errorMessage the error message
     * @throws IllegalArgumentException with {@code errorMessage} if {@code condition} is false.
     */
    public static void checkArgument(Boolean condition, String errorMessage) {
        if (!condition) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
