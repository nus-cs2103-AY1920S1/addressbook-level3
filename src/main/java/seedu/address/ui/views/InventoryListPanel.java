package seedu.address.ui.views;

import java.net.URL;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.task.Task;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of inventories.
 * Called by {@code UserViewUpdate} when user executes {@code ListInventoriesCommand}.
 */
public class InventoryListPanel extends UiPart<Region> {
    private static final String FXML = "InventoryListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(InventoryListPanel.class);

    @FXML
    private ListView<Inventory> InventoryListView;

    public InventoryListPanel(ObservableList<Inventory> inventoryList) {
        super(FXML);
        InventoryListView.setItems(inventoryList);
        InventoryListView.setCellFactory(listView -> new InventoryListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class InventoryListViewCell extends ListCell<Inventory> {
        @Override
        protected void updateItem(Inventory inventory, boolean empty) {
            super.updateItem(inventory, empty);

            if (empty || inventory == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new InventoryCard(inventory, getIndex() + 1).getRoot());
            }
        }
    }
}
