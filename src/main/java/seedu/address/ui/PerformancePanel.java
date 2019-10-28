package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyPerformance;
import seedu.address.model.performance.Event;

/**
 * UI component that is displayed when the command to view performance is issued.
 */
public class PerformancePanel extends UiPart<Region> {

    private static final String FXML = "PerformancePanel.fxml";
    private ReadOnlyPerformance performance;

    @FXML
    private VBox eventList;

    public PerformancePanel(Model model) {
        super(FXML);
        this.performance = model.getPerformance();
        populateEventList();
    }

    /**
     * Fills up the VBox with events.
     */
    public void populateEventList() {
        for (Event event : performance.getPerformance()) {
            EventRow eventRow = new EventRow(event.getName());
            eventList.getChildren().add(eventRow.getRoot());
        }
    }

}
