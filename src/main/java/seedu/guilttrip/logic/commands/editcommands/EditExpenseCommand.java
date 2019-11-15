package seedu.guilttrip.logic.commands.editcommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.guilttrip.model.Model.PREDICATE_SHOW_ALL_EXPENSES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.guilttrip.commons.core.Messages;
import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.commons.util.CollectionUtil;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.tag.Tag;

/**
 * Edits the details of an existing expense in the guiltTrip.
 */
public class EditExpenseCommand extends Command {

    public static final String COMMAND_WORD = "editExpense";
    public static final String COMMAND_WORD_SHORT = "editExp";

    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Edits the details of the Expense identified "
            + "by the index number used in the displayed Expenses list. ";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_CATEGORY + "CATEGORY] "
            + "[" + PREFIX_DESC + "NAME] "
            + "[" + PREFIX_DATE + "TIME] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AMOUNT + "5.60";

    public static final String MESSAGE_EDIT_ENTRY_SUCCESS = "Edited Expense: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ENTRY = "There is no change in the entry that you are editing.";

    private final Index index;
    private final EditExpenseDescriptor editEntryDescriptor;

    /**
     * @param index of the expense in the filtered expense list to edit
     * @param editEntryDescriptor details to edit the expense with
     */
    public EditExpenseCommand(Index index, EditExpenseDescriptor editEntryDescriptor) {
        requireNonNull(index);
        requireNonNull(editEntryDescriptor);

        this.index = index;
        this.editEntryDescriptor = new EditExpenseDescriptor(editEntryDescriptor);
    }


    /**
     *  Edits Expense at Index index in the list of Expenses. Model will handle the check if the expense is
     *  present in the list.
     *
     * @param model   {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return CommandResult the CommandResult for guiltTrip to display to User.
     * @throws CommandException if the category of the edited expense is not in the list of Categories, as well as if
     * the newly edited expense does not change any fields of the original existing one.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Expense> lastShownList = model.getFilteredExpenses();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        Expense entryToEdit = lastShownList.get(index.getZeroBased());
        Expense editedEntry = createEditedExpense(entryToEdit, editEntryDescriptor);
        if (!model.hasCategory(editedEntry.getCategory())) {
            throw new CommandException(MESSAGE_INVALID_CATEGORY);
        }

        if (entryToEdit.isSameEntry(editedEntry) && model.hasExpense(editedEntry)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        }

        model.setExpense(entryToEdit, editedEntry);
        model.updateFilteredExpenses(PREDICATE_SHOW_ALL_EXPENSES);
        model.commitGuiltTrip();
        return new CommandResult(String.format(MESSAGE_EDIT_ENTRY_SUCCESS, editedEntry));
    }

    /**
     * Creates and returns a {@code Expense} with the details of {@code expenseToEdit}
     * edited with {@code editExpenseDescriptor}.
     */
    private static Expense createEditedExpense(Expense expenseToEdit, EditExpenseDescriptor editEntryDescriptor) {
        assert expenseToEdit != null;
        Category updatedCategory = editEntryDescriptor.getCategory().orElse(expenseToEdit.getCategory());
        Description updatedName = editEntryDescriptor.getDesc().orElse(expenseToEdit.getDesc());
        Date updatedTime = editEntryDescriptor.getTime().orElse(expenseToEdit.getDate());
        Amount updatedAmount = editEntryDescriptor.getAmount().orElse(expenseToEdit.getAmount());
        Set<Tag> updatedTags = editEntryDescriptor.getTags().orElse(expenseToEdit.getTags());
        return new Expense(updatedCategory, updatedName, updatedTime, updatedAmount, updatedTags);
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
     * Stores the details to edit the expense with. Each non-empty field value will replace the
     * corresponding field value of the expense.
     */
    public static class EditExpenseDescriptor {
        private Category category;
        private Description desc;
        private Date date;
        private Amount amt;
        private Set<Tag> tags;

        public EditExpenseDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditExpenseDescriptor(EditExpenseDescriptor toCopy) {
            setCategory(toCopy.category);
            setDesc(toCopy.desc);
            setTime(toCopy.date);
            setAmount(toCopy.amt);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(category, desc, date, amt, tags);
        }

        public void setCategory(Category cat) {
            this.category = cat;
        }

        public Optional<Category> getCategory() {
            return Optional.ofNullable(category);
        }

        public void setDesc(Description desc) {
            this.desc = desc;
        }

        public Optional<Description> getDesc() {
            return Optional.ofNullable(desc);
        }

        public void setTime(Date time) {
            this.date = time;
        }

        public Optional<Date> getTime() {
            return Optional.ofNullable(date);
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
                    && getTags().equals(e.getTags());
        }
    }
}
