package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
     * Schedules the update of in-memory data after a {@code task} is executed.
     */
    public static void scheduleDataUpdate(Runnable task) {
        final ScheduledThreadPoolExecutor dataUpdateControl = new ScheduledThreadPoolExecutor(1);
        ScheduledFuture taskToComplete = dataUpdateControl.schedule(task, 2, TimeUnit.SECONDS);
        if (taskToComplete.isDone()) {
            shutDownDataWorker(dataUpdateControl);
        }
    }

    /**
     * Shutdown the thread that updated data when the task is done
     */
    private static void shutDownDataWorker(ScheduledThreadPoolExecutor dataUpdateControl) {
        dataUpdateControl.shutdown();
        try {
            if (!dataUpdateControl.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                dataUpdateControl.shutdownNow();
            }
        } catch (InterruptedException e) {
            dataUpdateControl.shutdownNow();
        }
    }
}
