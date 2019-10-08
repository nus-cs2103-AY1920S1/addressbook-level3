package seedu.address.ui;

import java.time.YearMonth;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.control.Label;

import javafx.scene.layout.StackPane;
import seedu.address.model.events.EventSource;

/**
 * An Ui that stores the logged feedback from the program to the user.
 */
public class DayCard extends UiPart<Region> {

    private static final String FXML = "DayCard.fxml";

    private ArrayList<EventSource> eventList = new ArrayList<>();

    @FXML
    private StackPane todayEvents;

    @FXML
    private Label index;

    /**
     * Constructor for ListPanel. Stores the event list, and task list[in v2.0].
     */
    public DayCard(int day) {
        super(FXML);
        String currentIndex = day + ". ";
        this.index.setText(currentIndex);
        for(EventSource event : eventList) {
            String eventDescription = event.getDescription().substring(0, 15);
            Label eventLabel = new Label(eventDescription);
        }
    }

    public void updateEventList(EventSource event) {
        this.eventList.add(event);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DayCard)) {
            return false;
        }

        // state check
        DayCard card = (DayCard) other;
        return eventList.equals(card.eventList);
    }
}
