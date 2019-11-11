package seedu.eatme.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.eatme.commons.core.Messages;
import seedu.eatme.commons.core.index.Index;
import seedu.eatme.logic.commands.exceptions.CommandException;
import seedu.eatme.model.Model;
import seedu.eatme.model.eatery.Review;

/**
 * Deletes an existing eatery's review.
 */
public class DeleteReviewCommand extends Command {

    public static final String COMMAND_WORD = "deletereview";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the review identified by the index number used in the displayed review list.\n"
            + "Parameters: [index] (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_REVIEW_SUCCESS = "Review successfully deleted";

    private final Index targetIndex;

    public DeleteReviewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Review> lastShownList = model.getActiveReviews();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REVIEW_DISPLAYED_INDEX);
        }

        Review reviewToDelete = lastShownList.get(targetIndex.getZeroBased());
        lastShownList.remove(targetIndex.getZeroBased());
        model.getActiveEatery().setReviews(lastShownList);
        return new CommandResult(MESSAGE_DELETE_REVIEW_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteReviewCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteReviewCommand) other).targetIndex)); // state check
    }
}
