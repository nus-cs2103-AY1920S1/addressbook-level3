package seedu.address.logic.commands.queue;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.queue.Room;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class ResumeCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "resume";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Doctor is back from his/her break\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Doctor %s resumes his/her duty";
    public static final String MESSAGE_DUPLICATE_ROOM = "This room already exists in the list.";
    public static final String MESSAGE_ALREADY_BACK_FROM_BREAK = "Doctor is already back from his/her break";

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
            throw new CommandException(MESSAGE_ALREADY_BACK_FROM_BREAK);
        }
        model.removeRoom(roomToEdit);

        if (model.hasRoom(editedRoom)) {
            throw new CommandException(MESSAGE_DUPLICATE_ROOM);
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
