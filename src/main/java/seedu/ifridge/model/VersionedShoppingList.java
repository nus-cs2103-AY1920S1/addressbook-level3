package seedu.ifridge.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Facilitates undo and redo mechanism in shopping list.
 */
public class VersionedShoppingList extends ShoppingList {
    private List<ReadOnlyShoppingList> shoppingListStateList;
    private int currentStatePointer = 0;

    public VersionedShoppingList() {
        shoppingListStateList = new ArrayList<>();
    }

    /**
     * Saves the current shopping list state in its history.
     */
    public void commit(ReadOnlyShoppingList shoppingList) {
        currentStatePointer++;
        int listSize = shoppingListStateList.size();
        for (int i = currentStatePointer; i < listSize; i++) {
            shoppingListStateList.remove(i);
        }
        shoppingListStateList.add(currentStatePointer, shoppingList);
    }

    /**
     * Restores the previous shopping list state from its history.
     */
    public ReadOnlyShoppingList undo() {
        currentStatePointer--;
        return shoppingListStateList.get(currentStatePointer);
    }

    /**
     *  Restores a previously undone grocery list state from its history.
     */
    public ReadOnlyShoppingList redo() {
        currentStatePointer++;
        return shoppingListStateList.get(currentStatePointer);
    }

    public int getCurrentStatePointer() {
        return currentStatePointer;
    }

    public int getListSize() {
        return shoppingListStateList.size();
    }

    public void add(ReadOnlyShoppingList shoppingList) {
        shoppingListStateList.add(shoppingList);
    }
}
