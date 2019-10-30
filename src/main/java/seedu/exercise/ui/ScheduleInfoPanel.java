package seedu.exercise.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.exercise.model.resource.Schedule;

/**
 * Information panel for displaying schedule type information
 */
public class ScheduleInfoPanel extends UiPart<Region> {

    private static final String FXML = "ScheduleInfoPanel.fxml";
    private static final String DATE_DISPLAY_TEXT = "Scheduled on: %1$s";

    @FXML
    private Label dateOfSchedule;

    @FXML
    private StackPane regimePanelPlaceholder;

    public ScheduleInfoPanel(Schedule schedule) {
        super(FXML);

        dateOfSchedule.setText(String.format(DATE_DISPLAY_TEXT,
                schedule.getDate().toString()));

        RegimeInfoPanel infoPanel = new RegimeInfoPanel(schedule.getRegime());
        regimePanelPlaceholder.getChildren().add(infoPanel.getRoot());
    }
}
