package seedu.address.testutil;

import java.util.TreeMap;

import seedu.address.model.WasteList;
import seedu.address.model.waste.WasteMonth;

/**
 * A Waste List Builder
 */
public class WasteArchiveBuilder {

    private TreeMap<WasteMonth, WasteList> wasteArchive;

    public WasteArchiveBuilder() {
        this.wasteArchive = new TreeMap<>();
    }

    public WasteArchiveBuilder(TreeMap<WasteMonth, WasteList> wasteArchive) {
        this.wasteArchive = wasteArchive;
    }

    public WasteArchiveBuilder withWasteList(WasteList wasteList) {
        WasteMonth WlMonth = wasteList.getWasteMonth();
        wasteArchive.put(WlMonth, wasteList);
        return this;
    }

    public TreeMap<WasteMonth, WasteList> build() {
        return this.wasteArchive;
    }
}
