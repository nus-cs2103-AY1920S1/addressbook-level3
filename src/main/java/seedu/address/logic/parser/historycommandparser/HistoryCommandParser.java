package seedu.address.logic.parser.historycommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.historycommand.HistoryCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the input arguments and returns a HistoryCommand object.
 */
public class HistoryCommandParser implements Parser<HistoryCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of an HistoryCommand
     * and returns an appropriate HistoryCommand tailored to the arguments for execution.
     * As of v1.4, the HistoryCommand takes no parameters, so this class is mainly used to
     * ensure that no additional parameters are supplied to the HistoryCommand.
     * @param args the arguments to be parsed by HistoryCommandParser
     * @return an HistoryCommand specific to the input arguments for execution.
     * @throws ParseException if the args do not conform to the expected format for the HistoryCommand.
     */
    public HistoryCommand parse(String args) throws ParseException {
        args = args.trim();

        if (args.equals("")) {
            return new HistoryCommand();
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    HistoryCommand.MESSAGE_USAGE));
        }
    }
}
