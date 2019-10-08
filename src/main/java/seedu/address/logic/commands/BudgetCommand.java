package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.budget.Budget;

/**
 * Adds a budget to Moolah.
 */
public class BudgetCommand extends Command {
    public static final String COMMAND_WORD = "budget";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an budget to the address book. "
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_PRICE + "AMOUNT "
            + PREFIX_START_DATE + "START DATE "
            + PREFIX_PERIOD + "PERIOD"
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "school related expenses "
            + PREFIX_PRICE + "300 "
            + PREFIX_START_DATE + "10-1 "
            + PREFIX_PERIOD + "month";

    public static final String MESSAGE_SUCCESS = "New budget added:\n %1$s";
    public static final String MESSAGE_DUPLICATE_BUDGET = "This budget already exists in Moolah";

    private final Budget toAdd;

    public BudgetCommand(Budget budget) {
        requireNonNull(budget);
        toAdd = budget;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasBudget(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_BUDGET);
        }

        model.addBudget(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BudgetCommand // instanceof handles nulls
                && toAdd.equals(((BudgetCommand) other).toAdd));
    }
}
