package seedu.address.ui;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.distinctdate.DistinctDate;
import seedu.address.model.event.Event;

/**
 * An UI component that displays information of a {@code DistinctDate}.
 */
public class DateCard extends UiPart<Region> {

    private static final String FXML = "DateListCard.fxml";

    public final DistinctDate date;
    private MainWindow mainWindow;
    private Integer index;

    @FXML
    private Label dateLabel;
    @FXML
    private ListView eventListView;


    public DateCard(DistinctDate distinctDate, int displayedIndex, MainWindow mainWindow) {
        super(FXML);
        this.date = distinctDate;
        this.mainWindow = mainWindow;
        this.index = displayedIndex - 1;
        dateLabel.setText(date.getDate().toString());
        List<Event> listOfEvent = distinctDate.getListOfEvents();
        ObservableList<Event> eventList = FXCollections.observableArrayList(listOfEvent);
        eventListView.setItems(eventList);
        eventListView.setCellFactory(listView -> new EventListViewCell(mainWindow));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Event} using a {@code EventCard}.
     */
    class EventListViewCell extends ListCell<Event> {
        private MainWindow mainWindow;

        EventListViewCell(MainWindow mainWindow) {
            this.mainWindow = mainWindow;
        }
        @Override
        protected void updateItem(Event event, boolean empty) {
            super.updateItem(event, empty);
            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                EventCard eventCard = new EventCard(event, getIndex() + 1, mainWindow);
                setGraphic(eventCard.getRoot());
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateCard)) {
            return false;
        }

        // state check
        DateCard card = (DateCard) other;
        return index.equals(card.index)
                && date.equals(card.date);
    }
}
