package thrift.logic.commands;

import static java.util.Objects.requireNonNull;
import static thrift.commons.util.CollectionUtil.requireAllNonNull;

import java.text.SimpleDateFormat;

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

    public static final String UNDO_SUCCESS = "Budget for the month %1$s is reset to: %2$s";
    public static final String REDO_SUCCESS = "Budget for the month %1$s is reset to: %2$s";

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
    public String undo(Model model) {
        requireAllNonNull(model, budget);
        assert oldBudget == null || budget.getBudgetDate().equals(oldBudget.getBudgetDate());
        String monthYear = new SimpleDateFormat("MMMMM yyyy").format(budget.getBudgetDate().getTime());
        if (oldBudget == null) {
            model.resetBudgetForThatMonth(budget);
            return String.format(UNDO_SUCCESS, monthYear, "-");
        } else {
            model.setBudget(oldBudget);
            return String.format(UNDO_SUCCESS, monthYear, oldBudget);
        }
    }

    @Override
    public String redo(Model model) {
        requireAllNonNull(model, budget);
        String monthYear = new SimpleDateFormat("MMMM yyyy").format(budget.getBudgetDate().getTime());
        model.setBudget(budget);
        return String.format(REDO_SUCCESS, monthYear, budget);
    }
}
