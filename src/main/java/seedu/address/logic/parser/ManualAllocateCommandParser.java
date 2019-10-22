package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_NUMBER;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ManualAllocateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ManualAllocateCommand object
 */
public class ManualAllocateCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ManualAllocateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMPLOYEE_NUMBER);

        Index index;
        Index eventIndex;

        try {
            eventIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_EMPLOYEE_NUMBER).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ManualAllocateCommand.MESSAGE_USAGE), pe);
        }

        return new ManualAllocateCommand(eventIndex, index);
    }
}
