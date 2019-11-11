package seedu.guilttrip.logic.commands.editcommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.guilttrip.model.Model.PREDICATE_SHOW_ALL_WISHES;

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
import seedu.guilttrip.model.entry.Wish;
import seedu.guilttrip.model.tag.Tag;

/**
 * Edits the details of an existing wish in GuiltTrip.
 */
public class EditWishCommand extends Command {

    public static final String COMMAND_WORD = "editWish";

    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Edits the details of the Wish identified "
            + "by the index number used in the displayed Wishes list. ";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESC + "NAME] "
            + "[" + PREFIX_DATE + "TIME] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AMOUNT + "5.60";

    public static final String MESSAGE_EDIT_ENTRY_SUCCESS = "Edited Wish: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ENTRY = "This entry already exists in the guilttrip book.";

    private final Index index;
    private final EditWishDescriptor editEntryDescriptor;

    /**
     * @param index of the wish in the filtered wish list to edit
     * @param editEntryDescriptor details to edit the wish with
     */
    public EditWishCommand(Index index, EditWishDescriptor editEntryDescriptor) {
        requireNonNull(index);
        requireNonNull(editEntryDescriptor);

        this.index = index;
        this.editEntryDescriptor = new EditWishDescriptor(editEntryDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Wish> lastShownList = model.getFilteredWishes();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        Wish entryToEdit = lastShownList.get(index.getZeroBased());
        Wish editedEntry = createEditedWish(entryToEdit, editEntryDescriptor);
        if (!model.hasCategory(editedEntry.getCategory())) {
            throw new CommandException(MESSAGE_INVALID_CATEGORY);
        }

        if (!entryToEdit.isSameEntry(editedEntry) && model.hasWish(editedEntry)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        }

        model.setWish(entryToEdit, editedEntry);
        model.updateFilteredWishes(PREDICATE_SHOW_ALL_WISHES);
        model.commitGuiltTrip();
        return new CommandResult(String.format(MESSAGE_EDIT_ENTRY_SUCCESS, editedEntry));
    }

    /**
     * Creates and returns a {@code Wish} with the details of {@code wishToEdit}
     * edited with {@code EditWersonDescriptor}.
     */
    private static Wish createEditedWish(Wish wishToEdit, EditWishDescriptor editEntryDescriptor) {
        assert wishToEdit != null;
        Category updatedCategory = editEntryDescriptor.getCategory().orElse(wishToEdit.getCategory());
        Description updatedName = editEntryDescriptor.getDesc().orElse(wishToEdit.getDesc());
        Date updatedTime = editEntryDescriptor.getDate().orElse(wishToEdit.getDate());
        Amount updatedAmount = editEntryDescriptor.getAmount().orElse(wishToEdit.getAmount());
        Set<Tag> updatedTags = editEntryDescriptor.getTags().orElse(wishToEdit.getTags());
        return new Wish(updatedCategory, updatedName, updatedTime, updatedAmount, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditWishCommand)) {
            return false;
        }

        // state check
        EditWishCommand e = (EditWishCommand) other;
        return index.equals(e.index)
                && editEntryDescriptor.equals(e.editEntryDescriptor);
    }

    /**
     * Stores the details to edit the wish with. Each non-empty field value will replace the
     * corresponding field value of the wish.
     */
    public static class EditWishDescriptor {
        private Category category;
        private Description desc;
        private Date date;
        private Amount amt;
        private Set<Tag> tags;

        public EditWishDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditWishDescriptor(EditWishDescriptor toCopy) {
            setCategory(toCopy.category);
            setDesc(toCopy.desc);
            setDate(toCopy.date);
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

        public void setDate(Date time) {
            this.date = time;
        }

        public Optional<Date> getDate() {
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
            if (!(other instanceof EditWishDescriptor)) {
                return false;
            }

            // state check
            EditWishDescriptor e = (EditWishDescriptor) other;

            return getDesc().equals(e.getDesc())
                    && getAmount().equals(e.getAmount())
                    && getDate().equals(e.getDate())
                    && getTags().equals(e.getTags());
        }
    }
}
