package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * The UI component that displays an error message when no training or performance record is
 * found on a particular date.
 */
public class ErrorMessageLabel extends UiPart<Region> {

    private static final String FXML = "ErrorMessage.fxml";

    @FXML
    private Label message;

    public ErrorMessageLabel(String text) {
        super(FXML);
        message.setText(text);
    }
}
