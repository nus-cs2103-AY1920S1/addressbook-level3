package seedu.address.logic.commands.budget;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUDGETS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandGroup;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GenericCommandWord;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.BudgetPeriod;
import seedu.address.model.expense.Description;
import seedu.address.model.expense.Price;
import seedu.address.model.expense.Timestamp;
import seedu.address.ui.budget.BudgetListPanel;

/**
 * Edits the details of an existing budget in the MooLah.
 */
public class EditBudgetCommand extends UndoableCommand {
    public static final String COMMAND_WORD = GenericCommandWord.EDIT + CommandGroup.BUDGET;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the budget identified "
            + "by the index number used in the displayed expense list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_PRICE + "AMOUNT] "
            + "[" + PREFIX_START_DATE + "START DATE]"
            + "[" + PREFIX_PERIOD + "PERIOD]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PRICE + "400 ";

    public static final String MESSAGE_EDIT_BUDGET_SUCCESS = "Edited Budget: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DEFAULT_BUDGET_UNEDITABLE = "The default budget cannot be edited.";
    public static final String MESSAGE_DUPLICATE_BUDGET = "This budget already exists in the MooLah.";

    private final Index index;
    private final EditBudgetDescriptor editBudgetDescriptor;

    /**
     * @param index of the budget in the filtered budget list to edit
     * @param editBudgetDescriptor details to edit the budget with
     */
    public EditBudgetCommand(Index index, EditBudgetDescriptor editBudgetDescriptor) {
        requireNonNull(index);
        requireNonNull(editBudgetDescriptor);

        this.index = index;
        this.editBudgetDescriptor = new EditBudgetDescriptor(editBudgetDescriptor);
    }

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);

        List<Budget> lastShownList = model.getFilteredBudgetList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
        }

        Budget budgetToEdit = lastShownList.get(index.getZeroBased());
        if (budgetToEdit.isSameBudget(Budget.createDefaultBudget())) {
            throw new CommandException(MESSAGE_DEFAULT_BUDGET_UNEDITABLE);
        }
        Budget editedBudget = createEditedBudget(budgetToEdit, editBudgetDescriptor);
        if (!budgetToEdit.isSameBudget(editedBudget) && model.hasBudget(editedBudget)) {
            throw new CommandException(MESSAGE_DUPLICATE_BUDGET);
        }
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);

        List<Budget> lastShownList = model.getFilteredBudgetList();
        Budget budgetToEdit = lastShownList.get(index.getZeroBased());
        Budget editedBudget = createEditedBudget(budgetToEdit, editBudgetDescriptor);

        model.setBudget(budgetToEdit, editedBudget);
        model.updateFilteredBudgetList(PREDICATE_SHOW_ALL_BUDGETS);
        return new CommandResult(String.format(MESSAGE_EDIT_BUDGET_SUCCESS, editedBudget),
                BudgetListPanel.PANEL_NAME);
    }

    /**
     * Creates and returns a {@code Expense} with the details of {@code expenseToEdit}
     * edited with {@code editExpenseDescriptor}.
     */
    private static Budget createEditedBudget(Budget budgetToEdit, EditBudgetDescriptor editBudgetDescriptor) {
        assert budgetToEdit != null;

        Description updatedDescription = editBudgetDescriptor.getDescription().orElse(budgetToEdit.getDescription());
        Price updatedAmount = editBudgetDescriptor.getAmount().orElse(budgetToEdit.getAmount());
        Timestamp updatedStartDate = editBudgetDescriptor.getStartDate().orElse(budgetToEdit.getStartDate());
        BudgetPeriod updatedPeriod = editBudgetDescriptor.getPeriod().orElse(budgetToEdit.getPeriod());

        Budget newBudget = new Budget(updatedDescription, updatedAmount, updatedStartDate, updatedPeriod,
                budgetToEdit.getExpenses());
        newBudget.setIsPrimary(budgetToEdit.isPrimary());
        newBudget.normalize(Timestamp.getCurrentTimestamp());
        newBudget.updateProportionUsed();

        return newBudget;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditBudgetCommand)) {
            return false;
        }

        // state check
        EditBudgetCommand e = (EditBudgetCommand) other;
        return index.equals(e.index)
                && editBudgetDescriptor.equals(e.editBudgetDescriptor);
    }

    /**
     * Stores the details to edit the expense with. Each non-empty field value will replace the
     * corresponding field value of the expense.
     */
    public static class EditBudgetDescriptor {
        private Description description;
        private Price amount;
        private Timestamp startDate;
        private BudgetPeriod period;

        public EditBudgetDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditBudgetDescriptor(EditBudgetDescriptor toCopy) {
            setDescription(toCopy.description);
            setAmount(toCopy.amount);
            setStartDate(toCopy.startDate);
            setPeriod(toCopy.period);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, amount, startDate, period);
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Price> getAmount() {
            return Optional.ofNullable(amount);
        }


        public void setAmount(Price amount) {
            this.amount = amount;
        }

        public Optional<Timestamp> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        public void setStartDate(Timestamp startDate) {
            this.startDate = startDate;
        }

        public Optional<BudgetPeriod> getPeriod() {
            return Optional.ofNullable(period);
        }

        public void setPeriod(BudgetPeriod period) {
            this.period = period;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditBudgetDescriptor)) {
                return false;
            }

            // state check
            EditBudgetDescriptor e = (EditBudgetDescriptor) other;

            return description.equals(e.description)
                    && amount.equals(e.amount)
                    && startDate.equals(e.startDate)
                    && period.equals(e.period);
        }
    }
}
