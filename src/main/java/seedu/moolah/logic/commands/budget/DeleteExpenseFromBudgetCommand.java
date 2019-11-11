package seedu.moolah.logic.commands.budget;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.moolah.commons.core.Messages;
import seedu.moolah.commons.core.index.Index;
import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.GenericCommandWord;
import seedu.moolah.logic.commands.UndoableCommand;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.ui.budget.BudgetPanel;

/**
 * Deletes an expense identified using its displayed index from primary budget panel.
 */
public class DeleteExpenseFromBudgetCommand extends UndoableCommand {
    public static final String COMMAND_WORD = GenericCommandWord.DELETE + CommandGroup.PRIMARY_BUDGET;
    public static final String COMMAND_DESCRIPTION = "Delete expense with index %1$d from budget";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the expense identified by the index number used in the "
            + "displayed expense list in this budget.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EXPENSE_SUCCESS = "Deleted Expense: %1$s";

    private final Index targetIndex;

    public DeleteExpenseFromBudgetCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Returns a description of this DeleteExpenseFromBudgetCommand.
     *
     * @return A string that describes this DeleteExpenseFromBudgetCommand.
     */
    @Override
    public String getDescription() {
        return String.format(COMMAND_DESCRIPTION, targetIndex.getOneBased());
    }

    /**
     * Validates this DeleteExpenseFromBudgetCommand with the current model, before execution.
     *
     * @param model The current model.
     * @throws CommandException If the index is invalid.
     */
    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);
        List<Expense> lastShownList = model.getPrimaryBudget().getCurrentPeriodExpenses();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
        }
    }

    /**
     * Executes this DeleteExpenseFromBudgetCommand with the current model.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A CommandResult consisting of success message and panel change request.
     */
    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);
        List<Expense> lastShownList = model.getPrimaryBudget().getCurrentPeriodExpenses();

        Expense expenseToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteExpense(expenseToDelete);
        Budget primaryBudget = model.getPrimaryBudget();
        Budget primaryBudgetCopy = primaryBudget.deepCopy();
        primaryBudgetCopy.removeExpense(expenseToDelete);
        model.setBudget(primaryBudget, primaryBudgetCopy);
        return new CommandResult(String.format(MESSAGE_DELETE_EXPENSE_SUCCESS, expenseToDelete),
                BudgetPanel.PANEL_NAME);
    }

    /**
     * Checks whether another object is identical to this DeleteExpenseFromBudgetCommand.
     *
     * @param other The other object to be compared.
     * @return True if the other object is a DeleteExpenseFromBudgetCommand with the same index, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteExpenseFromBudgetCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteExpenseFromBudgetCommand) other).targetIndex)); // state check
    }

}
