package seedu.address.ui.panel.log;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.ui.UiPart;

//@@author Kyzure
/**
 * An Ui that stores the logged feedback from the program to the user.
 */
public class LogPanel extends UiPart<Region> {

    private static final String FXML = "LogPanel.fxml";

    @FXML
    private VBox boxLog;

    @FXML
    private ScrollPane scrollLog;

    /**
     * Constructor for the LogPanel. Sets the size of the LogPanel to the user's screen size.
     */
    public LogPanel() {
        super(FXML);
    }

    /**
     * Creates an instance of LogBox.
     * @param feedbackToUser To provide the Log Box the feedback to the user.
     */
    public void createLogBox(String feedbackToUser, String color) {
        requireNonNull(feedbackToUser);
        LogBox logBox = new LogBox(feedbackToUser, color);
        boxLog.getChildren().add(logBox.getRoot());
    }


}
