package seedu.address.ui.modules;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

public class HintLabel extends UiPart<Region> {
    private static final String FXML = "HintLabel.fxml";

    @FXML
    private Label hintLabel;

    public HintLabel() {
        super(FXML);
    }

    public void updateHintLabel(String string) {
        hintLabel.setText(string);
    }

}
