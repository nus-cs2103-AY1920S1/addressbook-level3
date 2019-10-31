package seedu.address.logic.commands.removecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.TrackableState;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Id;

/**
 * Assigns an {@link Entity} to a Team in Alfred
 */
public abstract class RemoveCommand extends Command implements TrackableState {

    /* Possible Fields */

    public static final String COMMAND_WORD = "remove";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns the specified entity by ID to a Team with Team-ID\n"
            + "Assign command must specify the entity being assigned to a Team "
            + "along with its ID and the Team ID that the entity is assigned to. \n"
            + "Format: assign [entity] [entity ID] [team ID]\n"
            + "For example: assign mentor M-1 T-1";

    protected Id entityId;
    protected Id teamId;

    /**
     * Constructs an{@code AssignCommand} to assign Entity with entityId to Team with teamId.
     */
    protected RemoveCommand(Id entityId, Id teamId) {
        requireNonNull(entityId);
        requireNonNull(teamId);
        this.entityId = entityId;
        this.teamId = teamId;
    }

}

