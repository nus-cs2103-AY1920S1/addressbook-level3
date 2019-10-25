package seedu.ifridge.model.food;

import static java.util.Objects.requireNonNull;

import seedu.ifridge.model.waste.WasteMonth;

/**
 * A waste list.
 */
public class UniqueWasteList extends UniqueGroceryList {

    private final WasteMonth wasteMonth;

    public UniqueWasteList(WasteMonth wasteMonth) {
        requireNonNull(wasteMonth);
        this.wasteMonth = wasteMonth;
    }

    public WasteMonth getWasteMonth() {
        return wasteMonth;
    }

}
