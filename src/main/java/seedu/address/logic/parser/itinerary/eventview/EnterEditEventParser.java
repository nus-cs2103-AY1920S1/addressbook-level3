package seedu.address.logic.parser.itinerary.eventview;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.itinerary.events.EnterEditEventCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input index and returns {@link EnterEditEventCommand} by index.
 */
public class EnterEditEventParser implements Parser<EnterEditEventCommand> {
    @Override
    public EnterEditEventCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new EnterEditEventCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EnterEditEventCommand.MESSAGE_USAGE), pe);
        }
    }
}
