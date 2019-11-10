package seedu.address.ui.itinerary;

import java.util.Comparator;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.itinerary.events.EnterCreateEventCommand;
import seedu.address.logic.commands.itinerary.events.ShowEventDetailsCommand;
import seedu.address.logic.parser.itinerary.eventview.EventViewCommand;
import seedu.address.model.Model;
import seedu.address.model.itinerary.event.Event;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.PageWithSidebar;
import seedu.address.ui.template.UiChangeConsumer;


/**
 * {@code Page} for displaying the event details.
 */
public class EventsPage extends PageWithSidebar<AnchorPane> implements UiChangeConsumer {

    private static final String FXML = "itinerary/events/EventsPage.fxml";

    @FXML
    private ListView<Event> eventListView;

    private Label inventoryLabel;

    @FXML
    private Label totalBudgetLabel;

    @FXML
    private Label bookingLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label descriptionLabel;

    public EventsPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        fillPage();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillPage() {
        // Filling events
        ObservableList<Event> events = model.getPageStatus().getDay().getEventList().internalUnmodifiableList;

        // Sorts trips for display
        SortedList<Event> eventsSortedList = events.sorted(Comparator.comparing(Event::getStartDate));
        model.setPageStatus(model.getPageStatus().withNewSortedOccurrencesList(eventsSortedList));

        eventListView.setItems(eventsSortedList);
        eventListView.setCellFactory(listView -> {
            EventListViewCell eventListViewCell = new EventListViewCell();
            return eventListViewCell;
        });
        eventListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                mainWindow.executeGuiCommand(ShowEventDetailsCommand.COMMAND_WORD
                        + " " + (eventListView.getSelectionModel().getSelectedIndex() + 1));
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Event} using a {@code EventCard}.
     */
    class EventListViewCell extends ListCell<Event> {
        @Override
        protected void updateItem(Event event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                EventCard eventCard = new EventCard(event, Index.fromZeroBased(getIndex()), mainWindow);

                setGraphic(eventCard.getRoot());
            }
        }
    }

    @Override
    public void changeUi(String commandWord) throws CommandException {
        EventViewCommand eventViewCommand = EventViewCommand.valueOf(commandWord);
        switch (eventViewCommand) {
        case SHOW:
            handleShowEventDetails();
            break;
        default:
            throw new CommandException("Events Page does not support this method");
        }
    }

    /**
     * Handles the UI changes by the {@link ShowEventDetailsCommand}.
     */
    private void handleShowEventDetails() {
        // ShowEventDetailsCommand sets event in PageStatus as the event with details to show
        Event event = model.getPageStatus().getEvent();
        if (event.getExpense().isPresent()) {
            totalBudgetLabel.setText("Total Budget: "
                                                + event.getExpense().get().getBudget()
                                                .getValueStringInCurrency(model.getTravelPal().getCurrencies().get(0)));
        }

        nameLabel.setText(event.getName().toString());

        if (event.getDescription().isPresent()) {
            descriptionLabel.setText("Description: " + event.getDescription().get().toString());
        }
    }

    @FXML
    private void handleAddEvent() {
        mainWindow.executeGuiCommand(EnterCreateEventCommand.COMMAND_WORD);
    }

}
