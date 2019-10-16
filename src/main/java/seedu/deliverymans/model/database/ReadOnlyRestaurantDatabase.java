package seedu.deliverymans.model.database;

import javafx.collections.ObservableList;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * Unmodifiable view of a restaurant database
 */

public interface ReadOnlyRestaurantDatabase {

    /**
     * Returns an unmodifiable view of the restaurants list.
     * This list will not contain any duplicate restaurants.
     */
    ObservableList<Restaurant> getRestaurantList();
}
