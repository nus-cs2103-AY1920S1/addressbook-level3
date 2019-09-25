package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.food.Food;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the foods list.
     * This list will not contain any duplicate foods.
     */
    ObservableList<Food> getFoodList();

}
