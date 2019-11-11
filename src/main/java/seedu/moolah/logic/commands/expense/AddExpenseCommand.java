package seedu.moolah.logic.commands.expense;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.GenericCommandWord;
import seedu.moolah.logic.commands.UndoableCommand;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.budget.Percentage;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.ui.budget.BudgetPanel;

/**
 * Adds a expense to the MooLah.
 */
public class AddExpenseCommand extends UndoableCommand {

    public static final String COMMAND_WORD = GenericCommandWord.ADD + CommandGroup.EXPENSE;
    public static final String COMMAND_WORD_FROM_PRIMARY = GenericCommandWord.ADD + CommandGroup.PRIMARY_BUDGET;

    public static final String COMMAND_DESCRIPTION = "Add expense %1$s (%2$s)";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an expense to MooLah and the primary budget. \n"
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_CATEGORY + "CATEGORY "
            + "[" + PREFIX_TIMESTAMP + "TIMESTAMP]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Chicken Rice "
            + PREFIX_PRICE + "3.50 "
            + PREFIX_CATEGORY + "Food "
            + PREFIX_TIMESTAMP + "10-10";

    public static final String MESSAGE_SUCCESS = "New expense added:\n %1$s";
    public static final String MESSAGE_DUPLICATE_EXPENSE = "This expense already exists in MooLah!";
    public static final String MESSAGE_BUDGET_EXCEEDED_TOO_FAR = "This expense will exceed the budget limit too far";

    private final Expense toAdd;

    /**
     * Creates an AddExpenseCommand to add the specified {@code Expense}
     */
    public AddExpenseCommand(Expense expense) {
        requireNonNull(expense);
        toAdd = expense;
    }

    @Override
    public String getDescription() {
        return String.format(COMMAND_DESCRIPTION, toAdd.getDescription(), toAdd.getPrice());
    }

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasExpense(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXPENSE);
        }

        Budget pb = model.getPrimaryBudget();
        double price = toAdd.getPrice().getAsDouble();
        double limit = pb.getAmount().getAsDouble();
        try {
            Percentage.calculate(pb.calculateExpenseSum() + price, limit);
        } catch (IllegalArgumentException e) {
            throw new CommandException(MESSAGE_BUDGET_EXCEEDED_TOO_FAR);
        }
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);

        model.addExpense(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), BudgetPanel.PANEL_NAME);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddExpenseCommand // instanceof handles nulls
                && toAdd.equals(((AddExpenseCommand) other).toAdd));
    }
}
