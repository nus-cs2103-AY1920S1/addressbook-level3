package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * UI component that displays total number of records on a specified date.
 */
public class PerformanceStats extends UiPart<Region> {

    private static final String FXML = "PerformanceStats.fxml";

    @FXML
    private Label totalRecords;

    public PerformanceStats(int numRecords) {
        super(FXML);
        totalRecords.setText("Total Records: " + numRecords);
    }
}
