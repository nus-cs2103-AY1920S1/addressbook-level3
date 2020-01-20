package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.List;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class AssignTaskCommandParser implements Parser<AssignTaskCommand> {
    public AssignTaskCommand parse(String args) throws ParseException {
        try {
            List<Index> indexList = ParserUtil.parseMultipleIndex(args);
            return new AssignTaskCommand(indexList);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE), pe);
        }
    }
}
