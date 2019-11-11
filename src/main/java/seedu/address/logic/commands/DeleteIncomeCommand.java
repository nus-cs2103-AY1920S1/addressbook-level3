package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.income.Income;

/**
 * Deletes an income identified using it's displayed index from the income list.
 */
public class DeleteIncomeCommand extends Command {

    public static final String COMMAND_WORD = "delete_income";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the income identified by the index number used in the displayed incomes list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_INCOME_SUCCESS = "Deleted Income: %1$s";

    private final Index targetIndex;

    public DeleteIncomeCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Income> lastShownList = model.getFilteredIncomeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INCOME_DISPLAYED_INDEX);
        }

        Income incomeToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteIncome(incomeToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_INCOME_SUCCESS, incomeToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteIncomeCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteIncomeCommand) other).targetIndex)); // state check
    }


}
