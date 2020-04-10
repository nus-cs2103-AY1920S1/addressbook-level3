package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * The UI component that displays event names.
 */
public class PerformanceTableHeader extends UiPart<Region> {

    private static final String FXML = "PerformanceTableHeader.fxml";

    @FXML
    private Label tableHeader;

    public PerformanceTableHeader(String eventName) {
        super(FXML);
        tableHeader.setText(eventName);
    }
}
