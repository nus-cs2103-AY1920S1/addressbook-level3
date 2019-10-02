package seedu.address.ui.trips;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import seedu.address.model.itinerary.trip.Trip;
import seedu.address.ui.UiPart;

public class TripPane extends UiPart<AnchorPane> {
    private static final String FXML = "TripPane.fxml";

    @FXML
    private Label tripName;

    private Trip trip;

    private int displayedIndex;

    public TripPane(Trip trip, int displayedIndex) {
        super(FXML);
        this.trip = trip;
        this.displayedIndex = displayedIndex;
        this.tripName.setText(trip.getName().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TripPane)) {
            return false;
        }

        // state check
        TripPane card = (TripPane) other;
        return trip.equals(card.trip)
                && this.displayedIndex == card.displayedIndex;
    }
}
