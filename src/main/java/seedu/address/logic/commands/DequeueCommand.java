package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.core.CommandResult;
import seedu.address.logic.commands.core.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DequeueCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "dequeue";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Dequeues the person identified by the index number used in the displayed queue.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DEQUEUE_SUCCESS = "Dequeued person: %1$s";
    public static final String MESSAGE_UNDO_DEQUEUE_SUCCESS = "Undo successful! Person '%1$s' has been enqueued.";
    public static final String MESSAGE_UNDO_DEQUEUE_ERROR = "Could not undo the dequeue of person.";

    private final Index targetIndex;
    private Person personToDelete;

    public DequeueCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.personToDelete = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPatientList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (personToDelete == null) {
            personToDelete = lastShownList.get(targetIndex.getZeroBased());
        } else if (!model.hasPatient(personToDelete)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        model.removePatient(personToDelete);
        return new CommandResult(String.format(MESSAGE_DEQUEUE_SUCCESS, personToDelete));
    }

    @Override
    public CommandResult undo(Model model) throws CommandException {
        requireNonNull(model);

        if (personToDelete == null || model.hasPatient(personToDelete)) {
            throw new CommandException(MESSAGE_UNDO_DEQUEUE_ERROR);
        }

        model.addPatient(personToDelete);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_UNDO_DEQUEUE_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DequeueCommand // instanceof handles nulls
                && targetIndex.equals(((DequeueCommand) other).targetIndex)); // state check
    }
}
