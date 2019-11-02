package seedu.ichifund.logic.commands.repeater;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.ichifund.commons.core.Messages;
import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.repeater.RepeaterUniqueId;
import seedu.ichifund.model.transaction.Transaction;

/**
 * Deletes a repeater identified using it's displayed index.
 */
public class DeleteRepeaterCommand extends Command {

    public static final String COMMAND_WORD = "del";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the repeater identified by the index number used in the displayed repeater list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_REPEATER_SUCCESS = "Deleted Repeater: %1$s";

    private final Index targetIndex;

    public DeleteRepeaterCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Repeater> lastShownList = model.getFilteredRepeaterList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REPEATER_DISPLAYED_INDEX);
        }

        Repeater repeaterToDelete = lastShownList.get(targetIndex.getZeroBased());
        deleteRepeaterTransactions(model, repeaterToDelete.getUniqueId());
        model.deleteRepeater(repeaterToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_REPEATER_SUCCESS, repeaterToDelete));
    }

    /**
     * Deletes all transactions associated with the specified {@code RepeaterUniqueId}.
     */
    private void deleteRepeaterTransactions(Model model, RepeaterUniqueId repeaterUniqueId) {
        for (Transaction transaction : model.getAssociatedTransactions(repeaterUniqueId)) {
            model.deleteTransaction(transaction);
        }
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteRepeaterCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteRepeaterCommand) other).targetIndex)); // state check
    }
}
