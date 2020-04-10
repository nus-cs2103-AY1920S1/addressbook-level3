package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * The UI component that displays a record for an event. A record includes a name and a timing.
 */
public class PerformanceTableContent extends UiPart<Region> {

    private static final String FXML = "PerformanceTableContent.fxml";

    @FXML
    private Label name;

    @FXML
    private Label timing;

    public PerformanceTableContent(String personName, String personTiming) {
        super(FXML);
        name.setText(personName);
        timing.setText(personTiming);
    }
}
