package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.RateQuestionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.flashcard.Rating;

//@@author keiteo
/**
 * This class parses input arguments and creates a new RateQuestionCommand object.
 */
public class RateQuestionCommandParser implements Parser<RateQuestionCommand> {

    private final KeyboardFlashCardsParser keyboardFlashCardsParser;

    RateQuestionCommandParser(KeyboardFlashCardsParser keyboardFlashCardsParser) {
        requireNonNull(keyboardFlashCardsParser);
        this.keyboardFlashCardsParser = keyboardFlashCardsParser;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the RateQuestionCommand
     * and returns a RateQuestionCommand object for execution.
     */
    public RateQuestionCommand parse(String args) throws ParseException {
        String rating = args.trim().split("\\s+")[0];
        if (!rating.equals(Rating.EASY)
                && !rating.equals(Rating.GOOD)
                && !rating.equals(Rating.HARD)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateQuestionCommand.MESSAGE_USAGE));
        }
        return new RateQuestionCommand(keyboardFlashCardsParser, new Rating(rating));
    }
}
