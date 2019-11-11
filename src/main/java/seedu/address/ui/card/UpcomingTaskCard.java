package seedu.address.ui.card;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.UiPart;

//@@author Kyzure
/**
 * An UI component that displays information of a {@code Event}.
 */
public class UpcomingTaskCard extends UiPart<Region> {

    private static final String FXML = "UpcomingTaskCard.fxml";

    @FXML
    private Label upcomingTaskName;

    @FXML
    private Label upcomingWeekDay;

    @FXML
    private Label upcomingDay;

    /**
     * Constructor for the UpcomingTaskCard, which displays the information of a particular task.
     *
     * @param task The given event.
     */
    public UpcomingTaskCard(TaskSource task) {
        super(FXML);
        upcomingTaskName.setText(task.getDescription());
        upcomingWeekDay.setText(task.getDueDate().getEnglishWeekDay());
        upcomingDay.setText(String.valueOf(task.getDueDate().getDay()));
        upcomingTaskName.setMinHeight(Region.USE_PREF_SIZE);
    }
}
