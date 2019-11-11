//@@author wongsm7
package seedu.address.logic.commands.queue;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.queue.Room;

/**
 * Adds a doctor to the list of doctors on duty.
 */
public class AddConsultationRoomCommand extends ReversibleCommand {

    public static final String MESSAGE_SUCCESS = "%s is now on duty.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This doctor already exists in the list";

    public static final String COMMAND_WORD = "onduty";
    public static final String MESSAGE_INVALID_DOCTOR_ID = "The reference ID '%1$s' does not belong "
            + "to any registered doctor!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a doctor to the list of doctors on-duty.\n"
            + "Parameters: INDEX (must be a non-zero positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Room roomToAdd;

    /**
     * Creates an AddConsultationRoomCommand to add the specified {@code PatientReferenceId}
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
            throw new CommandException(String.format(MESSAGE_INVALID_DOCTOR_ID,
                    roomToAdd.getDoctor()));
        }

        model.addRoom(roomToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                model.resolveStaff(roomToAdd.getDoctor()).getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddConsultationRoomCommand // instanceof handles nulls
                && roomToAdd.equals(((AddConsultationRoomCommand) other).roomToAdd));
    }
}
