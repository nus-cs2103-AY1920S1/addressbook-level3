package seedu.address.financialtracker.commands;

import seedu.address.financialtracker.Model.Expense.Expense;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.financialtracker.Model.Model;

import static java.util.Objects.requireNonNull;

/**
 * add financial expenses command for Financial Tracker.
 */
public class AddFinCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an expense to the financial tracker ";

    public static final String MESSAGE_FAIL = "Unknown error, your expenses are not added.";
    public static final String MESSAGE_SUCCESS = "Expense added";

    private final Expense expense;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddFinCommand(Expense expense) {
        requireNonNull(expense);
        this.expense = expense;
    }

    public CommandResult execute(Model model) throws CommandException {
        model.addExpense(expense);
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }
}
