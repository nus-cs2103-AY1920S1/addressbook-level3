package mams.logic.parser;

import static mams.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static mams.logic.parser.CliSyntax.PREFIX_APPEAL;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE;
import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.stream.Stream;

import mams.commons.core.index.Index;
import mams.logic.commands.ViewCommand;
import mams.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_APPEAL, PREFIX_MODULE, PREFIX_STUDENT);

        if (areAllPrefixesAbsent(argMultimap, PREFIX_APPEAL, PREFIX_MODULE, PREFIX_STUDENT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        try {
            Index appealIndex = null;
            Index moduleIndex = null;
            Index studentIndex = null;

            if (argMultimap.getValue(PREFIX_APPEAL).isPresent()) {
                appealIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_APPEAL).get());
            }

            if (argMultimap.getValue(PREFIX_MODULE).isPresent()) {
                moduleIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_MODULE).get());
            }

            if (argMultimap.getValue(PREFIX_STUDENT).isPresent()) {
                studentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT).get());
            }
            return new ViewCommand(appealIndex, moduleIndex, studentIndex);
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

    }

    /**
     * Returns true if all of the prefixes are not present in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean areAllPrefixesAbsent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isEmpty());
    }
}
