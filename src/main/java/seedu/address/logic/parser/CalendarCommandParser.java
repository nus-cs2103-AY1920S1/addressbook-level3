package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.CalendarCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.date.AthletickDate;

public class CalendarCommandParser implements Parser<CalendarCommand> {

    @Override
    public CalendarCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.length() == 6 || trimmedArgs.length() == 8) {
            AthletickDate date = ParserUtil.parseDate(trimmedArgs);
            return new CalendarCommand(date);
        } else {
            System.out.println(trimmedArgs.length());
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarCommand.MESSAGE_USAGE));
        }
    }
}
