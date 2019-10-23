package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.queue.Room;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class RemoveRoomCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "removeRoom";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the room identified by the index number used in the displayed rooms.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DEQUEUE_SUCCESS = "Removed room: %1$s";
    public static final String MESSAGE_DEQUEUE_PERSON_NOT_FOUND =
            Messages.MESSAGE_INVAILD_REFERENCE_ID + ". '%1$s' has been removed from list";
    public static final String MESSAGE_PERSON_NOT_IN_QUEUE = "This room '%1$s' is not in the list";

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

        return new CommandResult(String.format(MESSAGE_DEQUEUE_SUCCESS, roomToRemove));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveRoomCommand // instanceof handles nulls
                && roomToRemove.equals(((RemoveRoomCommand) other).roomToRemove)); // state check
    }
}
