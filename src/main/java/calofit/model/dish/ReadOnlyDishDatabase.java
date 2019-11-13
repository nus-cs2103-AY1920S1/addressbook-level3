package calofit.model.dish;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an dish database
 */
public interface ReadOnlyDishDatabase {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Dish> getDishList();

}
