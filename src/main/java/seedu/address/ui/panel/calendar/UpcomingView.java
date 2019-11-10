package seedu.address.ui.panel.calendar;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import seedu.address.model.CalendarDate;
import seedu.address.model.DateTime;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.UiPart;
import seedu.address.ui.card.UpcomingEventCard;
import seedu.address.ui.card.UpcomingTaskCard;

//@@author Kyzure
/**
 * An UI that represents the upcoming event or task displayed to the user.
 */
public class UpcomingView extends UiPart<Region> {

    private static final String FXML = "UpcomingView.fxml";

    private CalendarDate currentDate;

    @FXML
    private GridPane upcomingViewBase;

    @FXML
    private Label upcomingViewTitle;

    @FXML
    private VBox upcomingList;

    public UpcomingView(List<Object> eventTaskList) {
        super(FXML);
        currentDate = CalendarDate.now();
        upcomingViewTitle.setText("Upcoming in " + currentDate.getEnglishMonth());
        onChange(eventTaskList);
    }

    /**
     * Updates the upcoming list to hold up to a certain amount of events or task.
     *
     * @param eventTaskList The given list of tasks and events.
     */
    public void onChange(List<Object> eventTaskList) {
        int index = 0;
        upcomingList.getChildren().clear();

        for (Object source : eventTaskList) {
            if (source instanceof EventSource) {
                EventSource event = (EventSource) source;
                DateTime eventDate = event.getStartDateTime();
                if (currentDate.sameMonthYear(eventDate.getMonth(), eventDate.getYear())
                        && currentDate.getDay() <= eventDate.getDay()) {
                    UpcomingEventCard eventCard = new UpcomingEventCard(event);
                    upcomingList.getChildren().add(eventCard.getRoot());
                    index++;
                }
            } else if (source instanceof TaskSource) {
                TaskSource task = (TaskSource) source;
                DateTime taskDate = task.getDueDate();
                if (taskDate != null
                        && currentDate.sameMonthYear(taskDate.getMonth(), taskDate.getYear())
                        && currentDate.getDay() <= taskDate.getDay()) {
                    UpcomingTaskCard taskCard = new UpcomingTaskCard(task);
                    upcomingList.getChildren().add(taskCard.getRoot());
                    index++;
                }
            }
        }
    }
}
