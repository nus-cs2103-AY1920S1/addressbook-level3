package thrift.logic.commands;

import static java.util.Objects.requireNonNull;
import static thrift.commons.util.CollectionUtil.requireAllNonNull;

import thrift.logic.parser.CliSyntax;
import thrift.model.Model;
import thrift.model.transaction.Budget;

/**
 * Sets the monthly budget.
 */
public class BudgetCommand extends NonScrollingCommand implements Undoable {

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
    private Budget oldBudget;
    /**
     * Creates a BudgetCommand with the specified {@code Value}.
     */
    public BudgetCommand(Budget budget) {
        requireNonNull(budget);
        this.budget = budget;
        this.oldBudget = null;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        oldBudget = model.setBudget(budget).orElse(null);
        return new CommandResult(String.format(MESSAGE_SUCCESS, budget));
    }

    @Override
    public void undo(Model model) {
        requireAllNonNull(model, budget);
        if (oldBudget == null) {
            model.resetBudgetForThatMonth(budget);
        } else {
            model.setBudget(oldBudget);
        }
    }

    @Override
    public void redo(Model model) {
        requireAllNonNull(model, budget);
        model.setBudget(budget);
    }
}
