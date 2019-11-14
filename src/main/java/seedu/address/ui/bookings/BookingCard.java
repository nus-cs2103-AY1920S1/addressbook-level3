package seedu.address.ui.bookings;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;
import seedu.address.model.booking.Booking;
import seedu.address.ui.UiPart;


/**
 * TODO: Implement display for inventory and booking labels.
 */
public class BookingCard extends UiPart<HBox> {
    private static final String FXML = "bookings/BookingCard.fxml";

    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label expenseLabel;
    @FXML
    private Label contactLabel;

    @FXML
    private VBox propertiesContainer;

    private Booking booking;
    private Index displayedIndex;

    public BookingCard(Booking booking, Index displayedIndex) {
        super(FXML);
        this.booking = booking;
        this.displayedIndex = displayedIndex;
        fillBookingCardLabels();
    }

    /**
     * Fills the labels of this expenditure card.
     */
    private void fillBookingCardLabels() {
        idLabel.setText(displayedIndex.getOneBased() + ".");
        nameLabel.setText(booking.getName().toString());
        contactLabel.setText(booking.getContact().toString());
        expenseLabel.setText(booking.getBudget().toString());
    }
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookingCard)) {
            return false;
        }

        // state check
        BookingCard otherCard = (BookingCard) other;
        return booking.equals(otherCard.booking)
                && this.displayedIndex.equals(otherCard.displayedIndex);
    }

    public Booking getBooking() {
        return booking;
    }
}
