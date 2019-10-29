package seedu.address.ui.panel.calendar;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import seedu.address.model.CalendarDate;
import seedu.address.model.events.EventSource;
import seedu.address.ui.UiPart;
import seedu.address.ui.card.UpcomingEventCard;


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

    public UpcomingView() {
        super(FXML);
        CalendarDate calendarDate = CalendarDate.now();
        upcomingViewTitle.setText("Upcoming in " + calendarDate.getEnglishMonth());
    }

    /**
     * Updates the upcoming list to hold up to 4 of the upcoming events.
     * @param events
     */
    public void eventChange(List<EventSource> events) {
        int index = 0;
        upcomingList.getChildren().clear();
        for (EventSource event : events) {
            if (index >= totalDisplays) {
                break;
            }
            UpcomingEventCard eventCard = new UpcomingEventCard(event);
            upcomingList.getChildren().add(eventCard.getRoot());
            index++;
        }
    }

    /**
     * Re-sizes the upcoming to fit the amount of displays of the UpcomingEventCard.
     *
     * @param events The list of events to change the upcoming view.
     */
    public void resizeUpcomingView(List<EventSource> events) {
        totalDisplays = (int) (upcomingViewBase.getHeight() / UpcomingEventCard.UPCOMING_CARD_HEIGHT) - 2;
        eventChange(events);
    }
}
