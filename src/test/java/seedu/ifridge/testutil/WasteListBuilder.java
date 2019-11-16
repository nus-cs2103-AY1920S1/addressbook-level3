package seedu.ifridge.testutil;

import seedu.ifridge.model.WasteList;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.waste.WasteMonth;

/**
 * A Waste List Builder
 */
public class WasteListBuilder {

    private WasteList wasteList;

    public WasteListBuilder(WasteMonth wasteMonth) {
        wasteList = new WasteList(wasteMonth);
    }

    /**
     * Constructs a waste list builder.
     * @param wasteList the waste list to construct a waste list builder with.
     */
    public WasteListBuilder(WasteList wasteList) {
        this.wasteList = wasteList;
    }

    /**
     * Adds an item to the waste list
     * @param wasteItem the item to be added
     * @return the waste list with the added item
     */
    public WasteListBuilder withWasteItem(GroceryItem wasteItem) {
        wasteList.addWasteItem(wasteItem);
        return this;
    }

    public WasteList build() {
        return wasteList;
    }
}
