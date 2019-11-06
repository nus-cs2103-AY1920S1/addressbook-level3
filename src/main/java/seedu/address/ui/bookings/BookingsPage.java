package seedu.address.ui.bookings;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.PageWithSidebar;

/**
 * {@code Page} for displaying the expenditure details.
 */
public abstract class BookingsPage extends PageWithSidebar<AnchorPane> {

    protected static final String FXML = "bookings/BookingsPage.fxml";

    protected List<Booking> bookings;

    @FXML
    private ToggleButton viewOptionButton;

    public BookingsPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        bookings = model.getPageStatus().getTrip().getBookingList().internalUnmodifiableList;
    }
}
