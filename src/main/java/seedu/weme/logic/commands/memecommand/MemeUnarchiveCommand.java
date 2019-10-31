package seedu.weme.logic.commands.memecommand;

import static java.util.Objects.requireNonNull;
import static seedu.weme.model.Model.PREDICATE_SHOW_ALL_ARCHIVED_MEMES;

import java.util.List;
import java.util.Set;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.imagePath.ImagePath;
import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;

/**
 * Unarchives a meme in the display window.
 */
public class MemeUnarchiveCommand extends Command {

    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD + ": unarchive a meme by index.";
    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION
            + " Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_UNARCHIVE_MEME_SUCCESS = "Unarchived Meme: %1$s";
    public static final String MESSAGE_ALREADY_UNARCHIVED = "This meme is already unarchived!";

    private final Index index;

    /**
     * @param index of the meme in the filtered meme list to unarchive
     */
    public MemeUnarchiveCommand(Index index) {
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

        Meme memeToUnarchive = lastShownList.get(index.getZeroBased());

        if (!memeToUnarchive.isArchived()) {
            throw new CommandException(MESSAGE_ALREADY_UNARCHIVED);
        }

        Meme unarchivedMeme = createUnarchivedMeme(memeToUnarchive);

        model.setMeme(memeToUnarchive, unarchivedMeme);
        CommandResult result = new CommandResult(String.format(MESSAGE_UNARCHIVE_MEME_SUCCESS, memeToUnarchive));
        model.commitWeme(result.getFeedbackToUser());
        model.updateFilteredMemeList(PREDICATE_SHOW_ALL_ARCHIVED_MEMES);

        return result;
    }

    /**
     * Creates an unarchived Meme using the input archived Meme.
     */
    private Meme createUnarchivedMeme(Meme meme) {
        assert meme != null;

        ImagePath imagePath = meme.getImagePath();
        Description description = meme.getDescription();
        Set<Tag> tags = meme.getTags();
        return new Meme(imagePath, description, tags, false);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MemeUnarchiveCommand)) {
            return false;
        }

        // state check
        MemeUnarchiveCommand e = (MemeUnarchiveCommand) other;
        return index.equals(e.index);
    }

}
