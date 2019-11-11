//@@author wongsm7
package seedu.address.logic.commands.queue;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.queue.Room;

/**
 * Sets a doctor who is on break back to duty.
 */
public class ResumeCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "resume";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Doctor is back from his/her break. Allows patients to be directed to the doctor.\n"
            + "Parameters: INDEX (must be a non-zero positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Doctor %s resumes his/her duty";
    public static final String MESSAGE_DUPLICATE_DOCTOR = "This doctor already exists in the list.";
    public static final String MESSAGE_NOT_ON_BREAK = "Doctor is not on break";

    private final Room roomToEdit;
    private final Room editedRoom;

    public ResumeCommand(Room roomToEdit, Room editedRoom) {
        this.editedRoom = editedRoom;
        this.roomToEdit = roomToEdit;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (roomToEdit.isReadyToServe()) {
            throw new CommandException(MESSAGE_NOT_ON_BREAK);
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
                || (other instanceof ResumeCommand // instanceof handles nulls
                && editedRoom.equals(((ResumeCommand) other).editedRoom)
                && roomToEdit.equals(((ResumeCommand) other).roomToEdit)); // state check
    }
}
