package seedu.address.calendar.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import seedu.address.ui.UiPart;


public class ResultDisplay extends UiPart<Region> {
    private static final String FXML = "ResultDisplay.fxml";
    @FXML
    Label response;

    // todo: use what's given but remove border

    public ResultDisplay() {
        super(FXML);
    }
}
