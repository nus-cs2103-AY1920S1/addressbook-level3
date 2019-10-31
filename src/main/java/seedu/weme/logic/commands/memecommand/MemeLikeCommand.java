package seedu.weme.logic.commands.memecommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.meme.Meme;

/**
 * Likes a meme in the display window.
 */
public class MemeLikeCommand extends Command {

    public static final String COMMAND_WORD = "like";

    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD + ": like a meme by index.";

    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION
            + " Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_LIKE_MEME_SUCCESS = "Liked Meme: %1$s";
    public static final String MESSAGE_NOT_LIKED = "Please specify the index of the meme that you like.";

    private final Index index;

    /**
     * @param index of the meme in the filtered meme list to like
     */
    public MemeLikeCommand(Index index) {
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

        model.incrementMemeLikeCount(memeToLike);

        CommandResult result = new CommandResult(String.format(MESSAGE_LIKE_MEME_SUCCESS, memeToLike));
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
        if (!(other instanceof MemeLikeCommand)) {
            return false;
        }

        // state check
        MemeLikeCommand e = (MemeLikeCommand) other;
        return index.equals(e.index);
    }

}
