package seedu.address.ui.panel.log;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

//@@author Kyzure
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
    public LogBox(String feedbackToUser, String color) {
        super(FXML);
        box.setText(feedbackToUser);
        box.setStyle("-fx-background-color: " + color);
    }
}
