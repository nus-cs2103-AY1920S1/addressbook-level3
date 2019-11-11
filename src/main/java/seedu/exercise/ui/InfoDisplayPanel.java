package seedu.exercise.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Resource;
import seedu.exercise.model.resource.Schedule;

/**
 * Placeholder Panel for specific information displayed in center panel.
 */
public class InfoDisplayPanel extends UiPart<Region> {

    public static final String DEFAULT_MESSAGE = "Select an exercise/regime/schedule to display its info.";

    private static final Logger logger = LogsCenter.getLogger(InfoDisplayPanel.class);
    private static final String FXML = "InfoDisplayPanel.fxml";

    @FXML
    private StackPane infoPanelPlaceholder;

    public InfoDisplayPanel() {
        super(FXML);
    }

    /**
     * Updates the information displayed in the center panel of application.
     */
    public void update(Resource resource) {
        infoPanelPlaceholder.getChildren().clear();
        if (resource instanceof Exercise) {
            infoPanelPlaceholder.getChildren().add(new ExerciseInfoPanel((Exercise) resource).getRoot());
            logCurrentInfoShown("exercise");
        } else if (resource instanceof Regime) {
            infoPanelPlaceholder.getChildren().add(new RegimeInfoPanel((Regime) resource).getRoot());
            logCurrentInfoShown("regime");
        } else if (resource instanceof Schedule) {
            infoPanelPlaceholder.getChildren().add(new ScheduleInfoPanel((Schedule) resource).getRoot());
            logCurrentInfoShown("schedule");
        }
    }

    /**
     * Updates the information displayed in the center panel of application to show the default message.
     */
    public void showDefaultMessage() {
        infoPanelPlaceholder.getChildren().clear();
        infoPanelPlaceholder.getChildren().add(new Label(DEFAULT_MESSAGE));
        logCurrentInfoShown("default message");
    }

    private void logCurrentInfoShown(String infoShown) {
        logger.info("Info panel displaying " + infoShown);
    }
}
