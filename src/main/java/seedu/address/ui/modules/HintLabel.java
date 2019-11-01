package seedu.address.ui.modules;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * UI Class that represents the region for the hints to be displayed on.
 */
public class HintLabel extends UiPart<Region> {
    private static final String FXML = "HintLabel.fxml";

    @FXML
    private Label hintLabel;

    public HintLabel() {
        super(FXML);
        hintLabel.setWrapText(true);
    }

    public void updateHintLabel(String string) {
        hintLabel.setText(string);
    }

}
