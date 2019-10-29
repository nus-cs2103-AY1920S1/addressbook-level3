package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyPerformance;
import seedu.address.model.performance.Event;

public class RecordsPanel extends UiPart<Region> {

    @FXML
    private Label eventName;

    private static final String FXML = "RecordsPanel.fxml";
    private ReadOnlyPerformance performance;
    private Event event;

    public RecordsPanel(Model model, String eventName) {
        super(FXML);
        this.performance = model.getPerformance();
        this.eventName.setText(eventName);
    }

    private void setEvent(String eventName) {
        for (Event event : performance.getPerformance()) {
            if (event.getName().equals(eventName.toLowerCase())) {
                this.event = event;
            }
        }
        this.eventName.setText(event.getName());

    }


}
