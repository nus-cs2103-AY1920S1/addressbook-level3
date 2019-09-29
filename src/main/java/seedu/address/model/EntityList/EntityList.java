package seedu.address.model.EntityList;

import java.util.List;
import seedu.address.model.Entity.Entity;
import seedu.address.model.Entity.Id;

/**
 * This interface serves as the new API for the model.
 * Each child of EntityList should behave as a singleton.
 */
public abstract class EntityList {
    protected int nextIDSuffix;

    EntityList() {
        nextIDSuffix = 1;
    }

    /**
     * Gets the entity from the entityList.
     *
     * @param id
     * @return Entity
     */
    abstract Entity get(Id id);

    // This exception will be ModelException - Pending Joanna's PR.

    /**
     * Updates the given entity in the list using the id of the entity argument.
     *
     * @param entity
     * @throws Exception
     */
    abstract void update(Entity entity) throws Exception;

    // As above, exception will be generalized.

    /**
     * Deletes the entity from the entity list using the ID.
     *
     * @param id
     * @throws Exception
     */
    abstract void delete(Id id) throws Exception;

    /**
     * Adds the entity into the entity list
     *
     * @param entity
     * @throws Exception
     */
    abstract void add(Entity entity) throws Exception;

    /**
     * Checks if a given entity list contains a certain entity.
     *
     * @param id
     * @return boolean
     */
    abstract boolean isContain(Id id);

    /**
     * List the entities.
     *
     * @return List<? extends Entity>
     */
    abstract List<? extends Entity> list();

    /**
     * This generates the id for the next entity object to be created.
     */
    abstract public Id generateID();

    protected int getNewIDSuffix() {
        int next = this.nextIDSuffix;
        this.nextIDSuffix++;
        return next;
    }
}
