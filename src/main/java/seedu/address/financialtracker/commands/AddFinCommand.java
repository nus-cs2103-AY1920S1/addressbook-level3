package seedu.address.financialtracker.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.financialtracker.model.Model;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * add financial expenses command for Financial Tracker.
 */
public class AddFinCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an expense to the financial tracker.\n"
            + "e.g. add a/[amount] d/[description c/[category or country]";
    public static final String MESSAGE_SUCCESS = "Expense added";

    private final Expense expense;

    /**
     * Creates an AddFinCommand to add the specified {@code Expense}
     */
    public AddFinCommand(Expense expense) {
        requireNonNull(expense);
        this.expense = expense;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.addExpense(expense);
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }
}
