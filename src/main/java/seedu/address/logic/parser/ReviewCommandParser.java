package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ReviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.eatery.Review;

/**
 * Parses review type of commands.
 */
public class ReviewCommandParser implements Parser<ReviewCommand> {

    /**
     * Parses input and returns a review command.
     */
    public ReviewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION,
                PREFIX_COST, PREFIX_RATING);

        Index index;

        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());

            String reviewDescription = ParserUtil
                    .parseReviewDescription(argumentMultimap.getValue(PREFIX_DESCRIPTION).get());
            double reviewCost = ParserUtil.parseReviewCost(argumentMultimap.getValue(PREFIX_COST).get());
            int reviewRating = ParserUtil.parseReviewRating(argumentMultimap.getValue(PREFIX_RATING).get());

            Review review = new Review(reviewDescription, reviewCost, reviewRating);

            return new ReviewCommand(index, review);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReviewCommand.MESSAGE_USAGE), pe);
        }

    }
}

