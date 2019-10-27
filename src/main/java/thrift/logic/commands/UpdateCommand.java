package thrift.logic.commands;

import static java.util.Objects.requireNonNull;
import static thrift.commons.util.CollectionUtil.requireAllNonNull;

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
import thrift.model.transaction.Remark;
import thrift.model.transaction.Transaction;
import thrift.model.transaction.TransactionDate;
import thrift.model.transaction.Value;
import thrift.ui.TransactionListPanel;

/**
 * Updates the details of an existing transaction in THRIFT.
 */
public class UpdateCommand extends ScrollingCommand implements Undoable {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the transaction identified "
            + "by the index number used in the displayed transaction list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: " + CliSyntax.PREFIX_INDEX + "INDEX (must be a positive integer) "
            + "[" + CliSyntax.PREFIX_NAME + "NAME DESCRIPTION] "
            + "[" + CliSyntax.PREFIX_VALUE + "VALUE] "
            + "[" + CliSyntax.PREFIX_REMARK + "REMARK] "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " " + CliSyntax.PREFIX_INDEX + "1 "
            + CliSyntax.PREFIX_NAME + "Mee Siam "
            + CliSyntax.PREFIX_VALUE + "3.00";

    public static final String HELP_MESSAGE = COMMAND_WORD
            + ": Updates the details of the transaction identified by the index number used in the displayed "
            + "transaction list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Format: " + COMMAND_WORD + " " + CliSyntax.PREFIX_INDEX + "INDEX (must be a positive integer) "
            + "[" + CliSyntax.PREFIX_NAME + "NAME DESCRIPTION] "
            + "[" + CliSyntax.PREFIX_VALUE + "VALUE] "
            + "[" + CliSyntax.PREFIX_REMARK + "REMARK] "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]...\n"
            + "Possible usages of " + COMMAND_WORD + ": \n"
            + "To update the name of the transaction at index 1 in the displayed transaction list: "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_INDEX + "1 " + CliSyntax.PREFIX_NAME + "Chicken rice\n"
            + "To update the value of the transaction at index 1 in the displayed transaction list: "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_INDEX + "1 " + CliSyntax.PREFIX_VALUE + "3\n"
            + "To update the remark of the transaction at index 1 in the displayed transaction list: "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_INDEX + "1 " + CliSyntax.PREFIX_REMARK + "Best food ever\n"
            + "To overwrite the existing tag(s) of the transaction at index 1 in the displayed transaction list: "
            + COMMAND_WORD + " " + CliSyntax.PREFIX_INDEX + "1 " + CliSyntax.PREFIX_TAG + "food";

    public static final String MESSAGE_UPDATE_TRANSACTION_SUCCESS = "Updated Transaction: %1$s";
    public static final String MESSAGE_ORIGINAL_TRANSACTION = "\n\nOriginal: %1$s";
    public static final String MESSAGE_NOT_UPDATED = "At least one field to update must be provided.";
    public static final String UNDO_SUCCESS = "Updated Transaction: %1$s\nOriginal: %2$s";
    public static final String REDO_SUCCESS = "Updated Transaction: %1$s\nOriginal: %2$s";

    private final Index index;
    private final UpdateTransactionDescriptor updateTransactionDescriptor;
    private Index actualIndex;
    private Transaction transactionToUpdate;
    private Transaction updatedTransaction;

    /**
     * @param index of the transaction in the filtered transaction list to update
     * @param updateTransactionDescriptor details to update the transaction with
     */
    public UpdateCommand(Index index, UpdateTransactionDescriptor updateTransactionDescriptor) {
        requireNonNull(index);
        requireNonNull(updateTransactionDescriptor);

        this.index = index;
        this.updateTransactionDescriptor = new UpdateTransactionDescriptor(updateTransactionDescriptor);
        this.actualIndex = null;
        this.transactionToUpdate = null;
        this.updatedTransaction = null;
    }

    /**
     * Updates the details of a THRIFT Transaction and scrolls to the updated Transaction in the displayed
     * TransactionListView UI.
     *
     * @param model {@code Model} which UpdateCommand should operate on.
     * @param transactionListPanel {@code TransactionListPanel} which contains the TransactionListView UI.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult execute(Model model, TransactionListPanel transactionListPanel) throws CommandException {
        requireNonNull(model);
        List<Transaction> lastShownList = model.getFilteredTransactionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        transactionToUpdate = lastShownList.get(index.getZeroBased());
        String originalTransactionNotification = String.format(MESSAGE_ORIGINAL_TRANSACTION, transactionToUpdate);
        updatedTransaction = createUpdatedTransaction(transactionToUpdate, updateTransactionDescriptor);
        String updatedTransactionNotification = String.format(MESSAGE_UPDATE_TRANSACTION_SUCCESS, updatedTransaction);

        actualIndex = model.getIndexInFullTransactionList(transactionToUpdate).get();
        model.setTransactionWithIndex(actualIndex, updatedTransaction);

        // Use null comparison instead of requireNonNull(transactionListPanel) as current JUnit tests are unable to
        // handle JavaFX initialization
        if (model.isInView(updatedTransaction) && transactionListPanel != null) {
            transactionListPanel.getTransactionListView().scrollTo(index.getZeroBased());
        }

        return new CommandResult(updatedTransactionNotification + originalTransactionNotification);
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
        Remark updatedRemark = updateTransactionDescriptor.getRemark().orElse(transactionToUpdate.getRemark());
        TransactionDate updatedDate = updateTransactionDescriptor.getDate().orElse(transactionToUpdate.getDate());
        Set<Tag> updatedTags = updateTransactionDescriptor.getTags().orElse(transactionToUpdate.getTags());

        if (transactionToUpdate instanceof Expense) {
            return new Expense(updatedDescription, updatedValue, updatedRemark, updatedDate, updatedTags);
        } else {
            return new Income(updatedDescription, updatedValue, updatedRemark, updatedDate, updatedTags);
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

    @Override
    public String undo(Model model) {
        requireAllNonNull(model, transactionToUpdate, updatedTransaction, actualIndex);
        model.setTransactionWithIndex(actualIndex, transactionToUpdate);
        return String.format(UNDO_SUCCESS, transactionToUpdate, updatedTransaction);
    }

    @Override
    public String redo(Model model) {
        requireAllNonNull(model, transactionToUpdate, updatedTransaction, actualIndex);
        model.setTransactionWithIndex(actualIndex, updatedTransaction);
        return String.format(REDO_SUCCESS, updatedTransaction, transactionToUpdate);
    }

    /**
     * Stores the details to update the transaction with. Each non-empty field value will replace the
     * corresponding field value of the transaction.
     */
    public static class UpdateTransactionDescriptor {
        private Description description;
        private Value value;
        private Remark remark;
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
            setRemark(toCopy.remark);
            setDate(toCopy.date);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is updated.
         */
        public boolean isAnyFieldUpdated() {
            return CollectionUtil.isAnyNonNull(description, value, remark, date, tags);
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

        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
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
                    && getRemark().equals(e.getRemark())
                    && getDate().equals(e.getDate())
                    && getTags().equals(e.getTags());
        }
    }
}
