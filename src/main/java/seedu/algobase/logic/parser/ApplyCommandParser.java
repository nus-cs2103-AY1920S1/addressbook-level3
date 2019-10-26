package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.ApplyCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ApplyCommand object
 */
public class ApplyCommandParser implements Parser<ApplyCommand> {

    @Override
    public ApplyCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ApplyCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ApplyCommand.MESSAGE_USAGE), pe);
        }
    }
}
