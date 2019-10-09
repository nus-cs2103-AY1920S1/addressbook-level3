package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ID;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.common.ReferenceId;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DequeueCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "dequeue";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Dequeues the person identified by the index number used in the displayed queue.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DEQUEUE_SUCCESS = "Dequeued person: %1$s";
    public static final String MESSAGE_UNDO_DEQUEUE_SUCCESS = "Undo successful! Person '%1$s' has been enqueued.";
    public static final String MESSAGE_UNDO_DEQUEUE_ERROR = "Could not undo the dequeue of person.";

    private final Index targetIndex;
    private ReferenceId referenceId;

    public DequeueCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.referenceId = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<ReferenceId> lastShownList = model.getFilteredReferenceIdList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (referenceId == null) {
            referenceId = lastShownList.get(targetIndex.getZeroBased());
        } else if (!model.hasPerson(referenceId)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        model.removePatient(referenceId);
        return new CommandResult(String.format(MESSAGE_DEQUEUE_SUCCESS, referenceId));
    }

    @Override
    public CommandResult undo(Model model) throws CommandException {
        requireNonNull(model);

        if (referenceId == null || !model.hasPerson(referenceId) || model.hasId(referenceId)) {
            throw new CommandException(MESSAGE_UNDO_DEQUEUE_ERROR);
        }

        model.addPatient(referenceId);
        model.updateFilteredReferenceIdList(PREDICATE_SHOW_ALL_ID);
        return new CommandResult(String.format(MESSAGE_UNDO_DEQUEUE_SUCCESS, referenceId));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DequeueCommand // instanceof handles nulls
                && targetIndex.equals(((DequeueCommand) other).targetIndex)); // state check
    }
}
