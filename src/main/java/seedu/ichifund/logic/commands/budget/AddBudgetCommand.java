package seedu.ichifund.logic.commands.budget;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.budget.Budget;

/**
 * Adds a budget to IchiFund.
 */
public class AddBudgetCommand extends Command {

    public static final String COMMAND_WORD = "badd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a budget to IchiFund. "
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_AMOUNT + "AMOUNT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Saving for my future "
            + PREFIX_AMOUNT + "200.00";

    public static final String MESSAGE_SUCCESS = "New budget added: %1$s";
    public static final String MESSAGE_DUPLICATE_BUDGET = "This budget already exists in IchiFund";

    private final Budget toAdd;

    /**
     * Creates an AddBudgetCommand to add the specified {@code Budget}
     */
    public AddBudgetCommand(Budget budget) {
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
                || (other instanceof AddBudgetCommand // instanceof handles nulls
                && toAdd.equals(((AddBudgetCommand) other).toAdd));
    }
}
