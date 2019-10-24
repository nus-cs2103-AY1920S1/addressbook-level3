package thrift.logic.commands;

import static java.util.Objects.requireNonNull;

import thrift.logic.parser.CliSyntax;
import thrift.model.Model;
import thrift.model.transaction.Budget;

/**
 * Sets the monthly budget.
 */
public class BudgetCommand extends NonScrollingCommand {

    public static final String COMMAND_WORD = "budget";

    public static final String HELP_MESSAGE = COMMAND_WORD + ": Sets your monthly budget to the specified amount.\n"
            + "Format: "
            + COMMAND_WORD + " "
            + CliSyntax.PREFIX_VALUE + "AMOUNT "
            + CliSyntax.PREFIX_DATE + "MM/YYYY\n"
            + "Possible usages of " + COMMAND_WORD + ": \n"
            + "To set budget for this month (assuming this month is October 2019): "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_VALUE + "500 " + CliSyntax.PREFIX_DATE + "10/2019\n"
            + "To forecast your budget for a particular month: "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_VALUE + "1000 " + CliSyntax.PREFIX_DATE + "11/2019";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets your monthly budget to the specified amount.\n"
            + "Parameters: "
            + CliSyntax.PREFIX_VALUE + "AMOUNT "
            + CliSyntax.PREFIX_DATE + "MM/YYYY\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_VALUE + "1000 "
            + CliSyntax.PREFIX_DATE + "10/2019 ";

    public static final String MESSAGE_SUCCESS = "New budget: %1$s";

    private final Budget budget;

    /**
     * Creates a BudgetCommand with the specified {@code Value}.
     */
    public BudgetCommand(Budget budget) {
        requireNonNull(budget);
        this.budget = budget;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setBudget(budget);
        return new CommandResult(String.format(MESSAGE_SUCCESS, budget));
    }
}
