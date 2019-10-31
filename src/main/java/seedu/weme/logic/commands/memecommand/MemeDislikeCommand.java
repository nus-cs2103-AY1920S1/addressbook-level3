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
public class MemeDislikeCommand extends Command {

    public static final String COMMAND_WORD = "dislike";

    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD + ": dislike a meme by index.";
    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION
            + " Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_DISLIKE_MEME_SUCCESS = "Disliked Meme: %1$s";
    public static final String MESSAGE_NOT_LIKED = "Please specify the index of the meme that you dislike.";

    private final Index index;

    /**
     * @param index of the meme in the filtered meme list to like
     */
    public MemeDislikeCommand(Index index) {
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

        Meme memeToDislike = lastShownList.get(index.getZeroBased());

        model.decrementMemeLikeCount(memeToDislike);

        CommandResult result = new CommandResult(String.format(MESSAGE_DISLIKE_MEME_SUCCESS, memeToDislike));
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
        if (!(other instanceof MemeDislikeCommand)) {
            return false;
        }

        // state check
        MemeDislikeCommand e = (MemeDislikeCommand) other;
        return index.equals(e.index);
    }

}
