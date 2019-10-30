package seedu.address.logic.commands.budget;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.address.logic.commands.CommandGroup;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GenericCommandWord;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.budget.Budget;
import seedu.address.ui.budget.BudgetPanel;

/**
 * Adds a budget to MooLah.
 */
public class AddBudgetCommand extends UndoableCommand {

    public static final String COMMAND_WORD = GenericCommandWord.ADD + CommandGroup.BUDGET;
    public static final String COMMAND_DESCRIPTION = "Add budget %1$s (%2$s)";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an budget to MooLah.\n"
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_PRICE + "AMOUNT "
            + PREFIX_START_DATE + "START DATE "
            + PREFIX_PERIOD + "PERIOD"
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "school related expenses "
            + PREFIX_PRICE + "300 "
            + PREFIX_START_DATE + "23-06 "
            + PREFIX_PERIOD + "month";

    public static final String MESSAGE_SUCCESS = "New budget added:\n %1$s";
    public static final String MESSAGE_DUPLICATE_BUDGET = "This budget already exists in MooLah";

    private final Budget toAdd;

    public AddBudgetCommand(Budget budget) {
        requireNonNull(budget);
        toAdd = budget;
    }

    @Override
    public String getDescription() {
        return String.format(COMMAND_DESCRIPTION, toAdd.getDescription(), toAdd.getAmount());
    }

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasBudget(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_BUDGET);
        }
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);

        model.addBudget(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), BudgetPanel.PANEL_NAME);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddBudgetCommand // instanceof handles nulls
                && toAdd.equals(((AddBudgetCommand) other).toAdd));
    }
}
