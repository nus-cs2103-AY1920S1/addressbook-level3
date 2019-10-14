package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * An UI component that displays the feedback to the user.
 */
public class LogBox extends UiPart<Region> {

    private static final String FXML = "LogBox.fxml";

    @FXML
    private Label box;

    /**
     * Constructor for LogBox. It is a dialog box that contains the feedback from the program to the user.
     * @param feedbackToUser The feedback from the program to the user.
     */
    public LogBox(String feedbackToUser) {
        super(FXML);
        box.setText(feedbackToUser);
    }
}
