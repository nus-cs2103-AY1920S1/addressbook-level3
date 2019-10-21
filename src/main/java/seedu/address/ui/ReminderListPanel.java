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
public class ReminderListPanel extends UiPart<Region> {
    private static final String FXML = "ReminderListPanel.fxml";
    private static int currentPosition;
    private static int itemSize;

    private final int NUM_OF_ITEMS_TO_SCROLL = 5;
    private final Logger logger = LogsCenter.getLogger(ReminderListPanel.class);

    @FXML
    private ListView<Item> reminderListView;

    public ReminderListPanel(ObservableList<Item> itemList) {
        super(FXML);
        reminderListView.setItems(itemList);
        reminderListView.setCellFactory(listView -> new ReminderListViewCell());
        itemSize = reminderListView.getItems().size();
        reminderListView.scrollTo(itemSize);
        currentPosition = itemSize - NUM_OF_ITEMS_TO_SCROLL;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Item} using a {@code ItemCard}.
     */
    class ReminderListViewCell extends ListCell<Item> {
        @Override
        protected void updateItem(Item item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null || !item.hasReminder()) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ReminderListCard(item, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Scrolls up.
     */
    public void scrollUp() {
        if (currentPosition - NUM_OF_ITEMS_TO_SCROLL <= 0) {
            currentPosition = 0;
        } else {
            currentPosition = currentPosition - NUM_OF_ITEMS_TO_SCROLL;
        }
        reminderListView.scrollTo(currentPosition);

    }

    /**
     * Scrolls down.
     */
    public void scrollDown() {
        if (currentPosition + NUM_OF_ITEMS_TO_SCROLL >= itemSize) {
            currentPosition = itemSize - NUM_OF_ITEMS_TO_SCROLL;
        } else {
            currentPosition = currentPosition + NUM_OF_ITEMS_TO_SCROLL;
        }
        reminderListView.scrollTo(currentPosition);
    }
}
