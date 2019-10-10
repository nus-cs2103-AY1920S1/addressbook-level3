package seedu.address.logic.parser.itinerary.dayview;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.itinerary.days.EnterDayCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class EnterDayParser implements Parser<EnterDayCommand> {
    @Override
    public EnterDayCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new EnterDayCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EnterDayCommand.MESSAGE_USAGE), pe);
        }
    }

}
