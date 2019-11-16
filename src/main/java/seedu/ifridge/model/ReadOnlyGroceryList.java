package seedu.ifridge.model;

import javafx.collections.ObservableList;
import seedu.ifridge.model.food.GroceryItem;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyGroceryList {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<GroceryItem> getGroceryList();

}
