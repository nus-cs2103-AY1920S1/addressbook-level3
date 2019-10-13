package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.weme.model.Model.PREDICATE_SHOW_ALL_MEMES;

import java.util.List;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.meme.Meme;

/**
 * Edits the details of an existing meme in the meme book.
 */
public class LikeMemeCommand extends Command {

    public static final String COMMAND_WORD = "like";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Like a meme by index."
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_LIKE_MEME_SUCCESS = "Liked Meme: %1$s";
    public static final String MESSAGE_NOT_LIKED = "Please specify the index of the meme that you like.";

    private final Index index;

    /**
     * @param index of the meme in the filtered meme list to like
     */
    public LikeMemeCommand(Index index) {
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

        Meme memeToLike = lastShownList.get(index.getZeroBased());

        model.getLikeData().incrementLikesByMeme(memeToLike);
        model.updateFilteredMemeList(PREDICATE_SHOW_ALL_MEMES); // This line only re-renders the GUI on first like.
        return new CommandResult(String.format(MESSAGE_LIKE_MEME_SUCCESS, memeToLike));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LikeMemeCommand)) {
            return false;
        }

        // state check
        LikeMemeCommand e = (LikeMemeCommand) other;
        return index.equals(e.index);
    }

}
