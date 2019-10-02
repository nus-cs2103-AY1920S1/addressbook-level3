package seedu.address.logic.parser.trips;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.trips.EnterEditTripCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class EnterEditTripParser implements Parser<EnterEditTripCommand> {
    @Override
    public EnterEditTripCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new EnterEditTripCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EnterEditTripCommand.MESSAGE_USAGE), pe);
        }
    }
}
