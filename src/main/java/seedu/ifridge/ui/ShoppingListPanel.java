package seedu.ifridge.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.ifridge.commons.core.LogsCenter;
import seedu.ifridge.model.food.ShoppingItem;


/**
 * Panel for shopping list.
 */
public class ShoppingListPanel extends UiPart<Region> {
    private static final String FXML = "ShoppingListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ShoppingListPanel.class);

    @FXML
    private ListView<ShoppingItem> shoppingListView;

    public ShoppingListPanel(ObservableList<ShoppingItem> shoppingItems) {
        super(FXML);
        shoppingListView.setItems(shoppingItems);
        shoppingListView.setCellFactory(listView -> new ShoppingListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ShoppingItem} using a {@code ShoppingItemCard}.
     */
    class ShoppingListViewCell extends ListCell<ShoppingItem> {
        @Override
        protected void updateItem(ShoppingItem shoppingItem, boolean empty) {
            super.updateItem(shoppingItem, empty);

            if (empty || shoppingItem == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ShoppingItemCard(shoppingItem, getIndex() + 1).getRoot());
            }
        }
    }

}
