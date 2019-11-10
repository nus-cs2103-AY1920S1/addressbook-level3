package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUDGETS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ViewState;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Amount;
import seedu.address.model.expense.Currency;
import seedu.address.model.expense.Name;

/**
 * Edits the details of an existing budget in the budget list.
 */
public class EditBudgetCommand extends Command {

    public static final String COMMAND_WORD = "editBudget";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the budget identified "
        + "by the index number used in the displayed budget list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_NAME + "NAME] "
        + "[" + PREFIX_AMOUNT + "AMOUNT] "
        + "[" + PREFIX_CURRENCY + "CURRENCY]...\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_NAME + "Japan Travel "
        + PREFIX_AMOUNT + "$5000 ";

    public static final String MESSAGE_EDIT_BUDGET_SUCCESS = "Edited Budget: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_BUDGET = "This budget already exists in the budget list.";
    public static final String MESSAGE_EDIT_BUDGET_ERROR = "You have to be viewing the budget list "
        + "first before you can edit a budget";

    private final Index index;
    private final EditBudgetDescriptor editBudgetDescriptor;

    /**
     * @param index                of the budget in the filtered budget list to edit
     * @param editBudgetDescriptor details to edit the budget with
     */
    public EditBudgetCommand(Index index, EditBudgetDescriptor editBudgetDescriptor) {
        requireNonNull(index);
        requireNonNull(editBudgetDescriptor);

        this.index = index;
        this.editBudgetDescriptor = new EditBudgetDescriptor(editBudgetDescriptor);
    }

    /**
     * Creates and returns a {@code Budget} with the details of {@code budgetToEdit}
     * edited with {@code editBudgetDescriptor}.
     */
    private static Budget createEditedBudget(Budget budgetToEdit, EditBudgetDescriptor editBudgetDescriptor) {
        assert budgetToEdit != null;

        Name updatedName = editBudgetDescriptor.getName().orElse(budgetToEdit.getName());
        Amount updatedAmount = editBudgetDescriptor.getAmount().orElse(budgetToEdit.getAmount());
        Currency updatedCurrency = editBudgetDescriptor.getCurrency().orElse(budgetToEdit.getCurrency());

        Budget editedBudget = new Budget(updatedName, updatedAmount, updatedAmount, updatedCurrency,
            budgetToEdit.getStartDate(), budgetToEdit.getEndDate(), budgetToEdit.getExpenseList());

        return editedBudget;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Budget> lastShownList = model.getFilteredBudgetList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX);
        }

        Budget budgetToEdit = lastShownList.get(index.getZeroBased());
        Budget editedBudget = createEditedBudget(budgetToEdit, editBudgetDescriptor);

        if (!budgetToEdit.isSameBudget(editedBudget) && model.hasBudget(editedBudget)) {
            throw new CommandException(MESSAGE_DUPLICATE_BUDGET);
        }

        model.setBudget(budgetToEdit, editedBudget);
        model.updateFilteredBudgetList(PREDICATE_SHOW_ALL_BUDGETS);
        model.setViewState(ViewState.BUDGETLIST);
        return new CommandResult(null, model.getFilteredBudgetList(), null,
            String.format(MESSAGE_EDIT_BUDGET_SUCCESS, editedBudget));
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
     * Stores the details to edit the budget with. Each non-empty field value will replace the
     * corresponding field value of the budget.
     */
    public static class EditBudgetDescriptor {

        private Name name;
        private Amount amount;
        private Currency currency;

        public EditBudgetDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditBudgetDescriptor(EditBudgetDescriptor toCopy) {
            setName(toCopy.name);
            setAmount(toCopy.amount);
            setCurrency(toCopy.currency);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, amount, currency);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Currency> getCurrency() {
            return Optional.ofNullable(currency);
        }

        public void setCurrency(Currency currency) {
            this.currency = currency;
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

            return getName().equals(e.getName())
                && getAmount().equals(e.getAmount())
                && getCurrency().equals(e.getCurrency());
        }
    }
}
