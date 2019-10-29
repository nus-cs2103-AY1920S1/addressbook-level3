package seedu.address.logic.parser.itinerary.eventview;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.itinerary.events.ShowEventDetailsCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input index and returns {@link ShowEventDetailsParser} by index.
 */
public class ShowEventDetailsParser implements Parser<ShowEventDetailsCommand> {
    @Override
    public ShowEventDetailsCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ShowEventDetailsCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowEventDetailsCommand.MESSAGE_USAGE), pe);
        }
    }
}
