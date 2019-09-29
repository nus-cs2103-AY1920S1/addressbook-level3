package seedu.address.model.entitylist;

import java.util.List;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Id;

public interface ReadableEntityList {
    /**
     * Checks if a given entity list contains a certain entity.
     *
     * @param id
     * @return boolean
     */
    public boolean contains(Id id);

    /**
     * List the entities.
     *
     * @return List<? extends Entity>
     */
    public List<? extends Entity> list();
}
