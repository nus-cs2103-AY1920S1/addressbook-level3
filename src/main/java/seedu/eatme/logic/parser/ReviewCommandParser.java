package seedu.eatme.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.eatme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_RATING;

import java.util.Date;

import seedu.eatme.commons.core.index.Index;
import seedu.eatme.logic.commands.ReviewCommand;
import seedu.eatme.logic.parser.exceptions.ParseException;
import seedu.eatme.model.eatery.Review;

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
                PREFIX_COST, PREFIX_RATING, PREFIX_DATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());

            String reviewDescription = ParserUtil
                    .parseReviewDescription(argumentMultimap.getValue(PREFIX_DESCRIPTION).get());
            double reviewCost = ParserUtil.parseReviewCost(argumentMultimap.getValue(PREFIX_COST).get());
            int reviewRating = ParserUtil.parseReviewRating(argumentMultimap.getValue(PREFIX_RATING).get());
            Date reviewDate = ParserUtil.parseReviewDate(argumentMultimap.getValue(PREFIX_DATE).get());

            Review review = new Review(reviewDescription, reviewCost, reviewRating, reviewDate);

            return new ReviewCommand(index, review);
        } catch (ParseException | java.text.ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReviewCommand.MESSAGE_USAGE), pe);
        }
    }
}

