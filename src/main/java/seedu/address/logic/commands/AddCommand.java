package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expense.Expense;

/**
 * Adds an expense to the expense list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an expense to the expense list. "
            + "Parameters: "
            + PREFIX_NAME + " NAME "
            + PREFIX_AMOUNT + " AMOUNT "
            + PREFIX_DATE + " DATE "
            + "[" + PREFIX_TAG + " TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + " Textbook "
            + PREFIX_AMOUNT + " $23.50 "
            + PREFIX_DATE + " 1245 "
            + PREFIX_TAG + " education "
            + PREFIX_TAG + " school";

    public static final String MESSAGE_SUCCESS = "New expense added: %1$s";
    public static final String MESSAGE_DUPLICATE_EXPENSE = "This expense already exists in the expense list";

    private final Expense toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Expense}
     */
    public AddCommand(Expense expense) {
        requireNonNull(expense);
        toAdd = expense;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasExpense(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXPENSE);
        }

        model.addExpense(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
