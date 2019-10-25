package seedu.ifridge.model;

import javafx.collections.ObservableList;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.waste.WasteMonth;
import seedu.ifridge.model.waste.WasteStatistic;

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
