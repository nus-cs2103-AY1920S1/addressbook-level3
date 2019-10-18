package seedu.address.model.food;

import static java.util.Objects.requireNonNull;

import seedu.address.model.waste.WasteMonth;

/**
 * A waste list.
 */
public class UniqueWasteList extends UniqueFoodList {

    private final WasteMonth wasteMonth;

    public UniqueWasteList(WasteMonth wasteMonth) {
        requireNonNull(wasteMonth);
        this.wasteMonth = wasteMonth;
    }

    public WasteMonth getWasteMonth() {
        return wasteMonth;
    }

}
