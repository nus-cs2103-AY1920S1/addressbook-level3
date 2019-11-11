package seedu.address.ui.bookings;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.bookings.EnterCreateBookingCommand;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.PageWithSidebar;

/**
 * {@code Page} for displaying the booking details.
 */
public class BookingsPage extends PageWithSidebar<AnchorPane> {

    protected static final String FXML = "bookings/BookingsPage.fxml";

    protected List<Booking> bookings;

    @FXML
    private ScrollPane bookingsContainer;

    public BookingsPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        bookings = model.getPageStatus().getTrip().getBookingList().internalUnmodifiableList;
        fillPage();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillPage() {
        VBox bookingCardsContainer = new VBox();
        List<Node> bookingCards = IntStream.range(0, bookings.size())
                .mapToObj(Index::fromZeroBased)
                .map(index -> {
                    Booking booking = bookings.get(index.getZeroBased());
                    BookingCard bookingCard = generateBookingCard(booking, index);
                    return bookingCard.getRoot();
                }).collect(Collectors.toList());
        bookingCardsContainer.getChildren().addAll(bookingCards);
        bookingsContainer.setContent(bookingCardsContainer);
    }

    /**
     * Generates a booking card
     *
     * @param booking booking to be displayed in booking card
     * @param index index of booking card
     * @return a booking card with matching type
     */
    private BookingCard generateBookingCard(Booking booking, Index index) {
        BookingCard bookingCard;
        bookingCard = new BookingCard(booking, index);
        return bookingCard;
    }

    @FXML
    private void handleAddBooking() {
        mainWindow.executeGuiCommand(EnterCreateBookingCommand.COMMAND_WORD);
    }
}
