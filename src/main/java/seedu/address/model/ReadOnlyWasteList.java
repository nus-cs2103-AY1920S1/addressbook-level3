package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.food.GroceryItem;
import seedu.address.model.waste.WasteMonth;
import seedu.address.model.waste.WasteStatistic;

/**
 * Unmodifiable view of a waste list
 */
public interface ReadOnlyWasteList {

    /**
     * Returns an unmodifiable view of the waste list.
     */
    ObservableList<GroceryItem> getWasteList();

    WasteMonth getWasteMonth();

    WasteStatistic getWasteStatistic();

}
