package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.List;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class MarkAttendanceCommandParser implements Parser<MarkAttendanceCommand> {

    public MarkAttendanceCommand parse(String args) throws ParseException {
        try {
            List<Index> indexList = ParserUtil.parseMultipleIndex(args);
            return new MarkAttendanceCommand(indexList);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE), pe);
        }
    }
}
