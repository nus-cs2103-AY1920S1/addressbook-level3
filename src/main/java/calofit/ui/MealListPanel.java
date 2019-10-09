package calofit.ui;

import calofit.commons.core.LogsCenter;
import calofit.model.meal.Meal;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of persons.
 */
public class MealListPanel extends UiPart<Region> {
    private static final String FXML = "MealListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MealListPanel.class);

    @FXML
    private ListView<Meal> mealListView;

    public MealListPanel(ObservableList<Meal> mealList) {
        super(FXML);
        mealListView.setItems(mealList);
        mealListView.setCellFactory(listView -> new MealListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Meal} using a {@code DishCard}.
     */
    class MealListViewCell extends ListCell<Meal> {
        @Override
        protected void updateItem(Meal meal, boolean empty) {
            super.updateItem(meal, empty);

            if (empty || meal == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MealCard(meal, getIndex() + 1).getRoot());
            }
        }
    }

}
