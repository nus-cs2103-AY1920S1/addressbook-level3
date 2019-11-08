package seedu.weme.logic.commands.memecommand;

import static java.util.Objects.requireNonNull;
import static seedu.weme.model.Model.PREDICATE_SHOW_ALL_UNARCHIVED_MEMES;

import java.util.List;
import java.util.Set;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.path.ImagePath;
import seedu.weme.model.tag.Tag;

/**
 * Archives a meme in the display window.
 */
public class MemeArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD + ": archive a meme by index.";
    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION
            + " Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_ARCHIVE_MEME_SUCCESS = "Archived Meme: %1$s";
    public static final String MESSAGE_ALREADY_ARCHIVED = "This meme is already archived!";

    private final Index index;

    /**
     * @param index of the meme in the filtered meme list to archive
     */
    public MemeArchiveCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Meme> lastShownList = model.getFilteredMemeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
        }

        Meme memeToArchive = lastShownList.get(index.getZeroBased());

        if (memeToArchive.isArchived()) {
            throw new CommandException(MESSAGE_ALREADY_ARCHIVED);
        }

        Meme archivedMeme = createArchivedMeme(memeToArchive);

        model.setMeme(memeToArchive, archivedMeme);
        CommandResult result = new CommandResult(String.format(MESSAGE_ARCHIVE_MEME_SUCCESS, memeToArchive));
        model.commitWeme(result.getFeedbackToUser());
        model.updateFilteredMemeList(PREDICATE_SHOW_ALL_UNARCHIVED_MEMES);

        return result;
    }

    /**
     * Creates an archived Meme using the input unarchived Meme.
     */
    private Meme createArchivedMeme(Meme meme) {
        assert meme != null;

        ImagePath imagePath = meme.getImagePath();
        Description description = meme.getDescription();
        Set<Tag> tags = meme.getTags();
        return new Meme(imagePath, description, tags, true);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MemeArchiveCommand)) {
            return false;
        }

        // state check
        MemeArchiveCommand e = (MemeArchiveCommand) other;
        return index.equals(e.index);
    }

}
