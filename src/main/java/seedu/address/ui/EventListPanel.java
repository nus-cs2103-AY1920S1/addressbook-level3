package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.item.Item;

/**
 * Panel containing the list of items.
 */
public class EventListPanel extends UiPart<Region> {
    private static int currentPosition;
    private static int itemSize;
    private static final String FXML = "EventListPanel.fxml";

    private final int NUMOFITEMSTOSCROLL = 5;
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);

    @FXML
    private ListView<Item> eventListView;

    public EventListPanel(ObservableList<Item> itemList) {
        super(FXML);
        eventListView.setItems(itemList);
        eventListView.setCellFactory(listView -> new EventListViewCell());
        itemSize = eventListView.getItems().size();
        eventListView.scrollTo(itemSize);
        currentPosition = itemSize - NUMOFITEMSTOSCROLL;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Item} using a {@code ItemCard}.
     */
    class EventListViewCell extends ListCell<Item> {
        @Override
        protected void updateItem(Item item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null || !item.hasEvent()) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EventListCard(item, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Scrolls up.
     */
    public void scrollUp() {
        if (currentPosition - NUMOFITEMSTOSCROLL <= 0) {
            currentPosition = 0;
        } else {
            currentPosition = currentPosition - NUMOFITEMSTOSCROLL;
        }
        eventListView.scrollTo(currentPosition);

    }

    /**
     * Scrolls down.
     */
    public void scrollDown() {
        if (currentPosition + NUMOFITEMSTOSCROLL >= itemSize) {
            currentPosition = itemSize - NUMOFITEMSTOSCROLL;
        } else {
            currentPosition = currentPosition + NUMOFITEMSTOSCROLL;
        }
        eventListView.scrollTo(currentPosition);
    }
}
