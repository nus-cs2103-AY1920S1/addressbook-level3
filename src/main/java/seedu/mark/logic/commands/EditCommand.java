package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_URL;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.mark.model.Model.PREDICATE_SHOW_ALL_BOOKMARKS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.mark.commons.core.Messages;
import seedu.mark.commons.core.index.Index;
import seedu.mark.commons.util.CollectionUtil;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.model.Model;
import seedu.mark.model.bookmark.Address;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Name;
import seedu.mark.model.bookmark.Phone;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.tag.Tag;

/**
 * Edits the details of an existing bookmark in the bookmark manager.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the bookmark identified "
            + "by the index number used in the displayed bookmark list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_URL + "URL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_URL + "johndoe@example.com"; // TODO: change EditCommand example

    public static final String MESSAGE_EDIT_BOOKMARK_SUCCESS = "Edited Bookmark: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_BOOKMARK = "This bookmark already exists in the bookmark manager.";

    private final Index index;
    private final EditBookmarkDescriptor editBookmarkDescriptor;

    /**
     * @param index of the bookmark in the filtered bookmark list to edit
     * @param editBookmarkDescriptor details to edit the bookmark with
     */
    public EditCommand(Index index, EditBookmarkDescriptor editBookmarkDescriptor) {
        requireNonNull(index);
        requireNonNull(editBookmarkDescriptor);

        this.index = index;
        this.editBookmarkDescriptor = new EditBookmarkDescriptor(editBookmarkDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Bookmark> lastShownList = model.getFilteredBookmarkList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
        }

        Bookmark bookmarkToEdit = lastShownList.get(index.getZeroBased());
        Bookmark editedBookmark = createEditedBookmark(bookmarkToEdit, editBookmarkDescriptor);

        if (!bookmarkToEdit.isSameBookmark(editedBookmark) && model.hasBookmark(editedBookmark)) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOKMARK);
        }

        model.setBookmark(bookmarkToEdit, editedBookmark);
        model.updateFilteredBookmarkList(PREDICATE_SHOW_ALL_BOOKMARKS);
        return new CommandResult(String.format(MESSAGE_EDIT_BOOKMARK_SUCCESS, editedBookmark));
    }

    /**
     * Creates and returns a {@code Bookmark} with the details of {@code bookmarkToEdit}
     * edited with {@code editBookmarkDescriptor}.
     */
    private static Bookmark createEditedBookmark(Bookmark bookmarkToEdit, EditBookmarkDescriptor editBookmarkDescriptor) {
        assert bookmarkToEdit != null;

        Name updatedName = editBookmarkDescriptor.getName().orElse(bookmarkToEdit.getName());
        Phone updatedPhone = editBookmarkDescriptor.getPhone().orElse(bookmarkToEdit.getPhone());
        Url updatedUrl = editBookmarkDescriptor.getUrl().orElse(bookmarkToEdit.getUrl());
        Address updatedAddress = editBookmarkDescriptor.getAddress().orElse(bookmarkToEdit.getAddress());
        Set<Tag> updatedTags = editBookmarkDescriptor.getTags().orElse(bookmarkToEdit.getTags());

        return new Bookmark(updatedName, updatedPhone, updatedUrl, updatedAddress, updatedTags);
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
                && editBookmarkDescriptor.equals(e.editBookmarkDescriptor);
    }

    /**
     * Stores the details to edit the bookmark with. Each non-empty field value will replace the
     * corresponding field value of the bookmark.
     */
    public static class EditBookmarkDescriptor {
        private Name name;
        private Phone phone;
        private Url url;
        private Address address;
        private Set<Tag> tags;

        public EditBookmarkDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditBookmarkDescriptor(EditBookmarkDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setUrl(toCopy.url);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, url, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setUrl(Url url) {
            this.url = url;
        }

        public Optional<Url> getUrl() {
            return Optional.ofNullable(url);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
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
            if (!(other instanceof EditBookmarkDescriptor)) {
                return false;
            }

            // state check
            EditBookmarkDescriptor e = (EditBookmarkDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getUrl().equals(e.getUrl())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
