package seedu.ifridge.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Facilitates undo and redo mechanism in grocery list.
 */
public class VersionedGroceryList extends GroceryList {
    private List<ReadOnlyGroceryList> groceryListStateList;
    private int currentStatePointer = 0;

    public VersionedGroceryList() {
        groceryListStateList = new ArrayList<>();
    }

    /**
     * Saves the current grocery list state in its history.
     */
    public void commit(ReadOnlyGroceryList groceryList) {
        currentStatePointer++;
        int listSize = groceryListStateList.size();
        for (int i = currentStatePointer; i < listSize; i++) {
            groceryListStateList.remove(i);
        }
        groceryListStateList.add(currentStatePointer, groceryList);
    }

    /**
     * Restores the previous grocery list state from its history.
     */
    public ReadOnlyGroceryList undo() {
        currentStatePointer--;
        return groceryListStateList.get(currentStatePointer);
    }

    /**
     *  Restores a previously undone grocery list state from its history.
     */
    public ReadOnlyGroceryList redo() {
        currentStatePointer++;
        return groceryListStateList.get(currentStatePointer);
    }

    public int getCurrentStatePointer() {
        return currentStatePointer;
    }

    public int getListSize() {
        return groceryListStateList.size();
    }

    public void add(ReadOnlyGroceryList groceryList) {
        groceryListStateList.add(groceryList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VersionedGroceryList // instanceof handles nulls
                && groceryListStateList.equals(((VersionedGroceryList) other).groceryListStateList));
    }
}
