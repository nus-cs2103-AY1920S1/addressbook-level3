package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;

    public ResultDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        dialogContainer.getChildren().addAll(
                DialogBox.getElisaDialog(feedbackToUser)
        );
    }

    public void setMessageFromUser(String messageFromUser) {
        requireNonNull(messageFromUser);
        dialogContainer.getChildren().add(
                DialogBox.getUserDialog(messageFromUser)
        );
    }

}
