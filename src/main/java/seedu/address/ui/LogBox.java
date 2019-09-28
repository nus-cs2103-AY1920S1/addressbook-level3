package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class LogBox extends UiPart<Region> {

    private static final String FXML = "LogBox.fxml";

    @FXML
    private Label box;

    public LogBox(String feedbackToUser) {
        super(FXML);
        box.setText(feedbackToUser);
    }
}
