package seedu.address.model.food;

import seedu.address.model.waste.WasteMonth;

/**
 * A waste list.
 */
public class UniqueWasteList extends UniqueFoodList {

    private final WasteMonth wasteMonth;

    public UniqueWasteList(WasteMonth wasteMonth) {
        this.wasteMonth = wasteMonth;
    }

    public WasteMonth getWasteMonth() {
        return wasteMonth;
    }

}
