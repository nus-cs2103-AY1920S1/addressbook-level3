package seedu.moolah.logic.commands.expense;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_MENU;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.GenericCommandWord;
import seedu.moolah.logic.commands.UndoableCommand;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.model.expense.util.UniqueIdentifierGenerator;
import seedu.moolah.model.menu.MenuItem;
import seedu.moolah.ui.budget.BudgetPanel;

/**
 * Adds a menu item expense to MooLah.
 */
public class AddMenuExpenseCommand extends UndoableCommand {

    public static final String COMMAND_WORD = GenericCommandWord.ADD + "menu" + CommandGroup.EXPENSE;
    public static final String COMMAND_DESCRIPTION = "Add expense %1$s (%2$s)";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a menu expense to MooLah and the primary budget. \n"
            + "Parameters: "
            + PREFIX_MENU + "MENU_ITEM "
            + "[" + PREFIX_TIMESTAMP + "TIMESTAMP]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MENU + "Deck Chicken Rice "
            + PREFIX_TIMESTAMP + "10-10";
    public static final String MESSAGE_SUCCESS = "New expense added:\n %1$s";
    public static final String MESSAGE_DUPLICATE_EXPENSE = "This expense already exists in the MooLah";

    private Expense expense;

    public AddMenuExpenseCommand(MenuItem menuItem) {
        requireNonNull(menuItem);
        expense = new Expense(menuItem, UniqueIdentifierGenerator.generateRandomUniqueIdentifier());
    }

    public AddMenuExpenseCommand(MenuItem menuItem, Timestamp timestamp) {
        requireAllNonNull(menuItem, timestamp);
        expense = new Expense(menuItem, timestamp, UniqueIdentifierGenerator.generateRandomUniqueIdentifier());
    }

    public AddMenuExpenseCommand(Expense expense) {
        requireNonNull(expense);
        this.expense = expense;
    }

    @Override
    public String getDescription() {
        return String.format(COMMAND_DESCRIPTION, expense.getDescription(), expense.getPrice());
    }

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasExpense(expense)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXPENSE);
        }
    }

    @Override
    protected CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.addExpense(expense);
        return new CommandResult(String.format(MESSAGE_SUCCESS, expense), BudgetPanel.PANEL_NAME);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof AddMenuExpenseCommand)) {
            return false;
        }

        AddMenuExpenseCommand other = (AddMenuExpenseCommand) obj;

        return expense.equals(other.expense);
    }
}
