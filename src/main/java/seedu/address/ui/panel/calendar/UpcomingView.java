package seedu.address.ui.panel.calendar;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.model.CalendarDate;
import seedu.address.model.events.EventSource;
import seedu.address.ui.UiPart;
import seedu.address.ui.card.UpcomingEventCard;

import java.util.List;

/**
 * An UI that represents the upcoming event or task displayed to the user.
 */
public class UpcomingView extends UiPart<Region> {

    private static final String FXML = "UpcomingView.fxml";
    private Integer totalDisplays;

    @FXML
    private Label upcomingViewTitle;

    @FXML
    private StackPane upcomingPanel;

    @FXML
    private VBox upcomingList;

    public UpcomingView() {
        super(FXML);
        CalendarDate calendarDate = CalendarDate.now();
        upcomingViewTitle.setText("Upcoming in " + calendarDate.getEnglishMonth());
        changeTotalDisplays();
    }

    /**
     * Updates the upcoming list to hold up to 4 of the upcoming events.
     * @param events
     */
    public void eventChange(List<EventSource> events) {
        int index = 0;
        upcomingList.getChildren().clear();
        for(EventSource event : events) {
            if(index >= totalDisplays) {
                break;
            }
            UpcomingEventCard eventCard = new UpcomingEventCard(event);
            upcomingList.getChildren().add(eventCard.getRoot());
            index++;
        }
    }

    private void changeTotalDisplays() {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new Exception(e.getMessage());
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                totalDisplays = (int) upcomingPanel.getHeight() / 39 - 1;
            }
        });
        new Thread(sleeper).start();
    }

    public void resizeUpcomingView(List<EventSource> events) {
        changeTotalDisplays();
        eventChange(events);
    }
}
