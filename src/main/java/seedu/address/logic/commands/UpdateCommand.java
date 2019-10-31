package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.category.Category;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.InTransaction;
import seedu.address.model.transaction.OutTransaction;
import seedu.address.model.util.Date;

/**
 * Updates the details of an existing Transaction in the BankAccount.
 */
public class UpdateCommand extends Command {
    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the person identified "
        + "by the index number used in the displayed person list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) Transaction entries preceded by 't', "
        + "Budget entries preced by 'b' \n"
        + "[" + PREFIX_AMOUNT + "AMOUNT] "
        + "[" + PREFIX_DATE + "DATE] "
        + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
        + "Example: " + COMMAND_WORD + " t1 "
        + PREFIX_AMOUNT + "123 "
        + PREFIX_DATE + "12022019";

    public static final String MESSAGE_NOT_EDITED = "At least one field to update must be provided.";
    public static final String MESSAGE_UPDATE_TRANSACTION_SUCCESS = "Updated: %1$s";
    public static final String MESSAGE_AMOUNT_OVERFLOW = "Transaction amount cannot exceed 1 billion (i.e. 1,000,000)";
    public static final String MESSAGE_AMOUNT_NEGATIVE = "Transaction amount cannot be negative";
    public static final String MESSAGE_AMOUNT_ZERO = "Transaction amount cannot be zero";

    private final String type;
    private final Index targetIndex;
    private final UpdateTransactionDescriptor updateTransactionDescriptor;

    public UpdateCommand(String type, Index targetIndex, UpdateTransactionDescriptor updateTransactionDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(updateTransactionDescriptor);

        this.type = type;
        this.targetIndex = targetIndex;
        this.updateTransactionDescriptor = new UpdateTransactionDescriptor(updateTransactionDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (this.type.equals("t")) {
            FilteredList<BankAccountOperation> lastShownList = model.getFilteredTransactionList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
            }

            BankAccountOperation transactionToReplace = lastShownList.get(targetIndex.getZeroBased());
            BankAccountOperation updatedTransaction = createUpdatedTransaction(transactionToReplace,
                    updateTransactionDescriptor);

            model.setTransaction(transactionToReplace, updatedTransaction);
            model.commitBankAccount();
            return new CommandResult(String.format(MESSAGE_UPDATE_TRANSACTION_SUCCESS, updatedTransaction));
        } else if (this.type.equals("b")) {
            ObservableList<Budget> lastShownList = model.getFilteredBudgetList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX);
            }

            Budget budgetToReplace = lastShownList.get(targetIndex.getZeroBased());
            Budget updatedBudget = createUpdatedBudget(budgetToReplace,
                    updateTransactionDescriptor);

            model.setBudget(budgetToReplace, updatedBudget);
            model.commitBankAccount();
            return new CommandResult(String.format(MESSAGE_UPDATE_TRANSACTION_SUCCESS, updatedBudget));

        } else {
            throw new CommandException("Unknown command error");
        }
    }

    /**
     * Creates and returns a {@code Transaction} with the details of {@code transactionToEdit}
     * edited with {@code editTransactionDescriptor}.
     */
    private static BankAccountOperation createUpdatedTransaction(
        BankAccountOperation transactionToEdit, UpdateTransactionDescriptor updateTransactionDescriptor) {
        assert transactionToEdit != null;

        Description updatedDescription = updateTransactionDescriptor
                .getDescription()
                .orElse(transactionToEdit.getDescription());
        Amount updatedAmount = updateTransactionDescriptor.getAmount().orElse(transactionToEdit.getAmount());
        Date updatedDate = updateTransactionDescriptor.getDate().orElse(transactionToEdit.getDate());
        Set<Category> updatedCategories = updateTransactionDescriptor
            .getCategories().orElse(transactionToEdit.getCategories());

        if (transactionToEdit instanceof InTransaction) {
            return new InTransaction(updatedAmount, updatedDate, updatedDescription, updatedCategories);
        } else {
            /* transactionToEdit instanceof OutTransaction. Add in more conditionals. */
            return new OutTransaction(updatedAmount, updatedDate, updatedDescription, updatedCategories);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateCommand)) {
            return false;
        }

        // state check
        UpdateCommand u = (UpdateCommand) other;
        return targetIndex.equals(u.targetIndex)
                && updateTransactionDescriptor.equals(u.updateTransactionDescriptor);
    }

    /**
     * Creates and returns a {@code Transaction} with the details of {@code transactionToEdit}
     * edited with {@code editTransactionDescriptor}.
     */
    private static Budget createUpdatedBudget(
            Budget budgetToEdit, UpdateTransactionDescriptor updateTransactionDescriptor) {
        assert budgetToEdit != null;

        Amount updatedAmount = updateTransactionDescriptor.getAmount().orElse(budgetToEdit.getBudget());
        Date updatedDate = updateTransactionDescriptor.getDate().orElse(budgetToEdit.getDeadline());
        Set<Category> updatedCategories = updateTransactionDescriptor
                .getCategories().orElse(budgetToEdit.getCategories());

        return new Budget(updatedAmount, updatedDate, updatedCategories);
    }

    /**
     * Stores the details to update the transaction with. Each non-empty field value will replace the
     * corresponding field value of the transaction.
     */
    public static class UpdateTransactionDescriptor {
        // TODO: Add name object
        private Description description;
        private Amount amount;
        private Date date;
        private Set<Category> categories;

        public UpdateTransactionDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code categories} is used internally.
         */
        public UpdateTransactionDescriptor(UpdateTransactionDescriptor toCopy) {
            setDescription(description);
            setAmount(toCopy.amount);
            setDate(toCopy.date);
            setCategories(toCopy.categories);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(amount, date, categories);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        /**
         * Sets {@code categories} to this object's {@code categories}.
         * A defensive copy of {@code categories} is used internally.
         */
        public void setCategories(Set<Category> categories) {
            this.categories = (categories != null) ? new HashSet<>(categories) : null;
        }

        /**
         * Returns an unmodifiable category set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code categories} is null.
         */
        public Optional<Set<Category>> getCategories() {
            return (categories != null) ? Optional.of(Collections.unmodifiableSet(categories)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof UpdateCommand.UpdateTransactionDescriptor)) {
                return false;
            }

            // state check
            UpdateCommand.UpdateTransactionDescriptor e = (UpdateCommand.UpdateTransactionDescriptor) other;

            return getAmount().equals(e.getAmount())
                && getDate().equals(e.getDate())
                && getCategories().equals(e.getCategories());
        }
    }

}
