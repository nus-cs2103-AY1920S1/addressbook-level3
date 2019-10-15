package seedu.deliverymans.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.deliverymans.commons.core.LogsCenter;
import seedu.deliverymans.model.restaurant.Restaurant;

import java.util.logging.Logger;

/**
 * Panel containing the list of restaurants.
 */
public class RestaurantListPanel extends UiPart<Region> {
    private static final String FXML = "RestaurantListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RestaurantListPanel.class);

    @javafx.fxml.FXML
    private ListView<Restaurant> restaurantListView;

    public RestaurantListPanel(ObservableList<Restaurant> restaurantList) {
        super(FXML);
        restaurantListView.setItems(restaurantList);
        restaurantListView.setCellFactory(listView -> new RestaurantListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Restaurant} using a {@code RestaurantCard}.
     */
    class RestaurantListViewCell extends ListCell<Restaurant> {
        @Override
        protected void updateItem(Restaurant restaurant, boolean empty) {
            super.updateItem(restaurant, empty);

            if (empty || restaurant == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RestaurantCard(restaurant, getIndex() + 1).getRoot());
            }
        }
    }

}
