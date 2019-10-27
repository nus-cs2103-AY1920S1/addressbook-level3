package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTIVITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;

import java.util.stream.Stream;

import seedu.address.logic.CommandSubType;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ListCommand} object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of a {@code ListCommand}
     * and returns a {@code ListCommand} object for execution.
     * @throws ParseException if the user input does not conform to the expected format,
     *                        or has missing compulsory arguments.
     */
    public ListCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CONTACT, PREFIX_ACTIVITY);

        if (!onePrefixPresent(argMultimap, PREFIX_CONTACT, PREFIX_ACTIVITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_ACTIVITY).isPresent()) {
            return new ListCommand(CommandSubType.ACTIVITY);
        } else {
            return new ListCommand(CommandSubType.CONTACT);
        }
    }

    /**
     * Returns true if exactly one the prefixes contains a non-empty {@code Optional} value in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean onePrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
                .filter(prefix -> argumentMultimap.getValue(prefix).isPresent())
                .count() == 1;
    }
}
