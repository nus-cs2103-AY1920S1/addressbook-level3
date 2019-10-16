package seedu.address.model;

import seedu.address.model.item.VisualizeList;

/**
 * Interface for current app state
 */

public interface ElisaState {
    public ElisaState deepCopy();

    public ItemStorage getStorage();

    public VisualizeList getVisualizeList();
}
