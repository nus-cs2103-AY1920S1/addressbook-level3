package seedu.address.model.entitylist;

import java.util.List;

import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Id;

/**
 * This interface serves as the new API for the model.
 * Each child of {@code EntityList} should behave as a singleton.
 */
public abstract class EntityList implements ReadableEntityList {
    //    /**
    //     * Gets the entity from the entityList.
    //     *
    //     * @param id
    //     * @return Entity
    //     */
    //    abstract Entity get(Id id);
    //
    //    // This exception will be ModelException - Pending Joanna's PR.
    //
    //    /**
    //     * Updates the given entity in the list using the id of the entity argument.
    //     *
    //     * @param entity
    //     * @return boolean
    //     */
    //    abstract boolean update(Entity entity);
    //
    //    // As above, exception will be generalized.
    //
    //    /**
    //     * Deletes the entity from the entity list using the ID.
    //     *
    //     * @param id
    //     * @throws Exception
    //     */
    //    abstract  delete(Id id) throws AlfredException;
    //
    //    /**
    //     * Adds the entity into the entity list
    //     *
    //     * @param entity
    //     * @throws Exception
    //     */
    // abstract void add(Entity entity) throws AlfredException;

    public abstract boolean contains(Id id);

    public abstract List<? extends Entity> list();
}
