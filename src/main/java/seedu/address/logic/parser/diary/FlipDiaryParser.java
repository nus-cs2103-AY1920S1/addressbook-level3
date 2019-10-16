package seedu.address.logic.parser.diary;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.diary.FlipDiaryCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * {@link Parser} that parses user input to return a {@link FlipDiaryCommand}.
 * The input must contain a valid integer index of the day to flip to.
 */
public class FlipDiaryParser implements Parser<FlipDiaryCommand> {
    @Override
    public FlipDiaryCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        Index index;

        try {
            index = ParserUtil.parseIndex(userInput.trim());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FlipDiaryCommand.MESSAGE_USAGE), pe);
        }

        return new FlipDiaryCommand(index);
    }
}
