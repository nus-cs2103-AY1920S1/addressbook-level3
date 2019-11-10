package seedu.address.ui.bookings;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.expense.EnterCreateExpenseCommand;
import seedu.address.logic.commands.expense.EnterDaysViewCommand;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * {@code Page} for displaying the expenditure details.
 */
public class BookingListPage extends BookingsPage {

    @FXML
    private ScrollPane bookingsContainer;

    @FXML
    private ToggleButton viewOptionButton;

    public BookingListPage(MainWindow mainWindow, Logic logic, Model model) {
        super(mainWindow, logic, model);
        viewOptionButton.setSelected(false);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillPage() {
        VBox bookingCardsContainer = new VBox();
        List<Node> bookingCards = IntStream.range(0, bookings.size())
                .mapToObj(i -> Index.fromZeroBased(i))
                .map(index -> {
                    BookingCard bookingCard = new BookingCard(bookings.get(index.getZeroBased()), index);
                    return bookingCard.getRoot();
                }).collect(Collectors.toList());
        bookingCardsContainer.getChildren().addAll(bookingCards);
        bookingsContainer.setContent(bookingCardsContainer);
    }

    @FXML
    private void handleToggle() {
        mainWindow.executeGuiCommand(EnterDaysViewCommand.COMMAND_WORD);
    }

    @FXML
    private void handleAddExpenditure() {
        mainWindow.executeGuiCommand(EnterCreateExpenseCommand.COMMAND_WORD);
    }

}
