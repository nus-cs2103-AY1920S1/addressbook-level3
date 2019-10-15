package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GenReportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author bernicechio
/**
 * Parses input bodyID and creates a new GenReportCommand object.
 */
public class GenReportCommandParser implements Parser<GenReportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GenReportCommand
     * and returns a GenReportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenReportCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            if (args.trim().length() < 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenReportCommand.MESSAGE_USAGE));
            }
            String index = args.trim().substring(1);
            if (index.matches("[0-9]+")) {
                Index genReportBodyId = Index.fromZeroBased(Integer.parseInt(index));
                return new GenReportCommand(genReportBodyId);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenReportCommand.MESSAGE_USAGE));
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenReportCommand.MESSAGE_USAGE));
        }
    }
}
