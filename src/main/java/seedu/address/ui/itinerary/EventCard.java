package seedu.address.ui.itinerary;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.model.itinerary.event.Event;
import seedu.address.ui.UiPart;
import seedu.address.commons.core.index.Index;

/**
 * TODO: Implement display for inventory and booking labels
 */
public class EventCard extends UiPart<HBox> {
    private static final String FXML = "itinerary/events/EventCard.fxml";

    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label endDateLabel;
    @FXML
    private Label destinationLabel;
    @FXML
    private Label inventoryLabel;
    @FXML
    private Label bookingLabel;
    @FXML
    private VBox propertiesContainer;

    private Event event;
    private Index displayedIndex;

    public EventCard(Event event, Index displayedIndex) {
        super(FXML);
        this.event = event;
        this.displayedIndex = displayedIndex;
        fillEventCardLabels();
    }

    private void fillEventCardLabels() {
        idLabel.setText(displayedIndex.getOneBased() + ".");
        nameLabel.setText("Name: " + event.getName().toString());
        destinationLabel.setText("Destination :" + event.getDestination().toString());
        startDateLabel.setText("Start: " + ParserDateUtil.getDisplayTime(event.getStartDate()));
        endDateLabel.setText("End: " + ParserDateUtil.getDisplayTime(event.getEndDate()));
    }
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCard)) {
            return false;
        }

        // state check
        EventCard otherCard = (EventCard) other;
        return event.equals(otherCard.event)
                && this.displayedIndex.equals(otherCard.displayedIndex);
    }
}
