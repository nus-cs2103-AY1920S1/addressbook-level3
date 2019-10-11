package seedu.address.logic.parser.itinerary.dayview;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.itinerary.days.EnterEditDayCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class EnterEditDayParser implements Parser<EnterEditDayCommand> {
    @Override
    public EnterEditDayCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new EnterEditDayCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EnterEditDayCommand.MESSAGE_USAGE), pe);
        }
    }
}
