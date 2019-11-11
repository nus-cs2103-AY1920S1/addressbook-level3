package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.category.Category;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.InTransaction;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.model.transaction.OutTransaction;
import seedu.address.model.transaction.ReceiveMoney;
import seedu.address.model.transaction.Split;
import seedu.address.model.util.Date;
import seedu.address.ui.tab.Tab;

/**
 * Updates the details of an existing Transaction in the BankAccount.
 */
public class UpdateCommand extends Command {
    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the person identified "
        + "by the index number used in the displayed person list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) Transaction entries preceded by 't', \n"
        + "Budget entries preceded by 'b' \n"
        + "Ledger entries preceded by 'l' \n"
        + "[" + PREFIX_AMOUNT + "AMOUNT] "
        + "[" + PREFIX_DATE + "DATE] "
        + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
        + "Example: " + COMMAND_WORD + " t1 "
        + PREFIX_AMOUNT + "123 "
        + PREFIX_DATE + "12022019";

    public static final String MESSAGE_NOT_EDITED = "At least one field to update must be provided.";
    public static final String MESSAGE_UPDATE_ENTRY_SUCCESS = "Updated: %1$s";

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

        if (this.type.equals(Model.TRANSACTION_TYPE)) {
            ObservableList<BankAccountOperation> lastShownList = model.getFilteredTransactionList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
            }

            BankAccountOperation transactionToReplace = lastShownList.get(targetIndex.getZeroBased());
            BankAccountOperation updatedTransaction = createUpdatedOperation(transactionToReplace,
                updateTransactionDescriptor);

            model.set(transactionToReplace, updatedTransaction);
            model.updateProjectionsAfterUpdate(transactionToReplace, updatedTransaction);

            model.commitUserState();
            return new CommandResult(String.format(MESSAGE_UPDATE_ENTRY_SUCCESS, updatedTransaction),
                false, false, Tab.TRANSACTION);
        } else if (this.type.equals(Model.BUDGET_TYPE)) {
            ObservableList<Budget> lastShownList = model.getFilteredBudgetList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX);
            }

            Budget budgetToReplace = lastShownList.get(targetIndex.getZeroBased());
            Budget updatedBudget = createUpdatedOperation(budgetToReplace,
                    updateTransactionDescriptor);
            model.set(budgetToReplace, updatedBudget);
            model.updateProjectionsAfterDelete(budgetToReplace);
            model.updateProjectionsAfterAdd(updatedBudget);
            model.commitUserState();
            return new CommandResult(String.format(MESSAGE_UPDATE_ENTRY_SUCCESS, updatedBudget),
                    false, false, Tab.BUDGET);
        } else {
            throw new CommandException("Unknown command error");
        }
    }

    /**
     * Creates and returns a {@code Transaction} with the details of {@code transactionToEdit}
     * edited with {@code editTransactionDescriptor}.
     */
    private static BankAccountOperation createUpdatedOperation(
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

    /**
     * Creates and returns a {@code Budget} with the details of {@code budgetToEdit}
     * edited with {@code editTransactionDescriptor}.
     */
    private static Budget createUpdatedOperation(Budget budgetToEdit,
                                                 UpdateTransactionDescriptor updateTransactionDescriptor) {
        assert budgetToEdit != null;

        Amount initialAmount = budgetToEdit.getBudget();
        Amount updatedAmount = updateTransactionDescriptor.getAmount().orElse(budgetToEdit.getInitialBudget());
        Date updatedDate = updateTransactionDescriptor.getDate().orElse(budgetToEdit.getDeadline());
        Set<Category> updatedCategories = updateTransactionDescriptor
                .getCategories().orElse(budgetToEdit.getCategories());

        return new Budget(updatedAmount, initialAmount, updatedDate, updatedCategories);
    }

    /**
     * [WIP DO NOT USE]
     * Creates and returns a new {@code LedgerOperation} with the details of {@code ToEdit}
     * based on fields from {@code descriptor}.
     */
    private static LedgerOperation createUpdatedOperation(LedgerOperation toEdit,
                                                          UpdateTransactionDescriptor descriptor) {
        assert toEdit != null : "LedgerOperation is null";
        Amount newAmount = descriptor.getAmount().orElse(toEdit.getAmount());
        Date newDate = descriptor.getDate().orElse(toEdit.getDate());
        Description newDescription = descriptor.getDescription().orElse(toEdit.getDescription());
        UniquePersonList newPeople = descriptor.getPeople().orElse(toEdit.getPeopleInvolved());

        if (newPeople.size() == 1 && descriptor.getShares().isEmpty()) {
            return new ReceiveMoney(newPeople.asUnmodifiableObservableList().get(0),
                    newAmount, newDate, newDescription);
        } else {
            return new Split(newAmount, newDate, newDescription, descriptor.getShares().get(), newPeople);
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
     * Stores the details to update the transaction with. Each non-empty field value will replace the
     * corresponding field value of the transaction.
     */
    public static class UpdateTransactionDescriptor {
        private Description description;
        private Amount amount;
        private Date date;
        private Set<Category> categories;
        private UniquePersonList people;
        private List<Integer> shares;

        public UpdateTransactionDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code categories} is used internally.
         */
        public UpdateTransactionDescriptor(UpdateTransactionDescriptor toCopy) {
            setDescription(toCopy.description);
            setAmount(toCopy.amount);
            setDate(toCopy.date);
            setCategories(toCopy.categories);
            setPeople(toCopy.people);
            setShares(toCopy.shares);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, amount, date, categories, people, shares);
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

        public void setPeople(UniquePersonList people) {
            this.people = people;
        }

        public Optional<UniquePersonList> getPeople() {
            return Optional.ofNullable(people);
        }

        public void setShares(List<Integer> shares) {
            this.shares = shares;
        }

        public Optional<List<Integer>> getShares() {
            return Optional.ofNullable(shares);
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
                && getCategories().equals(e.getCategories())
                && getDescription().equals(e.getDescription());
        }
    }

}
