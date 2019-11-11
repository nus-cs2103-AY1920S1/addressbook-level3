package seedu.eatme.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eatme.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_RATING;

import java.util.List;

import seedu.eatme.commons.core.Messages;
import seedu.eatme.commons.core.index.Index;
import seedu.eatme.logic.commands.exceptions.CommandException;
import seedu.eatme.model.Model;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.model.eatery.Review;

/**
 * Adds review to an existing eatery in the eatery list
 */
public class ReviewCommand extends Command {

    public static final String COMMAND_WORD = "review";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a review to the eatery identified "
            + "by the index number used in the last eatery listing. "
            + "Parameters: [index] (must be a positive integer) "
            + PREFIX_DESCRIPTION + " [description] "
            + PREFIX_COST + " [cost per person] "
            + PREFIX_RATING + " [rating]"
            + PREFIX_DATE + " [date] (in dd/MM/YYYY format)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + " Great noodles! "
            + PREFIX_COST + " 6.20 "
            + PREFIX_RATING + " 4 "
            + PREFIX_DATE + " 14/02/2020";

    public static final String MESSAGE_ADD_REVIEW_SUCCESS = "Review successfully added";
    public static final String MESSAGE_WRONG_MODE = "Adding of reviews is unavailable in todo mode!";

    private final Index index;
    private final Review review;

    /**
     * @param index of the eatery to which review is added.
     * @param review to be added to the eatery.
     */
    public ReviewCommand (Index index, Review review) {
        requireAllNonNull(index, review);

        this.index = index;
        this.review = review;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Eatery> lastShownList = model.getFilteredEateryList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
        }

        if (model.isMainMode()) {
            Eatery eateryToAddReview = lastShownList.get(index.getZeroBased());

            eateryToAddReview.addReview(review);
            model.updateFilteredEateryList(Model.PREDICATE_SHOW_ALL_EATERIES);
        } else {
            throw new CommandException(MESSAGE_WRONG_MODE);
        }

        return new CommandResult(MESSAGE_ADD_REVIEW_SUCCESS);
    }

}
