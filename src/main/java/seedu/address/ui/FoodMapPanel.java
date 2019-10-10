package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.sgm.model.food.Food;
import seedu.sgm.model.food.FoodType;

/**
 * Represents a panel of a list of foods categorized by {@code FoodType}.
 */
public class FoodMapPanel extends UiPart<Region> {
    private static final String FXML = "FoodMapPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FoodMapPanel.class);

    @FXML
    private ListView<ObservableList<Food>> foodListsView;

    public FoodMapPanel(ObservableList<ObservableList<Food>> foodLists) {
        super(FXML);
        foodListsView.setItems(foodLists);
        foodListsView.setCellFactory(listView -> new FoodListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Food} using a {@code FoodCard}.
     */
    class FoodListViewCell extends ListCell<ObservableList<Food>> {
        @Override
        protected void updateItem(ObservableList<Food> foodList, boolean empty) {
            super.updateItem(foodList, empty);

            if (empty || foodList == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FoodListView(foodList).getRoot());
            }
        }
    }

}
