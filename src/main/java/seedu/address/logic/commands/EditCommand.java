package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ViewState;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Amount;
import seedu.address.model.expense.Currency;
import seedu.address.model.expense.Date;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Name;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing expense in the expense list.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the expense identified "
        + "by the index number used in the displayed expense list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_NAME + "NAME] "
        + "[" + PREFIX_AMOUNT + "AMOUNT] "
        + "[" + PREFIX_CURRENCY + "CURRENCY] "
        + "[" + PREFIX_DATE + "DATE] "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_AMOUNT + "23.50 "
        + PREFIX_DATE + "1245";

    public static final String MESSAGE_EDIT_EXPENSE_SUCCESS = "Edited Expense: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EXPENSE = "This expense already exists in the expense list.";
    public static final String MESSAGE_EDIT_ERROR = "An error occurred while "
        + "trying to edit the expense";
    public static final String MESSAGE_EDIT_WHEN_NOT_VIEWING_EXPENSELIST_ERROR = "You have to be viewing an "
        + "expense list to edit an expense";

    private final Index index;
    private final EditExpenseDescriptor editExpenseDescriptor;

    /**
     * @param index                 of the expense in the filtered expense list to edit
     * @param editExpenseDescriptor details to edit the expense with
     */
    public EditCommand(Index index, EditExpenseDescriptor editExpenseDescriptor) {
        requireNonNull(index);
        requireNonNull(editExpenseDescriptor);

        this.index = index;
        this.editExpenseDescriptor = new EditExpenseDescriptor(editExpenseDescriptor);
    }

    /**
     * Creates and returns a {@code Expense} with the details of {@code expenseToEdit}
     * edited with {@code editExpenseDescriptor}.
     */
    private static Expense createEditedExpense(Expense expenseToEdit, EditExpenseDescriptor editExpenseDescriptor) {
        assert expenseToEdit != null;

        Name updatedName = editExpenseDescriptor.getName().orElse(expenseToEdit.getName());
        Amount updatedAmount = editExpenseDescriptor.getAmount().orElse(expenseToEdit.getAmount());
        Currency updatedCurrency = editExpenseDescriptor.getCurrency().orElse(expenseToEdit.getCurrency());
        Date updatedDate = editExpenseDescriptor.getDate().orElse(expenseToEdit.getDate());
        Tag updatedTag = editExpenseDescriptor.getTag().orElse(expenseToEdit.getTag());

        return new Expense(updatedName, updatedAmount, updatedCurrency, updatedDate, updatedTag);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ViewState viewState = model.getViewState();
        List<Expense> lastShownList;

        if (viewState.equals(ViewState.DEFAULT_EXPENSELIST)) {
            lastShownList = model.getFilteredExpenseList();
        } else if (viewState.equals(ViewState.EXPENSELIST_IN_BUDGET)) {
            Budget viewingBudget = model.getLastViewedBudget();
            lastShownList = viewingBudget.getObservableExpenseList();
        } else {
            throw new CommandException(MESSAGE_EDIT_WHEN_NOT_VIEWING_EXPENSELIST_ERROR);
        }

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
        }

        Expense expenseToEdit = lastShownList.get(index.getZeroBased());
        Expense editedExpense = createEditedExpense(expenseToEdit, editExpenseDescriptor);

        if (!expenseToEdit.isSameExpense(editedExpense) && model.hasExpense(editedExpense)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXPENSE);
        }

        Optional<Budget> b1 = model.getBudgetExpenseFallsInto(expenseToEdit);
        Optional<Budget> b2 = model.getBudgetExpenseFallsInto(editedExpense);

        if (b1.isPresent() && b2.isPresent() && b1.get().equals(b2.get())) {
            // both expenses fall in same budget
            b1.get().setExpenseInBudget(expenseToEdit, editedExpense);
        } else if (b1.isEmpty() && b2.isEmpty()) {
            // both expenses do not fall in any budget
            model.setExpense(expenseToEdit, editedExpense);
        } else if (b1.isPresent() && b2.isEmpty()) {
            // toEdit falls in budget, edited doesn't
            b1.get().deleteExpenseInBudget(expenseToEdit);
            model.addExpense(editedExpense);
        } else if (b1.isEmpty() && b2.isPresent()) {
            // toEdit doesn't fall in budget, edited does
            model.deleteExpense(expenseToEdit);
            b2.get().addExpenseIntoBudget(editedExpense);
        } else if (b1.isPresent() && b2.isPresent() && !b1.get().equals(b2.get())) {
            // both expenses in different budget
            b1.get().deleteExpenseInBudget(expenseToEdit);
            b2.get().addExpenseIntoBudget(editedExpense);
        } else {
            throw new CommandException(MESSAGE_EDIT_ERROR);
        }

        if (viewState.equals(ViewState.DEFAULT_EXPENSELIST)) {
            return new CommandResult(model.getFilteredExpenseList(), null,
                null, String.format(MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense));
        } else if (viewState.equals(ViewState.EXPENSELIST_IN_BUDGET)) {
            return new CommandResult(model.getExpenseListFromBudget(b1.get()), null, null,
                String.format(MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense));
        } else {
            throw new CommandException(MESSAGE_EDIT_WHEN_NOT_VIEWING_EXPENSELIST_ERROR);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
            && editExpenseDescriptor.equals(e.editExpenseDescriptor);
    }

    /**
     * Stores the details to edit the expense with. Each non-empty field value will replace the
     * corresponding field value of the expense.
     */
    public static class EditExpenseDescriptor {

        private Name name;
        private Amount amount;
        private Currency currency;
        private Date date;
        private Tag tag;

        public EditExpenseDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditExpenseDescriptor(EditExpenseDescriptor toCopy) {
            setName(toCopy.name);
            setAmount(toCopy.amount);
            setCurrency(toCopy.currency);
            setDate(toCopy.date);
            setTag(toCopy.tag);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, amount, currency, date, tag);
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

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Tag> getTag() {
            return Optional.ofNullable(tag);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTag(Tag tag) {
            this.tag = tag;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditExpenseDescriptor)) {
                return false;
            }

            // state check
            EditExpenseDescriptor e = (EditExpenseDescriptor) other;

            return getName().equals(e.getName())
                && getAmount().equals(e.getAmount())
                && getCurrency().equals(e.getCurrency())
                && getDate().equals(e.getDate())
                && getTag().equals(e.getTag());
        }
    }
}
