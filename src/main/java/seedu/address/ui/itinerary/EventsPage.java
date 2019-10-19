package seedu.address.ui.itinerary;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.common.EnterPrefsCommand;
import seedu.address.logic.commands.itinerary.events.EnterCreateEventCommand;
import seedu.address.model.Model;
import seedu.address.model.itinerary.event.Event;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.PageWithSidebar;

/**
 * {@code Page} for displaying the event details.
 */
public class EventsPage extends PageWithSidebar<AnchorPane> {

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
        eventListView.setItems(events);
        eventListView.setCellFactory(listView -> {
            EventListViewCell eventListViewCell = new EventListViewCell();
            return eventListViewCell;
        });
        eventListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                int index = eventListView.getSelectionModel().getSelectedIndex();
                if (events.get(index).getExpenditure().isPresent()) {
                    totalBudgetLabel.setText("Total Budget: "
                            + events.get(index).getExpenditure().get().getBudget()
                            .toString());
                } else {
                    totalBudgetLabel.setText("Total Budget: 0");
                }
                nameLabel.setText(events.get(index).getName().toString());
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


    @FXML
    private void handleAddEvent() {
        mainWindow.executeGuiCommand(EnterCreateEventCommand.COMMAND_WORD);
    }

    @FXML
    private void handlePreferences() {
        mainWindow.executeGuiCommand(EnterPrefsCommand.COMMAND_WORD);
    }

}
