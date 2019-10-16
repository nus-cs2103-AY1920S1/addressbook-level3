package seedu.address.testutil;

import seedu.address.model.WasteList;
import seedu.address.model.food.GroceryItem;

public class WasteListBuilder {

    private WasteList wasteList;

    public WasteListBuilder() {
        wasteList = new WasteList();
    }

    public WasteListBuilder(WasteList wasteList) {
        this.wasteList = wasteList;
    }

    public WasteListBuilder withWasteItem(GroceryItem wasteItem) {
        wasteList.addWasteItem(wasteItem);
        return this;
    }

    public WasteList build() {
        return wasteList;
    }
}
