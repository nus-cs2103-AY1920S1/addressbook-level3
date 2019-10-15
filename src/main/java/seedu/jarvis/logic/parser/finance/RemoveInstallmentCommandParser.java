package seedu.jarvis.logic.parser.finance;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.finance.RemoveInstallmentCommand;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveInstallmentCommand object
 */
public class RemoveInstallmentCommandParser implements Parser<RemoveInstallmentCommand> {

    @Override
    public RemoveInstallmentCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RemoveInstallmentCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveInstallmentCommand.MESSAGE_USAGE, pe));
        }
    }
}
