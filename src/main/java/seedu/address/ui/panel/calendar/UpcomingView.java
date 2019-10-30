package seedu.address.ui.panel.calendar;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import seedu.address.model.CalendarDate;
import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.UiPart;
import seedu.address.ui.card.UpcomingEventCard;
import seedu.address.ui.card.UpcomingTaskCard;


/**
 * An UI that represents the upcoming event or task displayed to the user.
 */
public class UpcomingView extends UiPart<Region> {

    private static final String FXML = "UpcomingView.fxml";
    private Integer totalDisplays;

    @FXML
    private GridPane upcomingViewBase;

    @FXML
    private Label upcomingViewTitle;

    @FXML
    private VBox upcomingList;

    public UpcomingView(List<Object> eventTaskList) {
        super(FXML);
        CalendarDate calendarDate = CalendarDate.now();
        upcomingViewTitle.setText("Upcoming in " + calendarDate.getEnglishMonth());
        resizeUpcomingView(eventTaskList);
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
            if (index >= totalDisplays) {
                return;
            }
            if (source instanceof EventSource) {
                EventSource event = (EventSource) source;
                UpcomingEventCard eventCard = new UpcomingEventCard(event);
                upcomingList.getChildren().add(eventCard.getRoot());
                index++;
            } else if (source instanceof TaskSource) {
                TaskSource task = (TaskSource) source;
                UpcomingTaskCard taskCard = new UpcomingTaskCard(task);
                upcomingList.getChildren().add(taskCard.getRoot());
                index++;
            }
        }
    }

    /**
     * Re-sizes the upcoming to fit the amount of displays of the UpcomingEventCard.
     *
     * @param eventTaskList The list of events and tasks to change the upcoming view.
     */
    public void resizeUpcomingView(List<Object> eventTaskList) {
        totalDisplays = (int) (upcomingViewBase.getHeight() / UpcomingEventCard.UPCOMING_CARD_HEIGHT) - 2;
        onChange(eventTaskList);
    }
}
