//@@author SakuraBlossom
package seedu.address.logic.commands.patients;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a patient identified using it's displayed index from the address book.
 */
public class UnregisterPatientCommand extends ReversibleCommand {

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Unregistered patient: %1$s";
    public static final String MESSAGE_PERSON_IN_QUEUE_DELETE_FAILED =
            "Patient is in the queue. Could not unregister!\n%1$s";

    private final Person toDelete;

    public UnregisterPatientCommand(Person toDelete) {
        requireNonNull(toDelete);
        this.toDelete = toDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasExactPatient(toDelete)) {
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, toDelete));
        }

        if (model.isPatientInQueue(toDelete.getReferenceId())) {
            throw new CommandException(String.format(MESSAGE_PERSON_IN_QUEUE_DELETE_FAILED, toDelete));
        }

        model.deletePatient(toDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, toDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnregisterPatientCommand // instanceof handles nulls
                && toDelete.equals(((UnregisterPatientCommand) other).toDelete)); // state check
    }
}
