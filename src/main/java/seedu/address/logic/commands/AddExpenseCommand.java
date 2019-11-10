package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ViewState;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Expense;


/**
 * Adds an expense to the expense list.
 */
public class AddExpenseCommand extends Command {

    public static final String COMMAND_WORD = "expense";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an expense to the expense list. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_AMOUNT + "AMOUNT "
            + "[" + PREFIX_CURRENCY + "CURRENCY] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TAG + "TAG]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Textbook "
            + PREFIX_AMOUNT + "23.50 "
            + PREFIX_CURRENCY + "USD "
            + PREFIX_DATE + "1245 "
            + PREFIX_TAG + "education ";

    public static final String MESSAGE_SUCCESS = "New expense added: %1$s";
    public static final String MESSAGE_DUPLICATE_EXPENSE = "This expense already exists in the expense list";
    public static final String MESSAGE_ADD_ERROR = "An error occurred while trying to add the expense";

    private final Expense toAdd;

    /**
     * Creates an AddExpenseCommand to add the specified {@code Expense}
     */
    public AddExpenseCommand(Expense expense) {
        requireNonNull(expense);
        toAdd = expense;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        ViewState viewState = model.getViewState();
        Budget lastViewedBudget = model.getLastViewedBudget();

        if (model.hasExpense(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXPENSE);
        }

        model.addExpense(toAdd);

        if (viewState.equals(ViewState.DEFAULT_EXPENSELIST)) {
            return new CommandResult(model.getFilteredExpenseList(), null, null,
                String.format(MESSAGE_SUCCESS, toAdd));
        } else if (viewState.equals(ViewState.BUDGETLIST)) {
            return new CommandResult(null, model.getFilteredBudgetList(), null,
                String.format(MESSAGE_SUCCESS, toAdd));
        } else if (viewState.equals(ViewState.EXPENSELIST_IN_BUDGET)) {
            return new CommandResult(model.getExpenseListFromBudget(lastViewedBudget), null, lastViewedBudget,
                String.format(MESSAGE_SUCCESS, toAdd));
        } else {
            throw new CommandException(MESSAGE_ADD_ERROR);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddExpenseCommand // instanceof handles nulls
                && toAdd.equals(((AddExpenseCommand) other).toAdd));
    }
}
