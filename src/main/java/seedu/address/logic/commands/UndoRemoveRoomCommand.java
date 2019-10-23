package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.queue.Room;

/**
 * Lists all persons in the address book to the user.
 */
public class UndoRemoveRoomCommand extends ReversibleCommand {

    public static final String MESSAGE_SUCCESS = "Room added back to the list: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This room already exists in the list";

    private final Room roomToAdd;
    private final int indexOfPatientInQueue;

    /**
     * Creates an EnqueueCommand to add the specified {@code PatientReferenceId}
     */
    public UndoRemoveRoomCommand(Room room, int index) {
        requireNonNull(room);
        this.roomToAdd = room;
        this.indexOfPatientInQueue = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasRoom(roomToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addRoom(roomToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, roomToAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UndoRemoveRoomCommand // instanceof handles nulls
                && roomToAdd.equals(((UndoRemoveRoomCommand) other).roomToAdd));
    }
}
