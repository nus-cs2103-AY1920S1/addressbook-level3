package seedu.address.ui.card;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import seedu.address.model.events.EventSource;
import seedu.address.ui.UiPart;

//@@author Kyzure
/**
 * An UI component that displays information of a {@code Event}.
 */
public class UpcomingEventCard extends UiPart<Region> {

    public static final Integer UPCOMING_CARD_HEIGHT = 39;
    private static final String FXML = "UpcomingEventCard.fxml";

    @FXML
    private Label upcomingEventName;

    @FXML
    private Label upcomingWeekDay;

    @FXML
    private Label upcomingDay;

    /**
     * Constructor for the EventCard, which displays the information of a particular event.
     *
     * @param event The given event.
     */
    public UpcomingEventCard(EventSource event) {
        super(FXML);
        upcomingEventName.setText(event.getDescription());
        upcomingWeekDay.setText(event.getStartDateTime().getEnglishWeekDay());
        upcomingDay.setText(String.valueOf(event.getStartDateTime().getDay()));
        upcomingEventName.setMinHeight(Region.USE_PREF_SIZE);
    }
}
