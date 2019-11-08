//@@author CarbonGrid
package seedu.address.ui;

import java.util.HashSet;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.address.model.events.Event;

/**
 * Panel containing the list of events.
 */
public class EventListPanel extends OmniPanel<Event> {

    private final boolean displayStatus;

    @FXML
    private ListView<Event> eventListView;

    public EventListPanel(ObservableList<Event> eventList, HashSet<Runnable> deferredUntilMouseClickOuter,
                          boolean displayStatus) {
        super(deferredUntilMouseClickOuter);
        this.displayStatus = displayStatus;
        super.setItems(eventList);
        super.setCellFactory(listView -> new EventListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Event} using a {@code EventCard}.
     */
    class EventListViewCell extends ListCell<Event> {

        public EventListViewCell() {
            setFocusTraversable(true);
        }

        @Override
        protected void updateItem(Event event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                Platform.runLater(() -> setGraphic(null));
            } else {
                Platform.runLater(() -> setGraphic(
                        new EventCard(event, getIndex() + 1, displayStatus).getRoot()));
            }
        }
    }
}
