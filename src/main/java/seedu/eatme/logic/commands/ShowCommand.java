package seedu.eatme.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;

import seedu.eatme.commons.core.Messages;
import seedu.eatme.commons.core.index.Index;
import seedu.eatme.logic.commands.exceptions.CommandException;
import seedu.eatme.model.Model;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.model.eatery.Review;

/**
 * Shows a eatery identified using it's displayed index from the eatery list.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the eatery identified by the index number used in the displayed eatery list.\n"
            + "Parameters: [index] (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SHOW_EATERY_SUCCESS = "Eatery successfully shown: %s";

    private final Index targetIndex;

    public ShowCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Eatery> lastShownList = model.isMainMode() ? model.getFilteredEateryList() : model.getFilteredTodoList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
        }

        Eatery eateryToShow = lastShownList.get(targetIndex.getZeroBased());

        List<Review> reviews = eateryToShow.getReviews();
        Collections.sort(reviews);
        model.updateActiveReviews(reviews);
        model.setActiveEatery(eateryToShow);

        return new CommandResult(
                String.format(MESSAGE_SHOW_EATERY_SUCCESS, eateryToShow.getName().fullName), eateryToShow);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowCommand // instanceof handles nulls
                && targetIndex.equals(((ShowCommand) other).targetIndex)); // state check
    }
}
