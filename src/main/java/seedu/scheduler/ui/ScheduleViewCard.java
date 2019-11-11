package seedu.scheduler.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * An UI component that displays information of a {@code ScheduleViewCard}.
 */
public class ScheduleViewCard extends UiPart<Region> {

    private static final String FXML = "ScheduleViewCard.fxml";
    public final ScheduleView schedule;

    @FXML
    private StackPane scheduleViewPane;

    public ScheduleViewCard(ScheduleView schedule) {
        super(FXML);
        this.schedule = schedule;
        scheduleViewPane.getChildren().setAll(schedule.getRoot());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof ScheduleViewCard)) {
            return false;
        }

        // state check
        ScheduleViewCard card = (ScheduleViewCard) other;
        return schedule.equals(card.schedule);
    }
}

