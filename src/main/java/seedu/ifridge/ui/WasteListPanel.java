package seedu.ifridge.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.ifridge.commons.core.LogsCenter;
import seedu.ifridge.model.food.GroceryItem;


/**
 * Panel for waste list.
 */
public class WasteListPanel extends UiPart<Region> {
    private static final String FXML = "WasteListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(WasteListPanel.class);

    @FXML
    private ListView<GroceryItem> wasteListView;

    public WasteListPanel(ObservableList<GroceryItem> foodList) {
        super(FXML);
        wasteListView.setItems(foodList);
        wasteListView.setCellFactory(listView -> new WasteListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class WasteListViewCell extends ListCell<GroceryItem> {
        @Override
        protected void updateItem(GroceryItem food, boolean empty) {
            super.updateItem(food, empty);

            if (empty || food == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ExpiredGroceryCard(food, getIndex() + 1).getRoot());
            }
        }
    }

}
