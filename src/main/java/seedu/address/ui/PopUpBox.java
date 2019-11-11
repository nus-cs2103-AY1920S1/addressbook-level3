package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

//@@author Kyzure
/**
 * An UI component that displays the feedback to the user.
 */
public class PopUpBox extends UiPart<Region> {

    private static final String FXML = "PopUpBox.fxml";

    @FXML
    private Label popUp;

    /**
     * Constructor for PopUpBox. Has almost the same features as LogBox
     */
    public PopUpBox(String feedbackToUser, String color) {
        super(FXML);
        popUp.setText(feedbackToUser);
        popUp.setStyle("-fx-background-color: " + color);
    }
}
