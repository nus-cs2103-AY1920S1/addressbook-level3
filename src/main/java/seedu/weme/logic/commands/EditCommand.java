package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.weme.model.Model.PREDICATE_SHOW_ALL_MEMES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.commons.util.CollectionUtil;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.meme.Address;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.meme.Name;
import seedu.weme.model.meme.Phone;
import seedu.weme.model.tag.Tag;

/**
 * Edits the details of an existing meme in the meme book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the meme identified "
            + "by the index number used in the displayed meme list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 ";

    public static final String MESSAGE_EDIT_MEME_SUCCESS = "Edited Meme: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MEME = "This meme already exists in weme.";

    private final Index index;
    private final EditMemeDescriptor editMemeDescriptor;

    /**
     * @param index of the meme in the filtered meme list to edit
     * @param editMemeDescriptor details to edit the meme with
     */
    public EditCommand(Index index, EditMemeDescriptor editMemeDescriptor) {
        requireNonNull(index);
        requireNonNull(editMemeDescriptor);

        this.index = index;
        this.editMemeDescriptor = new EditMemeDescriptor(editMemeDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Meme> lastShownList = model.getFilteredMemeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
        }

        Meme memeToEdit = lastShownList.get(index.getZeroBased());
        Meme editedMeme = createEditedMeme(memeToEdit, editMemeDescriptor);

        if (!memeToEdit.isSameMeme(editedMeme) && model.hasMeme(editedMeme)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEME);
        }

        model.setMeme(memeToEdit, editedMeme);
        model.updateFilteredMemeList(PREDICATE_SHOW_ALL_MEMES);
        return new CommandResult(String.format(MESSAGE_EDIT_MEME_SUCCESS, editedMeme));
    }

    /**
     * Creates and returns a {@code Meme} with the details of {@code memeToEdit}
     * edited with {@code editMemeDescriptor}.
     */
    private static Meme createEditedMeme(Meme memeToEdit, EditMemeDescriptor editMemeDescriptor) {
        assert memeToEdit != null;

        Name updatedName = editMemeDescriptor.getName().orElse(memeToEdit.getName());
        Phone updatedPhone = editMemeDescriptor.getPhone().orElse(memeToEdit.getPhone());
        Address updatedAddress = editMemeDescriptor.getAddress().orElse(memeToEdit.getAddress());
        Set<Tag> updatedTags = editMemeDescriptor.getTags().orElse(memeToEdit.getTags());

        return new Meme(updatedName, updatedPhone, updatedAddress, updatedTags);
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
                && editMemeDescriptor.equals(e.editMemeDescriptor);
    }

    /**
     * Stores the details to edit the meme with. Each non-empty field value will replace the
     * corresponding field value of the meme.
     */
    public static class EditMemeDescriptor {
        private Name name;
        private Phone phone;
        private Address address;
        private Set<Tag> tags;

        public EditMemeDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditMemeDescriptor(EditMemeDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, address, tags);
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
            if (!(other instanceof EditMemeDescriptor)) {
                return false;
            }

            // state check
            EditMemeDescriptor e = (EditMemeDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
