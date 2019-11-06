package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.scene.image.Image;
import seedu.address.MainApp;

/**
 * A container for App specific utility functions
 */
public class AppUtil {

    private static final ScheduledExecutorService dataUpdateControl = Executors.newScheduledThreadPool(1);

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
     * Schedules the updating of in-memory data after a {@code task} is executed.
     */
    public static void scheduleDataUpdate(Runnable task) {
        dataUpdateControl.schedule(task, 2, TimeUnit.SECONDS);
    }

    /**
     * Shutdown the thread that updated data when app exits.
     */
    public static void shutDownDataWorker() {
        dataUpdateControl.shutdown();
    }
}
