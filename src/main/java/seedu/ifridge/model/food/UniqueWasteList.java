package seedu.ifridge.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.ifridge.model.waste.WasteMonth;

/**
 * A waste list.
 */
public class UniqueWasteList extends UniqueGroceryList {

    private final WasteMonth wasteMonth;

    public UniqueWasteList(WasteMonth wasteMonth) {
        super();
        requireNonNull(wasteMonth);
        this.wasteMonth = wasteMonth;
    }

    public WasteMonth getWasteMonth() {
        return wasteMonth;
    }

    @Override
    public void setGroceryList(List<GroceryItem> foods) {
        requireAllNonNull(foods);
        internalList.setAll(foods);
    }

}
