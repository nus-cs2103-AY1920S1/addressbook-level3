package seedu.address.logic.commands.viewcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Id;

/**
 * Shows detailed view of the {@link Entity} at specified ID.
 */
public abstract class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": shows details of the "
            + "entity with the specified ID. \n"
            + "Format: view [entity name] [entity ID] \n"
            + "For example (to view Mentor with ID M-1): " + COMMAND_WORD + " mentor M-1";

    protected Id id;

    ViewCommand(Id id) {
        requireNonNull(id);
        this.id = id;
    }

    /**
     * Prints detailed information regarding given entity.
     *
     * @param entity Entity to view.
     */
    void displayDetailedEntity(Entity entity) {
        System.out.println("Viewing " + entity.getName());
        System.out.println("\t" + entity.toString());
    }

}
