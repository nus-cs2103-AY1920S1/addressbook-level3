package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SPENDINGS;

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
import seedu.address.model.spending.Cost;
import seedu.address.model.spending.Date;
import seedu.address.model.spending.Name;
import seedu.address.model.spending.Remark;
import seedu.address.model.spending.Spending;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing Spending in the MoneyGoWhere list.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the Spending identified "
            + "by the index number used in the displayed Spending list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_COST + "COST] "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "today "
            + PREFIX_REMARK + "Likes to play soccer";

    public static final String MESSAGE_EDIT_SPENDING_SUCCESS = "Edited Spending: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_SPENDING = "This Spending already exists in the MoneyGoWhere list.";

    private final Index index;
    private final EditSpendingDescriptor editSpendingDescriptor;

    /**
     * @param index of the Spending in the filtered Spending list to edit
     * @param editSpendingDescriptor details to edit the Spending with
     */
    public EditCommand(Index index, EditSpendingDescriptor editSpendingDescriptor) {
        requireNonNull(index);
        requireNonNull(editSpendingDescriptor);

        this.index = index;
        this.editSpendingDescriptor = new EditSpendingDescriptor(editSpendingDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Spending> lastShownList = model.getFilteredSpendingList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SPENDING_DISPLAYED_INDEX);
        }

        Spending spendingToEdit = lastShownList.get(index.getZeroBased());
        Spending editedSpending = createEditedSpending(spendingToEdit, editSpendingDescriptor);

        if (!spendingToEdit.isSameSpending(editedSpending) && model.hasSpending(editedSpending)) {
            throw new CommandException(MESSAGE_DUPLICATE_SPENDING);
        }

        model.setSpending(spendingToEdit, editedSpending);
        model.updateFilteredSpendingList(PREDICATE_SHOW_ALL_SPENDINGS);
        return new CommandResult(String.format(MESSAGE_EDIT_SPENDING_SUCCESS, editedSpending));
    }

    /**
     * Creates and returns a {@code Spending} with the details of {@code SpendingToEdit}
     * edited with {@code editSpendingDescriptor}.
     */
    private static Spending createEditedSpending(Spending spendingToEdit,
            EditSpendingDescriptor editSpendingDescriptor) {
        assert spendingToEdit != null;

        Name updatedName = editSpendingDescriptor.getName().orElse(spendingToEdit.getName());
        Date updatedDate = editSpendingDescriptor.getDate().orElse(spendingToEdit.getDate());
        Remark updatedRemark = editSpendingDescriptor.getRemark().orElse(spendingToEdit.getRemark());
        Cost updatedCost = editSpendingDescriptor.getCost().orElse(spendingToEdit.getCost());
        Set<Tag> updatedTags = editSpendingDescriptor.getTags().orElse(spendingToEdit.getTags());

        return new Spending(updatedName, updatedDate, updatedRemark, updatedCost, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editSpendingDescriptor.equals(e.editSpendingDescriptor);
    }

    /**
     * Stores the details to edit the Spending with.
     * Each non-empty field value will replace the corresponding field value of the Spending.
     */
    public static class EditSpendingDescriptor {

        private Name name;
        private Date date;
        private Remark remark;
        private Cost cost;
        private Set<Tag> tags;

        public EditSpendingDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditSpendingDescriptor(EditSpendingDescriptor toCopy) {
            setName(toCopy.name);
            setDate(toCopy.date);
            setRemark(toCopy.remark);
            setCost(toCopy.cost);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, date, remark, cost, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }

        public void setCost(Cost cost) {
            this.cost = cost;
        }

        public Optional<Cost> getCost() {
            return Optional.ofNullable(cost);
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
            if (!(other instanceof EditSpendingDescriptor)) {
                return false;
            }

            // state check
            EditSpendingDescriptor e = (EditSpendingDescriptor) other;

            return getName().equals(e.getName())
                    && getDate().equals(e.getDate())
                    && getRemark().equals(e.getRemark())
                    && getCost().equals(e.getCost())
                    && getTags().equals(e.getTags());
        }
    }
}
