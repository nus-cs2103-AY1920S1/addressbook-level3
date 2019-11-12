package seedu.eatme.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.eatme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_RATING;

import java.util.OptionalDouble;
import java.util.OptionalInt;

import seedu.eatme.commons.core.index.Index;
import seedu.eatme.logic.commands.EditReviewCommand;
import seedu.eatme.logic.commands.EditReviewCommand.EditReviewDescriptor;
import seedu.eatme.logic.parser.exceptions.ParseException;

/**
 * Parses edit review commands input by user.
 */
public class EditReviewCommandParser implements Parser<EditReviewCommand> {

    /**
     * Parses the given string of {@code args} in the context of
     * edit review command and returns a new editcommand object.
     */
    public EditReviewCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_COST, PREFIX_RATING, PREFIX_DATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditReviewCommand.MESSAGE_USAGE), pe);
        }

        EditReviewCommand.EditReviewDescriptor editReviewDescriptor = new EditReviewDescriptor();

        if (argumentMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editReviewDescriptor.setDescription(ParserUtil
                    .parseReviewDescription(argumentMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (argumentMultimap.getValue(PREFIX_COST).isPresent()) {
            editReviewDescriptor.setCost(OptionalDouble.of(ParserUtil
                    .parseReviewCost(argumentMultimap.getValue(PREFIX_COST).get())));
        }

        if (argumentMultimap.getValue(PREFIX_RATING).isPresent()) {
            editReviewDescriptor.setRating(OptionalInt.of(ParserUtil
                    .parseReviewRating(argumentMultimap.getValue(PREFIX_RATING).get())));
        }

        if (argumentMultimap.getValue(PREFIX_DATE).isPresent()) {
            try {
                editReviewDescriptor.setDate(ParserUtil
                        .parseReviewDate(argumentMultimap.getValue(PREFIX_DATE).get()));
            } catch (java.text.ParseException e) {
                throw new ParseException(String.format(EditReviewCommand.MESSAGE_USAGE));
            }
        }

        if (!editReviewDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditReviewCommand.MESSAGE_NOT_EDITED);
        }

        return new EditReviewCommand(index, editReviewDescriptor);
    }

}
