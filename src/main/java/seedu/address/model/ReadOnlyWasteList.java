package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.food.GroceryItem;

/**
 * Unmodifiable view of a waste list
 */
public interface ReadOnlyWasteList {

    /**
     * Returns an unmodifiable view of the waste list.
     */
    ObservableList<GroceryItem> getWasteList();

}
