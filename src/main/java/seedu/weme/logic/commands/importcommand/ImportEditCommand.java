package seedu.weme.logic.commands.importcommand;

import static java.util.Objects.requireNonNull;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.logic.commands.memecommand.MemeEditCommand;
import seedu.weme.model.Model;
import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.path.ImagePath;
import seedu.weme.model.tag.Tag;

/**
 * Edits the details of an imported meme in the import tab.
 */
public class ImportEditCommand extends Command {

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
    public static final String MESSAGE_DUPLICATE_MEME = "This meme already exists in import tab.";

    private final Index index;
    private final MemeEditCommand.EditMemeDescriptor editMemeDescriptor;

    /**
     * @param index              of the meme in the filtered meme list to edit
     * @param editMemeDescriptor details to edit the meme with
     */
    public ImportEditCommand(Index index, MemeEditCommand.EditMemeDescriptor editMemeDescriptor) {
        requireNonNull(index);
        requireNonNull(editMemeDescriptor);

        this.index = index;
        this.editMemeDescriptor = new MemeEditCommand.EditMemeDescriptor(editMemeDescriptor);
    }

    /**
     * Creates and returns a {@code Meme} with the details of {@code memeToEdit}
     * edited with {@code editMemeDescriptor}.
     */
    private static Meme createEditedMeme(Meme memeToEdit, MemeEditCommand.EditMemeDescriptor editMemeDescriptor) {
        assert memeToEdit != null;

        ImagePath updatedPath = editMemeDescriptor.getFilePath().orElse(memeToEdit.getImagePath());
        Description updatedDescription = editMemeDescriptor.getDescription().orElse(memeToEdit.getDescription());
        Set<Tag> updatedTags = editMemeDescriptor.getTags().orElse(memeToEdit.getTags());

        return new Meme(updatedPath, updatedDescription, updatedTags);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Meme> lastShownList = model.getImportList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
        }

        Meme memeToEdit = lastShownList.get(index.getZeroBased());
        Meme editedMeme = createEditedMeme(memeToEdit, editMemeDescriptor);

        if (!memeToEdit.isSameMeme(editedMeme) && model.hasMeme(editedMeme)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEME);
        }

        model.setImportedMeme(memeToEdit, editedMeme);
        CommandResult result = new CommandResult(String.format(MESSAGE_EDIT_MEME_SUCCESS, editedMeme));

        return result;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ImportEditCommand)) {
            return false;
        }

        // state check
        ImportEditCommand e = (ImportEditCommand) other;
        return index.equals(e.index)
                && editMemeDescriptor.equals(e.editMemeDescriptor);
    }
}
