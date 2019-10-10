package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.sgm.model.food.Food;

/**
 * Represents a panel of a list of foods.
 */
public class FoodListView extends UiPart<Region> {
    private static final String FXML = "FoodListView.fxml";
    private final Logger logger = LogsCenter.getLogger(FoodListView.class);

    @FXML
    private ListView<Food> foodListView;

    public FoodListView(ObservableList<Food> foodList) {
        super(FXML);
        foodListView.setItems(foodList);
        foodListView.setCellFactory(listView -> new FoodListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Food} using a {@code FoodCard}.
     */
    class FoodListViewCell extends ListCell<Food> {
        @Override
        protected void updateItem(Food food, boolean empty) {
            super.updateItem(food, empty);

            if (empty || food == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FoodCard(food, getIndex() + 1).getRoot());
            }
        }
    }

}
