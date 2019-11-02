package seedu.address.logic.commands.expense;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandGroup;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GenericCommandWord;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.model.Model;
import seedu.address.ui.expense.ExpenseListPanel;

/**
 * Lists all expenses in the MooLah to the user.
 */
public class ListExpensesCommand extends Command {

    public static final String COMMAND_WORD = GenericCommandWord.LIST + CommandGroup.EXPENSE + "s";
    public static final String COMMAND_DESCRIPTION = "List expenses";
    public static final String MESSAGE_SUCCESS = "Listed all expenses";

    @Override
    protected void validate(Model model) {
        // No validation necessary.
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);
        return new CommandResult(MESSAGE_SUCCESS, ExpenseListPanel.PANEL_NAME);
    }
}
