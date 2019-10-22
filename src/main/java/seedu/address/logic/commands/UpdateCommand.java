package seedu.address.logic.commands;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.util.Date;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class UpdateCommand extends Command {
    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a transaction to the bank account.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_DATE + "DATE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "chicken "
            + PREFIX_AMOUNT + "5 "
            + PREFIX_DATE + "10102019 "
            + PREFIX_TAG + "lunch "
            + PREFIX_TAG + "foodAndBeverage";

    private final Index targetIndex;

    public static final String MESSAGE_DELETE_TRANSACTION_SUCCESS = "Updated Transaction: %1$s";

    public UpdateCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        FilteredList<BankAccountOperation> lastShownList = model.getFilteredTransactionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        BankAccountOperation transactionToReplace = lastShownList.get(targetIndex.getZeroBased());
        model.deleteTransaction(transactionToReplace);
        model.commitBankAccount();
        return new CommandResult(String.format(MESSAGE_DELETE_TRANSACTION_SUCCESS, transactionToReplace));
    }

    public static class UpdateTransactionDescriptor {
        private Amount amount;
        private Date date;
        private Set<Tag> tags;

        public UpdateTransactionDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public UpdateTransactionDescriptor(UpdateCommand.UpdateTransactionDescriptor toCopy) {
            setAmount(toCopy.amount);
            setDate(toCopy.date);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(amount, date, tags);
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
            if (!(other instanceof UpdateCommand.UpdateTransactionDescriptor)) {
                return false;
            }

            // state check
            UpdateCommand.UpdateTransactionDescriptor e = (UpdateCommand.UpdateTransactionDescriptor) other;

            return getAmount().equals(e.getAmount())
                    && getDate().equals(e.getDate())
                    && getTags().equals(e.getTags());
        }
    }

}
