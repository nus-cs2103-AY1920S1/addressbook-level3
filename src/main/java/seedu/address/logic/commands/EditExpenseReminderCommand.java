package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSE_REMINDERS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ExpenseContainsTagPredicate;
import seedu.address.model.person.ExpenseReminder;
import seedu.address.model.person.ExpenseTracker;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditExpenseReminderCommand extends Command {

    public static final String COMMAND_WORD = "editExpenseReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the Expense identified "
            + "by the index number used in the displayed Expenses list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DESC + "REMINDER_MESSAGE"
            + PREFIX_AMOUNT + "QUOTA "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AMOUNT + "5.60";

    public static final String MESSAGE_EDIT_ENTRY_SUCCESS = "Edited Expense Reminder: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ENTRY = "This entry already exists in the address book.";

    private final Index index;
    private final EditReminderDescriptor editReminderDescriptor;

    /**
     * @param index of the expenseReminder in the filtered expense reminder list to edit
     * @param editReminderDescriptor details to edit the person with
     */
    public EditExpenseReminderCommand(Index index, EditReminderDescriptor editReminderDescriptor) {
        requireNonNull(index);
        requireNonNull(editReminderDescriptor);

        this.index = index;
        this.editReminderDescriptor = new EditReminderDescriptor(editReminderDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<ExpenseReminder> lastShownList = model.getFilteredExpenseReminders();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ExpenseReminder entryToEdit = lastShownList.get(index.getZeroBased());
        ExpenseReminder editedEntry = createEditedExpenseReminder(entryToEdit, editReminderDescriptor);

        if (!entryToEdit.isSameReminder(editedEntry) && model.hasExpenseReminder(editedEntry)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        }

        model.setExpenseReminder(entryToEdit, editedEntry);
        model.updateFilteredExpenseReminders(PREDICATE_SHOW_ALL_EXPENSE_REMINDERS);
        return new CommandResult(String.format(MESSAGE_EDIT_ENTRY_SUCCESS, editedEntry));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static ExpenseReminder createEditedExpenseReminder(ExpenseReminder expenseToEdit,
                                                               EditReminderDescriptor editEntryDescriptor) {
        assert expenseToEdit != null;
        String updatedMessage = editEntryDescriptor.getDesc().orElse(expenseToEdit.getMessage());
        Long updatedAmount = editEntryDescriptor.getAmount().orElse(expenseToEdit.getQuota());
        Set<Tag> updatedTags = editEntryDescriptor.getTags()
                .orElse(expenseToEdit.getTracker().getPredicate().getTags());
        ExpenseTracker tracker = new ExpenseTracker(new ExpenseContainsTagPredicate(updatedTags));
        return new ExpenseReminder(updatedMessage, updatedAmount, tracker);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditExpenseReminderCommand)) {
            return false;
        }

        // state check
        EditExpenseReminderCommand e = (EditExpenseReminderCommand) other;
        return index.equals(e.index)
                && editReminderDescriptor.equals(e.editReminderDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditReminderDescriptor {
        private String desc;
        private Long amt;
        private Set<Tag> tags;

        public EditReminderDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditReminderDescriptor(EditReminderDescriptor toCopy) {
            setDesc(toCopy.desc);
            setAmount(toCopy.amt);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(desc, amt, tags);
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Optional<String> getDesc() {
            return Optional.ofNullable(desc);
        }


        public void setAmount(Long amt) {
            this.amt = amt;
        }

        public Optional<Long> getAmount() {
            return Optional.ofNullable(amt);
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
            if (!(other instanceof EditReminderDescriptor)) {
                return false;
            }

            // state check
            EditReminderDescriptor e = (EditReminderDescriptor) other;

            return getDesc().equals(e.getDesc())
                    && getAmount().equals(e.getAmount())
                    && getTags().equals(e.getTags());
        }
    }
}
