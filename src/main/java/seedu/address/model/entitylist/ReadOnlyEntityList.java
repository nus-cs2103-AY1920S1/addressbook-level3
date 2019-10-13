package seedu.address.model.entitylist;

import javafx.collections.ObservableList;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Id;

/**
 * Aims to make each EntityList readable.
 */
public interface ReadOnlyEntityList {
    /**
     * Checks if a given entity list contains a certain entity.
     *
     * @param id
     * @return boolean
     */
    boolean contains(Id id);

    /**
     * List the entities.
     *
     * @return List<? extends Entity>
     */
    ObservableList<? extends Entity> list();
}
