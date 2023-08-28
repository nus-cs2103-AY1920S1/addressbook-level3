package seedu.address.logic.parser.utility;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.entity.Entity;

//@@author ambervoong
/**
 * Stores the details to update the entity with. Each non-empty field value will replace the
 * corresponding field value of the entity.
 */
public interface UpdateEntityDescriptor {

    /**
     * Returns true if at least one field is edited.
     */
    boolean isAnyFieldEdited();

    /**
     * Changes the entity's fields according to the descriptor to the updated values in the UpdateEntityDescriptor
     * object if they are present. Uses the existing values in the entity otherwise.
     * Guarantees: the given entity exists.
     */
    Entity apply(Entity entity) throws CommandException;

    /**
     * Changes all of the entity's fields according to the descriptor, even for null fields.
     */
    Entity applyOriginal(Entity entity);
}
