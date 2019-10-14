package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ENTRIES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;

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
import seedu.address.model.person.Amount;
import seedu.address.model.person.Description;
import seedu.address.model.person.Expense;
import seedu.address.model.person.Time;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditExpenseCommand extends Command {

    public static final String COMMAND_WORD = "editExpense";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the Expense identified "
            + "by the index number used in the displayed Expenses list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESC + "NAME] "
            + "[" + PREFIX_TIME + "TIME] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AMOUNT + "5.60";

    public static final String MESSAGE_EDIT_ENTRY_SUCCESS = "Edited Expense: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ENTRY = "This entry already exists in the address book.";

    private final Index index;
    private final EditExpenseDescriptor editEntryDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editEntryDescriptor details to edit the person with
     */
    public EditExpenseCommand(Index index, EditExpenseDescriptor editEntryDescriptor) {
        requireNonNull(index);
        requireNonNull(editEntryDescriptor);

        this.index = index;
        this.editEntryDescriptor = new EditExpenseDescriptor(editEntryDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Expense> lastShownList = model.getFilteredExpenses();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Expense entryToEdit = lastShownList.get(index.getZeroBased());
        Expense editedEntry = createEditedExpense(entryToEdit, editEntryDescriptor);

        if (!entryToEdit.isSameEntry(editedEntry) && model.hasEntry(editedEntry)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        }

        model.setExpense(entryToEdit, editedEntry);
        model.updateFilteredExpenses(PREDICATE_SHOW_ALL_EXPENSES);
        model.updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
        return new CommandResult(String.format(MESSAGE_EDIT_ENTRY_SUCCESS, editedEntry));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Expense createEditedExpense(Expense expenseToEdit, EditExpenseDescriptor editEntryDescriptor) {
        assert expenseToEdit != null;
        Description updatedName = editEntryDescriptor.getDesc().orElse(expenseToEdit.getDesc());
        Time updatedTime = editEntryDescriptor.getTime().orElse(expenseToEdit.getTime());
        Amount updatedAmount = editEntryDescriptor.getAmount().orElse(expenseToEdit.getAmount());
        Set<Tag> updatedTags = editEntryDescriptor.getTags().orElse(expenseToEdit.getTags());
        return new Expense(updatedName, updatedTime, updatedAmount, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditExpenseCommand)) {
            return false;
        }

        // state check
        EditExpenseCommand e = (EditExpenseCommand) other;
        return index.equals(e.index)
                && editEntryDescriptor.equals(e.editEntryDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditExpenseDescriptor {
        private Description desc;
        private Time time;
        private Amount amt;
        private Set<Tag> tags;

        public EditExpenseDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditExpenseDescriptor(EditExpenseDescriptor toCopy) {
            setDesc(toCopy.desc);
            setTime(toCopy.time);
            setAmount(toCopy.amt);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(desc, amt, tags);
        }

        public void setDesc(Description desc) {
            this.desc = desc;
        }

        public Optional<Description> getDesc() {
            return Optional.ofNullable(desc);
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public Optional<Time> getTime() {
            return Optional.ofNullable(time);
        }

        public void setAmount(Amount amt) {
            this.amt = amt;
        }

        public Optional<Amount> getAmount() {
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
            if (!(other instanceof EditExpenseDescriptor)) {
                return false;
            }

            // state check
            EditExpenseDescriptor e = (EditExpenseDescriptor) other;

            return getDesc().equals(e.getDesc())
                    && getAmount().equals(e.getAmount())
                    && getTime().equals(e.getTime())
                    && getTime().equals(e.getTime())
                    && getTags().equals(e.getTags());
        }
    }
}
