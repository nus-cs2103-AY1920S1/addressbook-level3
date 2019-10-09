package seedu.savenus.model;

import javafx.collections.ObservableList;
import seedu.savenus.model.food.Food;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyMenu {

    /**
     * Returns an unmodifiable view of the foods list.
     * This list will not contain any duplicate foods.
     */
    ObservableList<Food> getFoodList();

}
