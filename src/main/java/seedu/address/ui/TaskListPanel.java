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
public class TaskListPanel extends UiPart<Region> {

    private static final int NUM_OF_ITEMS_TO_SCROLL = 5;
    private static final String FXML = "TaskListPanel.fxml";

    private static int currentPosition;
    private static int itemSize;

    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    @FXML
    private ListView<Item> taskListView;

    public TaskListPanel(ObservableList<Item> itemList) {
        super(FXML);
        taskListView.setItems(itemList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        itemSize = taskListView.getItems().size();
        taskListView.scrollTo(itemSize);
        currentPosition = itemSize - NUM_OF_ITEMS_TO_SCROLL;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Item} using a {@code ItemCard}.
     */
    class TaskListViewCell extends ListCell<Item> {
        @Override
        protected void updateItem(Item item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null || !item.hasTask()) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskListCard(item, getIndex() + 1).getRoot());
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
        taskListView.scrollTo(currentPosition);

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
        taskListView.scrollTo(currentPosition);
    }
}
