package seedu.guilttrip.logic.commands.editcommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_PERIOD;
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
import seedu.guilttrip.model.entry.Budget;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Period;
import seedu.guilttrip.model.tag.Tag;


/**
 * Edits the details of an existing budget in the GuiltTrip.
 */
public class EditBudgetCommand extends Command {

    public static final String COMMAND_WORD = "editBudget";

    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Edits the details of the Budget identified "
            + "by the index number used in the displayed Budget list. ";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_CATEGORY + "CATEGORY] "
            + "[" + PREFIX_DESC + "NAME] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_PERIOD + "PERIOD] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AMOUNT + "5.60";

    public static final String MESSAGE_EDIT_ENTRY_SUCCESS = "Edited Budget: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ENTRY = "There is no change in the entry that you are editing.";

    private final Index index;
    private final EditBudgetDescriptor editEntryDescriptor;

    /**
     * @param index of the entry in the filtered entry list to edit
     * @param editEntryDescriptor details to edit the entry with
     */
    public EditBudgetCommand(Index index, EditBudgetDescriptor editEntryDescriptor) {
        requireNonNull(index);
        requireNonNull(editEntryDescriptor);

        this.index = index;
        this.editEntryDescriptor = new EditBudgetDescriptor(editEntryDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Budget> lastShownList = model.getFilteredBudgets();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        Budget entryToEdit = lastShownList.get(index.getZeroBased());
        Budget editedEntry = createEditedBudget(entryToEdit, editEntryDescriptor);
        if (!model.hasCategory(editedEntry.getCategory())) {
            throw new CommandException(MESSAGE_INVALID_CATEGORY);
        }

        if (entryToEdit.isSameBudget(editedEntry) && model.hasBudget(editedEntry)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        }

        model.setBudget(entryToEdit, editedEntry);
        model.commitGuiltTrip();
        return new CommandResult(String.format(MESSAGE_EDIT_ENTRY_SUCCESS, editedEntry));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Budget createEditedBudget(Budget budgetToEdit, EditBudgetDescriptor editEntryDescriptor) {
        assert budgetToEdit != null;
        Category updatedCategory = editEntryDescriptor.getCategory().orElse(budgetToEdit.getCategory());
        Description updatedName = editEntryDescriptor.getDesc().orElse(budgetToEdit.getDesc());
        Date updatedDate = editEntryDescriptor.getDate().orElse(budgetToEdit.getDate());
        Period updatedPeriod = editEntryDescriptor.getPeriod().orElse(budgetToEdit.getPeriod());
        Amount updatedAmount = editEntryDescriptor.getAmount().orElse(budgetToEdit.getAmount());
        Set<Tag> updatedTags = editEntryDescriptor.getTags().orElse(budgetToEdit.getTags());
        return new Budget(updatedCategory, updatedName, updatedDate, updatedPeriod, updatedAmount,
                updatedTags, budgetToEdit.getSpent());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditBudgetCommand)) {
            return false;
        }

        // state check
        EditBudgetCommand e = (EditBudgetCommand) other;
        return index.equals(e.index)
                && editEntryDescriptor.equals(e.editEntryDescriptor);
    }

    /**
     * Stores the details to edit the entry with. Each non-empty field value will replace the
     * corresponding field value of the entry.
     */
    public static class EditBudgetDescriptor {
        private Category category;
        private Description desc;
        private Date date;
        private Period period;
        private Amount amt;
        private Set<Tag> tags;

        public EditBudgetDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditBudgetDescriptor(EditBudgetDescriptor toCopy) {
            setCategory(toCopy.category);
            setDesc(toCopy.desc);
            setDate(toCopy.date);
            setPeriod(toCopy.period);
            setAmount(toCopy.amt);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(category, desc, date, amt, tags, period);
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

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setPeriod(Period period) {
            this.period = period;
        }

        public Optional<Period> getPeriod() {
            return Optional.ofNullable(period);
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
            if (!(other instanceof EditBudgetDescriptor)) {
                return false;
            }

            // state check
            EditBudgetDescriptor e = (EditBudgetDescriptor) other;

            return getDesc().equals(e.getDesc())
                    && getCategory().equals(e.getCategory())
                    && getAmount().equals(e.getAmount())
                    && getDate().equals(e.getDate())
                    && getPeriod().equals(e.getPeriod())
                    && getTags().equals(e.getTags());
        }
    }
}
