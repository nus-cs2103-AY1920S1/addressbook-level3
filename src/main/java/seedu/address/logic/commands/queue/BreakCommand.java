//@@author wongsm7
package seedu.address.logic.commands.queue;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.queue.Room;

/**
 * Sets a doctor on duty to be on break.
 */
public class BreakCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "break";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the doctor as on break. Patients cannot be referred to the doctors on break.\n"
            + "Parameters: INDEX (must be a non-zero positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Doctor %s is on break";
    public static final String MESSAGE_ALREADY_ON_BREAK = "Doctor is already resting";
    public static final String MESSAGE_DUPLICATE_DOCTOR = "This doctor already exists in the list.";

    private final Room roomToEdit;
    private final Room editedRoom;

    public BreakCommand(Room roomToEdit, Room editedRoom) {
        this.editedRoom = editedRoom;
        this.roomToEdit = roomToEdit;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!roomToEdit.isReadyToServe()) {
            throw new CommandException(MESSAGE_ALREADY_ON_BREAK);
        }
        model.removeRoom(roomToEdit);

        if (model.hasRoom(editedRoom)) {
            throw new CommandException(MESSAGE_DUPLICATE_DOCTOR);
        }
        model.addRoom(editedRoom);
        return new CommandResult(String.format(MESSAGE_SUCCESS, model.resolveStaff(editedRoom.getDoctor()).getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BreakCommand // instanceof handles nulls
                && editedRoom.equals(((BreakCommand) other).editedRoom)
                && roomToEdit.equals(((BreakCommand) other).roomToEdit)); // state check
    }
}
