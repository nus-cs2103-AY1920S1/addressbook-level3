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
}
