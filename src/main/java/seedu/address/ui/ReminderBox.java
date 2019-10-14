package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ReminderBox extends UiPart<Region> {

    private static final String FXML = "ReminderBox.fxml";

    @FXML
    private TextArea reminderBox;

    public ReminderBox() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        reminderBox.setText(feedbackToUser);
    }

}