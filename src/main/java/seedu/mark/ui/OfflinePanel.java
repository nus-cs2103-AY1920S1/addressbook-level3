package seedu.mark.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.mark.commons.core.LogsCenter;

public class OfflinePanel extends UiPart<Region> {

    private static final String FXML = "OfflinePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private TextField dummyField;

    public OfflinePanel() {
        //TODO: init any necessary construct
        super(FXML);

        dummyField.setText("This is a dummy offline tab. Please change me /. .\\|||");
    }
}
