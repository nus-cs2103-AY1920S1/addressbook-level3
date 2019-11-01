package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GenReportCommand;
import seedu.address.logic.commands.GenReportsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

//@@author bernicechio

/**
 * Parses input workerID and creates a new GenReportCommand object.
 */
public class GenReportsCommandParser implements Parser<GenReportsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GenReportCommand
     * and returns a GenReportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenReportsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            ArgumentMultimap argMultimap = tokenize(args);
            Name sign = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            return new GenReportsCommand(sign.toString());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenReportCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Tokenizes the input string for genReport command
     */
    private static ArgumentMultimap tokenize(String args) {
        return ArgumentTokenizer.tokenize(args, PREFIX_NAME);
    }
}
