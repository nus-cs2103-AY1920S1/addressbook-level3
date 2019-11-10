package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.eatery.Review;

/**
 * Deletes an existing eatery's review.
 */
public class DeleteReviewCommand extends Command {

    public static final String COMMAND_WORD = "deletereview";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the review identified by the index number used in the displayed review list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_REVIEW_SUCCESS = "Deleted Review: %1$s";

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
        return new CommandResult(String.format(MESSAGE_DELETE_REVIEW_SUCCESS, reviewToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteReviewCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteReviewCommand) other).targetIndex)); // state check
    }
}
