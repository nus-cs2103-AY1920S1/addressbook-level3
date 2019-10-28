package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.ReadOnlyPerformance;
import seedu.address.model.performance.Event;

/**
 * UI component that is displayed when the command to view performance is issued.
 */
public class PerformancePanel extends UiPart<Region> {

    private static final String FXML = "PerformancePanel.fxml";

    @FXML
    private ListView performanceListView;

    public PerformancePanel(ReadOnlyPerformance performance) {
        super(FXML);
        for (Event event : performance.getPerformance()) {
            performanceListView.getItems().add(event.getName());
        }
    }

}
