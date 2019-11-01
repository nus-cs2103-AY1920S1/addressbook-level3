package seedu.ifridge.model;

import java.util.ArrayList;
import java.util.List;

public class VersionedBoughtList extends GroceryList{
    private List<ReadOnlyGroceryList> boughtListStateList;
    private int currentStatePointer = 0;

    public VersionedBoughtList() {
        boughtListStateList = new ArrayList<>();
    }

    /**
     * Saves the current grocery list state in its history.
     */
    public void commit(ReadOnlyGroceryList boughtList) {
        currentStatePointer++;
        int listSize = boughtListStateList.size();
        for (int i = currentStatePointer; i < listSize; i++) {
            boughtListStateList.remove(i);
        }
        boughtListStateList.add(currentStatePointer, boughtList);
    }

    /**
     * Restores the previous grocery list state from its history.
     */
    public ReadOnlyGroceryList undo() {
        currentStatePointer--;
        return boughtListStateList.get(currentStatePointer);
    }

    /**
     *  Restores a previously undone grocery list state from its history.
     */
    public ReadOnlyGroceryList redo() {
        currentStatePointer++;
        return boughtListStateList.get(currentStatePointer);
    }

    public int getCurrentStatePointer() {
        return currentStatePointer;
    }

    public int getListSize() {
        return boughtListStateList.size();
    }

    public void add(ReadOnlyGroceryList boughtList) {
        boughtListStateList.add(boughtList);
    }
}
