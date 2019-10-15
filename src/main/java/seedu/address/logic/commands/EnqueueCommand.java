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
public class EnqueueCommand extends ReversibleCommand {

    public static final String MESSAGE_SUCCESS = "New person added to the queue: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the queue";

    public static final String COMMAND_WORD = "enqueue";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enqueues a patient to the queue. "
            + "Parameters: "
            + "REFERENCE_ID ";

    private final ReferenceId patientReferenceId;

    /**
     * Creates an EnqueueCommand to add the specified {@code PatientReferenceId}
     */
    public EnqueueCommand(ReferenceId patientReferenceId) {
        requireNonNull(patientReferenceId);
        this.patientReferenceId = patientReferenceId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.isPatientInQueue(patientReferenceId)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } else if (!model.hasPerson(patientReferenceId)) {
            throw new CommandException(String.format(Messages.MESSAGE_INVAILD_REFERENCE_ID, patientReferenceId));
        }

        model.enqueuePatient(patientReferenceId);
        return new CommandResult(String.format(MESSAGE_SUCCESS, patientReferenceId));
    }

    /*
    @Override
    public CommandResult undo(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasPerson(toAdd)) {
            throw new CommandException(String.format(MESSAGE_UNDO_ADD_ERROR, toAdd));
        }

        model.removeFromQueue(toAdd);
        return new CommandResult(String.format(MESSAGE_UNDO_ADD_SUCCESS, toAdd));
    }
    */

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EnqueueCommand // instanceof handles nulls
                && patientReferenceId.equals(((EnqueueCommand) other).patientReferenceId));
    }
}
