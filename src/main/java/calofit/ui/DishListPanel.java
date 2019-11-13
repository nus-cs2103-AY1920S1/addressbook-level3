package calofit.ui;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

import calofit.commons.core.LogsCenter;
import calofit.model.dish.Dish;


/**
 * Panel containing the list of persons.
 */
public class DishListPanel extends UiPart<Region> {
    private static final String FXML = "DishListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DishListPanel.class);

    @FXML
    private ListView<Dish> dishListView;

    @FXML
    private Text emptyOverlay;

    public DishListPanel(ObservableList<Dish> dishList) {
        super(FXML);
        dishListView.setItems(dishList);
        dishListView.setCellFactory(listView -> new DishListViewCell());

        emptyOverlay.visibleProperty().bind(Bindings.createBooleanBinding(dishList::isEmpty, dishList));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Dish} using a {@code DishCard}.
     */
    class DishListViewCell extends ListCell<Dish> {
        @Override
        protected void updateItem(Dish dish, boolean empty) {
            super.updateItem(dish, empty);

            if (empty || dish == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DishCard(dish, getIndex() + 1).getRoot());
            }
        }
    }

}
