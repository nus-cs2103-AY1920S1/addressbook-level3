package thrift.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import thrift.commons.core.Messages;
import thrift.commons.core.index.Index;
import thrift.commons.util.CollectionUtil;
import thrift.logic.commands.exceptions.CommandException;
import thrift.logic.parser.CliSyntax;
import thrift.model.Model;
import thrift.model.tag.Tag;
import thrift.model.transaction.Description;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Income;
import thrift.model.transaction.Transaction;
import thrift.model.transaction.TransactionDate;
import thrift.model.transaction.Value;

/**
 * Updates the details of an existing transaction in THRIFT.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the transaction identified "
            + "by the index number used in the displayed transaction list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: " + CliSyntax.PREFIX_INDEX + "INDEX (must be a positive integer) "
            + "[" + CliSyntax.PREFIX_NAME + "NAME DESCRIPTION] "
            + "[" + CliSyntax.PREFIX_COST + "COST] "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " " + CliSyntax.PREFIX_INDEX + "1 "
            + CliSyntax.PREFIX_NAME + "Mee Siam "
            + CliSyntax.PREFIX_COST + "3.00";

    public static final String MESSAGE_UPDATE_TRANSACTION_SUCCESS = "Updated Transaction: %1$s";
    public static final String MESSAGE_NOT_UPDATED = "At least one field to update must be provided.";

    private final Index index;
    private final UpdateTransactionDescriptor updateTransactionDescriptor;

    /**
     * @param index of the transaction in the filtered transaction list to update
     * @param updateTransactionDescriptor details to update the transaction with
     */
    public UpdateCommand(Index index, UpdateTransactionDescriptor updateTransactionDescriptor) {
        requireNonNull(index);
        requireNonNull(updateTransactionDescriptor);

        this.index = index;
        this.updateTransactionDescriptor = new UpdateTransactionDescriptor(updateTransactionDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Transaction> lastShownList = model.getFilteredTransactionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction transactionToUpdate = lastShownList.get(index.getZeroBased());
        Transaction updatedTransaction = createUpdatedTransaction(transactionToUpdate, updateTransactionDescriptor);

        model.setTransaction(transactionToUpdate, updatedTransaction);
        model.updateFilteredTransactionList(Model.PREDICATE_SHOW_ALL_TRANSACTIONS);
        return new CommandResult(String.format(MESSAGE_UPDATE_TRANSACTION_SUCCESS, updatedTransaction));
    }

    /**
     * Creates and returns a {@code Transaction} with the details of {@code transactionToUpdate}
     * updated with {@code updateTransactionDescriptor}.
     */
    private static Transaction createUpdatedTransaction(Transaction transactionToUpdate,
                                                        UpdateTransactionDescriptor updateTransactionDescriptor) {
        assert transactionToUpdate != null;

        Description updatedDescription = updateTransactionDescriptor.getDescription()
                .orElse(transactionToUpdate.getDescription());
        Value updatedValue = updateTransactionDescriptor.getValue().orElse(transactionToUpdate.getValue());
        TransactionDate updatedDate = updateTransactionDescriptor.getDate().orElse(transactionToUpdate.getDate());
        Set<Tag> updatedTags = updateTransactionDescriptor.getTags().orElse(transactionToUpdate.getTags());

        if (transactionToUpdate instanceof Expense) {
            return new Expense(updatedDescription, updatedValue, updatedDate, updatedTags);
        } else {
            return new Income(updatedDescription, updatedValue, updatedDate, updatedTags);
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
        UpdateCommand e = (UpdateCommand) other;
        return index.equals(e.index)
                && updateTransactionDescriptor.equals(e.updateTransactionDescriptor);
    }

    /**
     * Stores the details to update the transaction with. Each non-empty field value will replace the
     * corresponding field value of the transaction.
     */
    public static class UpdateTransactionDescriptor {
        private Description description;
        private Value value;
        private TransactionDate date;
        private Set<Tag> tags;

        public UpdateTransactionDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public UpdateTransactionDescriptor(UpdateTransactionDescriptor toCopy) {
            setDescription(toCopy.description);
            setValue(toCopy.value);
            setDate(toCopy.date);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is updated.
         */
        public boolean isAnyFieldUpdated() {
            return CollectionUtil.isAnyNonNull(description, value, date, tags);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setValue(Value value) {
            this.value = value;
        }

        public Optional<Value> getValue() {
            return Optional.ofNullable(value);
        }

        public void setDate(TransactionDate date) {
            this.date = date;
        }

        public Optional<TransactionDate> getDate() {
            return Optional.ofNullable(date);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof UpdateTransactionDescriptor)) {
                return false;
            }

            // state check
            UpdateTransactionDescriptor e = (UpdateTransactionDescriptor) other;

            return getDescription().equals(e.getDescription())
                    && getValue().equals(e.getValue())
                    && getDate().equals(e.getDate())
                    && getTags().equals(e.getTags());
        }
    }
}
