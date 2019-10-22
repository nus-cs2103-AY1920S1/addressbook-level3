package seedu.savenus.model.menu;

import javafx.collections.ObservableList;
import seedu.savenus.model.food.Food;

/**
 * Unmodifiable view of an menu
 */
public interface ReadOnlyMenu {

    /**
     * Returns an unmodifiable view of the foods list.
     * This list will not contain any duplicate foods.
     */
    ObservableList<Food> getFoodList();

}
