package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_NUMBER;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AutoAllocateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AutoAllocateCommand object
 */
public class AutoAllocateCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AutoAllocateCommand
     * and returns an AutoAllocateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AutoAllocateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMPLOYEE_NUMBER);

        Integer manpowerCount;
        Index eventIndex;

        try {
            eventIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
            manpowerCount = Integer.valueOf(argMultimap.getValue(PREFIX_EMPLOYEE_NUMBER).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AutoAllocateCommand.MESSAGE_USAGE), pe);
        }

        return new AutoAllocateCommand(eventIndex, manpowerCount);
    }
}
