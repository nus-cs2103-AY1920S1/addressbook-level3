package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.queue.Room;

/**
 * Lists all persons in the address book to the user.
 */
public class AddConsultationRoomCommand extends ReversibleCommand {

    public static final String MESSAGE_SUCCESS = "New room added : %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This room already exists in the list";

    public static final String COMMAND_WORD = "addRoom";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new room. "
            + "Parameters: "
            + "REFERENCE_ID \n"
            + "Example: " + COMMAND_WORD + " 001A";

    private final Room roomToAdd;

    /**
     * Creates an EnqueueCommand to add the specified {@code PatientReferenceId}
     */
    public AddConsultationRoomCommand(Room roomToAdd) {
        requireNonNull(roomToAdd);
        this.roomToAdd = roomToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasRoom(roomToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } else if (!model.hasStaff(roomToAdd.getDoctor())) {
            throw new CommandException(String.format(Messages.MESSAGE_INVAILD_REFERENCE_ID, roomToAdd));
        }

        model.addRoom(roomToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, roomToAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddConsultationRoomCommand // instanceof handles nulls
                && roomToAdd.equals(((AddConsultationRoomCommand) other).roomToAdd));
    }
}
