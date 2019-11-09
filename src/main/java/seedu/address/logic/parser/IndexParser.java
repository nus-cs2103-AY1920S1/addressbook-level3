package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INDEX;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a parser that can parse user input into an Index (Integer).
 * Between [0, 2^31)
 */
public class IndexParser implements Parser<Integer> {

    @Override
    public Integer parse(String userInput) throws ParseException {
        int i;
        try {
            i = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }

        if (i < 0) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }

        return i;
    }
}
