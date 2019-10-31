package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Opens the calendar in a separate window for screenshot purposes
 */
public class EventScheduleWindow extends UiPart<Stage> {
    private static final String FXML = "EventScheduleWindow.fxml";
    private static final Logger logger = LogsCenter.getLogger(EventScheduleWindow.class);

    @FXML
    private BorderPane eventScheduleBorderPane;

    public EventScheduleWindow(Stage root, Region scheduleRegion) {
        super(FXML, root);
        eventScheduleBorderPane.setCenter(scheduleRegion);
    }

    /**
     * Shows the event schedule window
     */
    public void show() {
        logger.fine("Showing EventSchedule...");
        getRoot().setFullScreen(true);
        getRoot().centerOnScreen();
        getRoot().setFullScreenExitHint("Taking Screenshot of Schedule");
        getRoot().show();
    }

    /**
     * Close the event schedule window
     */
    public void close() {
        getRoot().setFullScreen(false);
        getRoot().close();
    }

    /**
     * Take snapshot of the event schedule window
     * @return a writableImage object which represents the window and can be saved into various file types
     */
    public WritableImage takeSnapShot() {
        return getRoot().getScene().getRoot().snapshot(new SnapshotParameters(), null);
    }
}
