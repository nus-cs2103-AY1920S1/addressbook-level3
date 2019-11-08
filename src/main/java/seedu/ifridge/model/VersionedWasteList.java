package seedu.ifridge.model;

import java.util.ArrayList;
import java.util.List;

import seedu.ifridge.model.waste.WasteMonth;

/**
 * Facilitates undo and redo mechanism in waste list.
 */
public class VersionedWasteList extends WasteList {
    private List<ReadOnlyWasteList> wasteListStateList;
    private int currentStatePointer = 0;

    public VersionedWasteList(WasteMonth wasteMonth) {
        super(wasteMonth);
        wasteListStateList = new ArrayList<>();
    }

    /**
     * Saves the current waste list state in its history.
     */
    public void commit(ReadOnlyWasteList wasteList) {
        currentStatePointer++;
        int listSize = wasteListStateList.size();
        for (int i = currentStatePointer; i < listSize; i++) {
            wasteListStateList.remove(i);
        }
        wasteListStateList.add(wasteList);
    }

    /**
     * Restores the previous waste list state from its history.
     */
    public ReadOnlyWasteList undo() {
        currentStatePointer--;
        return wasteListStateList.get(currentStatePointer);
    }

    /**
     *  Restores a previously undone waste list state from its history.
     */
    public ReadOnlyWasteList redo() {
        currentStatePointer++;
        return wasteListStateList.get(currentStatePointer);
    }

    public void add(ReadOnlyWasteList wasteList) {
        wasteListStateList.add(wasteList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VersionedWasteList // instanceof handles nulls
                && wasteListStateList.equals(((VersionedWasteList) other).wasteListStateList));
    }
}
