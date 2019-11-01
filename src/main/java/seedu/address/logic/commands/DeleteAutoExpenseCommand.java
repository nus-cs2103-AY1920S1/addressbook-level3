package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.AutoExpense;

/**
 * Deletes a AutoExpense identified using its displayed index from the finance tracker.
 */
public class DeleteAutoExpenseCommand extends Command {

    public static final String COMMAND_WORD = "deleteAutoExp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the autoExpense identified by the index number used in the displayed autoExpense list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ENTRY_SUCCESS = "Deleted AutoExpense: %1$s";

    private final Index targetIndex;

    public DeleteAutoExpenseCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<AutoExpense> lastShownList = model.getFilteredAutoExpenses();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        AutoExpense entryToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteAutoExpense(entryToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_ENTRY_SUCCESS, entryToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteAutoExpenseCommand // instanceof handles nulls
                        && targetIndex.equals(((DeleteAutoExpenseCommand) other).targetIndex)); // state check
    }
}
