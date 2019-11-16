package seedu.ifridge.testutil;

import java.util.TreeMap;

import seedu.ifridge.model.WasteList;
import seedu.ifridge.model.waste.WasteMonth;

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

    /**
     * Adds a new waste list to the waste archive.
     * @param wasteList the waste list to be added
     * @return an updated WasteArchiveBuilder
     */
    public WasteArchiveBuilder withWasteList(WasteList wasteList) {
        WasteMonth wlMonth = wasteList.getWasteMonth();
        wasteArchive.put(wlMonth, wasteList);
        return this;
    }

    public TreeMap<WasteMonth, WasteList> build() {
        return this.wasteArchive;
    }
}
