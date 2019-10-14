package seedu.address.model.entitylist;

import javafx.collections.ObservableList;

import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Id;

/**
 * This interface serves as the new API for the model.
 * Each child of {@code EntityList} should behave as a singleton.
 */
public abstract class EntityList implements ReadOnlyEntityList {

    public abstract boolean contains(Id id);

    public abstract ObservableList<? extends Entity> list();

    public abstract ObservableList<? extends Entity> getUnmodifiableList();
}
