package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_UNDO_DELETE_ERROR = "Could not undo the removal of Person '%1$s'.";

    private final Person toDelete;

    public DeleteCommand(Person toDelete) {
        requireNonNull(toDelete);
        this.toDelete = toDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasExactPerson(toDelete)) {
            throw new CommandException(String.format(Messages.MESSAGE_PERSON_NOT_FOUND, toDelete));
        }

        if (model.isPatientInQueue(toDelete.getReferenceId())) {
            model.removeFromQueue(toDelete.getReferenceId());
        }

        model.deletePerson(toDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, toDelete));
    }

    @Override
    public String getFailedUndoMessage() {
        return String.format(MESSAGE_UNDO_DELETE_ERROR, toDelete);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && toDelete.equals(((DeleteCommand) other).toDelete)); // state check
    }
}
