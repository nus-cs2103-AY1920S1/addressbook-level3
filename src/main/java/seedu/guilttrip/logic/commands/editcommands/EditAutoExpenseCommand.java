package seedu.guilttrip.logic.commands.editcommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_FREQ;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TAG;

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
import seedu.guilttrip.model.entry.AutoExpense;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.tag.Tag;
import seedu.guilttrip.model.util.Frequency;

/**
 * Edits the details of an existing AutoExpense in the GuiltTrip.
 */
public class EditAutoExpenseCommand extends Command {

    public static final String COMMAND_WORD = "editAutoExp";

    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Edits the details of the AutoExpense identified "
            + "by the index number used in the displayed AutoExpenses list. ";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DESC + "DESCRIPTION "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_FREQ + "FREQUENCY "
            + PREFIX_DATE + "DATE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: "
            + COMMAND_WORD + " 1 " + PREFIX_AMOUNT + "5.60";

    public static final String MESSAGE_EDIT_ENTRY_SUCCESS = "Edited Auto Expense: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ENTRY = "This entry already exists in the guiltTrip!";

    private final Index index;
    private final EditAutoExpenseDescriptor editAutoExpenseDescriptor;

    /**
     * @param index of the autoExpense in the filtered expense AutoExpense list to edit
     * @param editAutoExpenseDescriptor details to edit the entry with
     */
    public EditAutoExpenseCommand(Index index, EditAutoExpenseDescriptor editAutoExpenseDescriptor) {
        requireNonNull(index);
        requireNonNull(editAutoExpenseDescriptor);

        this.index = index;
        this.editAutoExpenseDescriptor = new EditAutoExpenseDescriptor(editAutoExpenseDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<AutoExpense> lastShownList = model.getFilteredAutoExpenses();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        AutoExpense entryToEdit = lastShownList.get(index.getZeroBased());
        AutoExpense editedEntry = createEditedAutoExpense(entryToEdit, editAutoExpenseDescriptor);
        if (!model.hasCategory(editedEntry.getCategory())) {
            throw new CommandException(MESSAGE_INVALID_CATEGORY);
        }

        if (!entryToEdit.isSameEntry(editedEntry) && model.hasAutoExpense(editedEntry)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        }

        model.setAutoExpense(entryToEdit, editedEntry);
        model.commitGuiltTrip();
        return new CommandResult(String.format(MESSAGE_EDIT_ENTRY_SUCCESS, editedEntry));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static AutoExpense createEditedAutoExpense(AutoExpense expenseToEdit,
            EditAutoExpenseDescriptor editAutoExpenseDescriptor) {
        assert expenseToEdit != null;

        Description updatedDescription = editAutoExpenseDescriptor.getDesc().orElse(expenseToEdit.getDesc());
        Amount updatedAmount = editAutoExpenseDescriptor.getAmount().orElse(expenseToEdit.getAmount());
        Frequency updatedFrequency = editAutoExpenseDescriptor.getFrequency().orElse(expenseToEdit.getFrequency());
        Date updatedDate = editAutoExpenseDescriptor.getDate().orElse(expenseToEdit.getDate());
        Category updatedCategory = editAutoExpenseDescriptor.getCategory().orElse(expenseToEdit.getCategory());
        Set<Tag> updatedTags = editAutoExpenseDescriptor.getTags().orElse(expenseToEdit.getTags());

        return new AutoExpense(updatedCategory, updatedDescription, updatedAmount, updatedTags, updatedFrequency,
                updatedDate);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAutoExpenseCommand)) {
            return false;
        }

        // state check
        EditAutoExpenseCommand e = (EditAutoExpenseCommand) other;
        return index.equals(e.index) && editAutoExpenseDescriptor.equals(e.editAutoExpenseDescriptor);
    }

    /**
     * Stores the details to edit the entry with. Each non-empty field value will replace the
     * corresponding field value of the entry.
     */
    public static class EditAutoExpenseDescriptor {
        private Description desc;
        private Amount amt;
        private Set<Tag> tags;
        private Date date;
        private Frequency freq;
        private Category cat;

        public EditAutoExpenseDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditAutoExpenseDescriptor(EditAutoExpenseDescriptor toCopy) {
            setDesc(toCopy.desc);
            setAmount(toCopy.amt);
            setTags(toCopy.tags);
            setDate(toCopy.date);
            setFrequency(toCopy.freq);
            setCategory(toCopy.cat);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(desc, amt, tags);
        }

        /**
         * @param desc the desc to set
         */
        public void setDesc(Description desc) {
            this.desc = desc;
        }

        public Optional<Description> getDesc() {
            return Optional.ofNullable(desc);
        }

        /**
         * @param amt the amt to set
         */
        public void setAmount(Amount amt) {
            this.amt = amt;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amt);
        }

        /**
         * Sets the date.
         *
         * @param date the date to set
         */
        public void setDate(Date date) {
            this.date = date;
        }

        /**
         * Get the Optional Date.
         *
         * @return the date
         */
        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        /**
         * Set the Frequency.
         *
         * @param freq the freq to set
         */
        public void setFrequency(Frequency freq) {
            this.freq = freq;
        }

        /**
         * Get the Optional Frequency.
         *
         * @return the freq
         */
        public Optional<Frequency> getFrequency() {
            return Optional.ofNullable(freq);
        }

        /**
         * Set the Category.
         *
         * @param cat the cat to set
         */
        public void setCategory(Category cat) {
            this.cat = cat;
        }

        /**
         * Get the Optional Category.
         *
         * @return the cat
         */
        public Optional<Category> getCategory() {
            return Optional.ofNullable(cat);
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
            if (!(other instanceof EditAutoExpenseDescriptor)) {
                return false;
            }

            // state check
            EditAutoExpenseDescriptor e = (EditAutoExpenseDescriptor) other;

            return getDesc().equals(e.getDesc()) && getAmount().equals(e.getAmount()) && getTags().equals(e.getTags())
                    && getFrequency().equals(e.getFrequency()) && getCategory().equals(e.getCategory())
                    && getDate().equals(e.getDate());
        }
    }
}
