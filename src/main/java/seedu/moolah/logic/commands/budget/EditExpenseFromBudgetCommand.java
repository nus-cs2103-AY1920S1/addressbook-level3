package seedu.moolah.logic.commands.budget;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_TIMESTAMP;
import static seedu.moolah.model.Model.PREDICATE_SHOW_ALL_EXPENSES;

import java.util.List;

import seedu.moolah.commons.core.Messages;
import seedu.moolah.commons.core.index.Index;
import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.GenericCommandWord;
import seedu.moolah.logic.commands.UndoableCommand;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.logic.commands.expense.EditExpenseCommand.EditExpenseDescriptor;
import seedu.moolah.model.Model;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.expense.Price;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.ui.budget.BudgetPanel;

/**
 * Edits the details of an expense identified using its displayed index from primary budget panel.
 */
public class EditExpenseFromBudgetCommand extends UndoableCommand {
    public static final String COMMAND_WORD = GenericCommandWord.EDIT + CommandGroup.PRIMARY_BUDGET;
    public static final String COMMAND_DESCRIPTION = "Edit expense with index %1$d from budget";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the expense identified "
            + "by the index number used in the displayed expense list of this budget. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_PRICE + "PRICE] "
            + "[" + PREFIX_CATEGORY + "CATEGORY]"
            + "[" + PREFIX_TIMESTAMP + "TIMESTAMP]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PRICE + "3512.123 ";

    public static final String MESSAGE_EDIT_EXPENSE_SUCCESS = "Edited Expense: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EXPENSE = "This expense already exists in MooLah!";

    private final Index index;
    private final EditExpenseDescriptor editExpenseDescriptor;

    /**
     * @param index of the expense in the filtered expense list to edit
     * @param editExpenseDescriptor details to edit the expense with
     */
    public EditExpenseFromBudgetCommand(Index index, EditExpenseDescriptor editExpenseDescriptor) {
        requireNonNull(index);
        requireNonNull(editExpenseDescriptor);

        this.index = index;
        this.editExpenseDescriptor = new EditExpenseDescriptor(editExpenseDescriptor);
    }

    @Override
    public String getDescription() {
        return String.format(COMMAND_DESCRIPTION, index.getOneBased());
    }

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);

        List<Expense> lastShownList = model.getPrimaryBudget().getCurrentPeriodExpenses();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
        }

        Expense expenseToEdit = lastShownList.get(index.getZeroBased());
        Expense editedExpense = createEditedExpense(expenseToEdit, editExpenseDescriptor);
        if (!expenseToEdit.isSameExpense(editedExpense) && model.hasExpense(editedExpense)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXPENSE);
        }
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);

        List<Expense> lastShownList = model.getPrimaryBudget().getCurrentPeriodExpenses();
        Expense expenseToEdit = lastShownList.get(index.getZeroBased());
        Expense editedExpense = createEditedExpense(expenseToEdit, editExpenseDescriptor);

        model.setExpense(expenseToEdit, editedExpense);
        Budget primaryBudget = model.getPrimaryBudget();
        Budget primaryBudgetCopy = primaryBudget.deepCopy();
        primaryBudgetCopy.setExpense(expenseToEdit, editedExpense);
        model.setBudget(primaryBudget, primaryBudgetCopy);
        model.updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);
        return new CommandResult(String.format(MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense),
                BudgetPanel.PANEL_NAME);
    }

    /**
     * Creates and returns a {@code Expense} with the details of {@code expenseToEdit}
     * edited with {@code editExpenseDescriptor}.
     */
    private static Expense createEditedExpense(Expense expenseToEdit,
                                               EditExpenseDescriptor editExpenseDescriptor) {
        assert expenseToEdit != null;

        Description updatedDescription = editExpenseDescriptor.getDescription().orElse(expenseToEdit.getDescription());
        Price updatedPrice = editExpenseDescriptor.getPrice().orElse(expenseToEdit.getPrice());
        Category updatedCategory = editExpenseDescriptor.getCategory().orElse(expenseToEdit.getCategory());
        Timestamp updatedTimestamp = editExpenseDescriptor.getTimestamp().orElse(expenseToEdit.getTimestamp());

        return new Expense(updatedDescription, updatedPrice, updatedCategory,
                updatedTimestamp, expenseToEdit.getBudgetName(), expenseToEdit.getUniqueIdentifier());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditExpenseFromBudgetCommand)) {
            return false;
        }

        // state check
        EditExpenseFromBudgetCommand e = (EditExpenseFromBudgetCommand) other;
        return index.equals(e.index)
                && editExpenseDescriptor.equals(e.editExpenseDescriptor);
    }
}
