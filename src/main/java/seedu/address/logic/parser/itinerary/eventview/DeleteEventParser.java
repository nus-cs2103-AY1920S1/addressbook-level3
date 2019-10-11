package seedu.address.logic.parser.itinerary.eventview;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.itinerary.events.DeleteEventCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class DeleteEventParser implements Parser<DeleteEventCommand> {
    @Override
    public DeleteEventCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteEventCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE), pe);
        }
    }
}
