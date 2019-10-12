package seedu.address.model.entitylist;

import java.util.List;

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
    List<? extends Entity> list();
}
