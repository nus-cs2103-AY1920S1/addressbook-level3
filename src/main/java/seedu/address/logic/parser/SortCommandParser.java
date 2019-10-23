package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ARGUMENT_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEQUENCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.stream.Stream;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.SortSequence;
import seedu.address.model.person.SortType;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException, IllegalArgumentException {
        try {
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_SEQUENCE);

            if (!arePrefixesPresent(argMultimap, PREFIX_TYPE, PREFIX_SEQUENCE)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
            }
            SortType type;
            type = ParserUtil.parseSortType(argMultimap.getValue(PREFIX_TYPE).get().toLowerCase());
            SortSequence seq = ParserUtil.parseSortSequence(argMultimap.getValue(PREFIX_SEQUENCE).get().toLowerCase());
            return new SortCommand(type, seq);
        } catch (IllegalArgumentException iea) {
            throw new IllegalArgumentException(String.format(MESSAGE_INVALID_ARGUMENT_FORMAT,
                                                               SortType.MESSAGE_CONSTRAINTS));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), pe);
        }
    }


    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

