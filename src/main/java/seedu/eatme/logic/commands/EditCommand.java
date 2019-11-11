package seedu.eatme.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.eatme.model.Model.PREDICATE_SHOW_ALL_EATERIES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.eatme.commons.core.Messages;
import seedu.eatme.commons.core.index.Index;
import seedu.eatme.commons.util.CollectionUtil;
import seedu.eatme.logic.commands.exceptions.CommandException;
import seedu.eatme.model.Model;
import seedu.eatme.model.eatery.Address;
import seedu.eatme.model.eatery.Category;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.model.eatery.Name;
import seedu.eatme.model.eatery.Tag;

/**
 * Edits the details of an existing eatery in the eatery list.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the eatery identified "
            + "by the index number used in the displayed eatery list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: [index] (must be a positive integer) "
            + "{" + PREFIX_NAME + " [name]} "
            + "{" + PREFIX_ADDRESS + " [address]} "
            + "{" + PREFIX_CATEGORY + " [category]} "
            + "{" + PREFIX_TAG + " [tag]} ...\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_ADDRESS + " 100 Orchard Road";

    public static final String MESSAGE_EDIT_EATERY_SUCCESS = "Eatery successfully edited: %s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EATERY = "This eatery already exists!";

    private final Index index;
    private final EditEateryDescriptor editEateryDescriptor;

    /**
     * @param index of the eatery in the filtered eatery list to edit
     * @param editEateryDescriptor details to edit the eatery with
     */
    public EditCommand(Index index, EditEateryDescriptor editEateryDescriptor) {
        requireNonNull(index);
        requireNonNull(editEateryDescriptor);

        this.index = index;
        this.editEateryDescriptor = new EditEateryDescriptor(editEateryDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Eatery> lastShownList = model.getFilteredEateryList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
        }

        Eatery eateryToEdit = lastShownList.get(index.getZeroBased());
        Eatery editedEatery = createEditedEatery(eateryToEdit, editEateryDescriptor);

        if (!eateryToEdit.isSameEatery(editedEatery) && model.hasEatery(editedEatery)) {
            throw new CommandException(MESSAGE_DUPLICATE_EATERY);
        }

        model.setEatery(eateryToEdit, editedEatery);
        model.updateFilteredEateryList(PREDICATE_SHOW_ALL_EATERIES);
        return new CommandResult(String.format(MESSAGE_EDIT_EATERY_SUCCESS, editedEatery.getName()));
    }

    /**
     * Creates and returns a {@code Eatery} with the details of {@code eateryToEdit}
     * edited with {@code editEateryDescriptor}.
     */
    private static Eatery createEditedEatery(Eatery eateryToEdit, EditEateryDescriptor editEateryDescriptor) {
        assert eateryToEdit != null;

        Name updatedName = editEateryDescriptor.getName().orElse(eateryToEdit.getName());
        Address updatedAddress = editEateryDescriptor.getAddress().orElse(eateryToEdit.getAddress());
        Category updatedCategory = editEateryDescriptor.getCategory().orElse(eateryToEdit.getCategory());
        Set<Tag> updatedTags = editEateryDescriptor.getTags().orElse(eateryToEdit.getTags());

        Eatery newEatery = new Eatery(updatedName, updatedAddress, updatedCategory, updatedTags);
        newEatery.setReviews(eateryToEdit.getReviews());
        return newEatery;
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
                && editEateryDescriptor.equals(e.editEateryDescriptor);
    }

    /**
     * Stores the details to edit the eatery with. Each non-empty field value will replace the
     * corresponding field value of the eatery.
     */
    public static class EditEateryDescriptor {
        private Name name;
        private boolean isOpen;
        private Address address;
        private Category category;
        private Set<Tag> tags;

        public EditEateryDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEateryDescriptor(EditEateryDescriptor toCopy) {
            setName(toCopy.name);
            setCategory(toCopy.category);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, address, category, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public Optional<Category> getCategory() {
            return Optional.ofNullable(category);
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
            if (!(other instanceof EditEateryDescriptor)) {
                return false;
            }

            // state check
            EditEateryDescriptor e = (EditEateryDescriptor) other;

            return getName().equals(e.getName())
                    && getAddress().equals(e.getAddress())
                    && getCategory().equals(e.getCategory())
                    && getTags().equals(e.getTags());
        }
    }
}
