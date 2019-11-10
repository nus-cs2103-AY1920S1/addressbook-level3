package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ViewState;
import seedu.address.model.budget.Budget;

/**
 * Adds a budget into the budget list.
 */
public class AddBudgetCommand extends Command {

    public static final String COMMAND_WORD = "budget";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a budget to the budget list.\n"
                                                            + "Parameters: "
                                                            + PREFIX_NAME + "NAME "
                                                            + PREFIX_AMOUNT + "AMOUNT "
                                                            + "[" + PREFIX_CURRENCY + "CURRENCY] "
                                                            + PREFIX_DATE + "START-DATE "
                                                            + PREFIX_END_DATE + "END-DATE...\n"
                                                            + "Example: " + COMMAND_WORD + " "
                                                            + PREFIX_NAME + "Japan Travel "
                                                            + PREFIX_AMOUNT + "$2000.00 "
                                                            + PREFIX_CURRENCY + "USD "
                                                            + PREFIX_DATE + "12/12/2019 "
                                                            + PREFIX_END_DATE + "18/12/2019\n";

    public static final String MESSAGE_SUCCESS = "New budget added: %1$s";
    public static final String MESSAGE_DUPLICATE_BUDGET = "This budget already exists in the budget list";
    public static final String MESSAGE_BUDGET_CLASH = "This budget period clashes with another budget";
    public static final String MESSAGE_START_BEFORE_END = "The budget end date has to be after its start date";

    private final Budget toAdd;

    /**
     * Creates an AddBudgetCommand to add the specified {@code Budget}
     */
    public AddBudgetCommand(Budget budget) {
        requireNonNull(budget);
        toAdd = budget;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (toAdd.getEndDate().localDate.isBefore(toAdd.getStartDate().localDate)) {
            throw new CommandException(MESSAGE_START_BEFORE_END);
        }

        if (model.hasBudgetPeriodClash(toAdd)) {
            throw new CommandException(MESSAGE_BUDGET_CLASH);
        }

        if (model.hasBudget(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_BUDGET);
        }

        model.addBudget(toAdd);
        model.setViewState(ViewState.BUDGETLIST);
        return new CommandResult(null, model.getFilteredBudgetList(), null, String.format(MESSAGE_SUCCESS, toAdd));
    }
}
