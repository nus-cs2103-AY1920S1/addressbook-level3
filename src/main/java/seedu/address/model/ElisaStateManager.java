package seedu.address.model;

import seedu.address.model.item.VisualizeList;

/**
 * Stores the current application state
 */

public class ElisaStateManager implements ElisaState {
    private ItemStorage storage;
    private VisualizeList visualizeList;

    public ElisaStateManager(ItemStorage storage, VisualizeList visualizeList) {
        this.storage = storage;
        this.visualizeList = visualizeList;
    }

    public ItemStorage getStorage() {
        return storage;
    }

    public VisualizeList getVisualizeList() {
        return visualizeList;
    }

    @Override
    public ElisaState deepCopy() {
        return new ElisaStateManager(storage.deepCopy(), visualizeList.deepCopy());
    }
}
