//@@author wongsm7
package seedu.address.logic.commands.queue;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.queue.Room;

/**
 * Removes a doctor who is on duty.
 */
public class RemoveRoomCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "offduty";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the doctor on-duty from the list.\n"
            + "Parameters: INDEX (must be a non-zero positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_OFF_DUTY_SUCCESS = "%s is off-duty";
    public static final String MESSAGE_PERSON_NOT_IN_QUEUE = "This doctor '%1$s' is not in the list";

    private final Room roomToRemove;

    public RemoveRoomCommand(Room roomToRemove) {
        requireNonNull(roomToRemove);
        this.roomToRemove = roomToRemove;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasRoom(roomToRemove)) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_IN_QUEUE, roomToRemove));
        }
        model.removeRoom(roomToRemove);

        return new CommandResult(String.format(MESSAGE_OFF_DUTY_SUCCESS,
                model.resolveStaff(roomToRemove.getDoctor()).getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveRoomCommand // instanceof handles nulls
                && roomToRemove.equals(((RemoveRoomCommand) other).roomToRemove)); // state check
    }
}
