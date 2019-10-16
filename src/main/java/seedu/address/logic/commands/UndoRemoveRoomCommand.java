package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.common.ReferenceId;

/**
 * Lists all persons in the address book to the user.
 */
public class UndoRemoveRoomCommand extends ReversibleCommand {

    public static final String MESSAGE_SUCCESS = "Room added back to the list: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This room already exists in the list";

    private final ReferenceId doctorReferenceId;
    private final int indexOfPatientInQueue;

    /**
     * Creates an EnqueueCommand to add the specified {@code PatientReferenceId}
     */
    public UndoRemoveRoomCommand(ReferenceId doctorReferenceId, int index) {
        requireNonNull(doctorReferenceId);
        this.doctorReferenceId = doctorReferenceId;
        this.indexOfPatientInQueue = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.isPatientInQueue(doctorReferenceId)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } else if (!model.hasPerson(doctorReferenceId)) {
            throw new CommandException(String.format(Messages.MESSAGE_INVAILD_REFERENCE_ID, doctorReferenceId));
        }

        model.addRoomToIndex(doctorReferenceId, indexOfPatientInQueue);
        return new CommandResult(String.format(MESSAGE_SUCCESS, doctorReferenceId));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UndoRemoveRoomCommand // instanceof handles nulls
                && doctorReferenceId.equals(((UndoRemoveRoomCommand) other).doctorReferenceId));
    }
}
