package seedu.weme.logic.commands.memecommand;

import static java.util.Objects.requireNonNull;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.commons.util.CollectionUtil;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.path.ImagePath;
import seedu.weme.model.tag.Tag;

/**
 * Edits the details of an existing meme in Weme.
 */
public class MemeEditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD
            + ": edits the details of the meme identified by the index number used in the displayed meme list.";

    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION
            + " Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "d/A funny meme "
            + "t/funny";

    public static final String MESSAGE_EDIT_MEME_SUCCESS = "Edited Meme: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MEME = "This meme already exists in Weme.";
    public static final String MESSAGE_STAGED_MEME = "Meme to be edited is currently being staged. "
            + "Please unstage it first.";

    private final Index index;
    private final EditMemeDescriptor editMemeDescriptor;

    /**
     * @param index              of the meme in the filtered meme list to edit
     * @param editMemeDescriptor details to edit the meme with
     */
    public MemeEditCommand(Index index, EditMemeDescriptor editMemeDescriptor) {
        requireNonNull(index);
        requireNonNull(editMemeDescriptor);

        this.index = index;
        this.editMemeDescriptor = new EditMemeDescriptor(editMemeDescriptor);
    }

    /**
     * Creates and returns a {@code Meme} with the details of {@code memeToEdit}
     * edited with {@code editMemeDescriptor}.
     */
    private static Meme createEditedMeme(Meme memeToEdit, EditMemeDescriptor editMemeDescriptor) {
        assert memeToEdit != null;

        ImagePath updatedPath = editMemeDescriptor.getFilePath().orElse(memeToEdit.getImagePath());
        Description updatedDescription = editMemeDescriptor.getDescription().orElse(memeToEdit.getDescription());
        Set<Tag> updatedTags = editMemeDescriptor.getTags().orElse(memeToEdit.getTags());
        boolean isArchived = memeToEdit.isArchived();

        return new Meme(updatedPath, updatedDescription, updatedTags, isArchived);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Meme> lastShownList = model.getFilteredMemeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
        }

        Meme memeToEdit = lastShownList.get(index.getZeroBased());
        if (model.isMemeStaged(memeToEdit)) {
            throw new CommandException(MESSAGE_STAGED_MEME);
        }
        Meme editedMeme = createEditedMeme(memeToEdit, editMemeDescriptor);

        if (!memeToEdit.isSameMeme(editedMeme) && model.hasMeme(editedMeme)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEME);
        }

        model.setMeme(memeToEdit, editedMeme);
        model.addMemeToRecords(editedMeme);
        CommandResult result = new CommandResult(String.format(MESSAGE_EDIT_MEME_SUCCESS, editedMeme));
        model.commitWeme(result.getFeedbackToUser());

        return result;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MemeEditCommand)) {
            return false;
        }

        // state check
        MemeEditCommand e = (MemeEditCommand) other;
        return index.equals(e.index)
                && editMemeDescriptor.equals(e.editMemeDescriptor);
    }

    /**
     * Stores the details to edit the meme with. Each non-empty field value will replace the
     * corresponding field value of the meme.
     */
    public static class EditMemeDescriptor {
        private ImagePath filePath;
        private Description description;
        private Set<Tag> tags;

        public EditMemeDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditMemeDescriptor(EditMemeDescriptor toCopy) {
            setFilePath(toCopy.filePath);
            setDescription(toCopy.description);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(filePath, description, tags);
        }

        public Optional<ImagePath> getFilePath() {
            return Optional.ofNullable(filePath);
        }

        public void setFilePath(ImagePath filePath) {
            this.filePath = filePath;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
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

            return getFilePath().equals(e.getFilePath())
                    && getDescription().equals(e.getDescription())
                    && getTags().equals(e.getTags());
        }
    }
}
