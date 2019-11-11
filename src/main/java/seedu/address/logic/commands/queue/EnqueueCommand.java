//@@author wongsm7
package seedu.address.logic.commands.queue;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReferenceId;

/**
 * Adds a patient to the queue.
 */
public class EnqueueCommand extends ReversibleCommand {

    public static final String MESSAGE_SUCCESS = "New patient added to the queue: %s";
    public static final String MESSAGE_DUPLICATE_PERSON_IN_QUEUE = "This patient already exists in the queue";
    public static final String MESSAGE_DUPLICATE_PERSON_BEING_SERVED = "This patient is already being served";
    public static final String COMMAND_WORD = "enqueue";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enqueues a patient to the queue. "
            + "Parameters: "
            + "REFERENCE_ID \n"
            + "Example: " + COMMAND_WORD + " E0000001A";

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
            throw new CommandException(MESSAGE_DUPLICATE_PERSON_IN_QUEUE);
        } else if (!model.hasPatient(patientReferenceId)) {
            throw new CommandException(String.format(Messages.MESSAGE_INVAILD_REFERENCE_ID, patientReferenceId));
        } else if (model.isPatientBeingServed(patientReferenceId)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON_BEING_SERVED);
        }

        model.enqueuePatient(patientReferenceId);
        return new CommandResult(String.format(MESSAGE_SUCCESS, model.resolvePatient(patientReferenceId).getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EnqueueCommand // instanceof handles nulls
                && patientReferenceId.equals(((EnqueueCommand) other).patientReferenceId));
    }
}
