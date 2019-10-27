package seedu.address.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.TrackableState;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Id;

/**
 * Deletes an {@link Entity} in Alfred.
 */
public abstract class DeleteCommand extends Command implements TrackableState {

    /* Possible Fields */

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the specified entity by the ID provided in the input\n"
            + "Delete command must specify the entity being deleted "
            + "along with its ID. \n"
            + "Format: delete [entity] [entity ID] \n"
            + "For example: delete participant P-1";

    protected Id id;

    protected DeleteCommand(Id id) {
        requireNonNull(id);
        this.id = id;
    }

}
