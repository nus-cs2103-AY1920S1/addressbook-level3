package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTIVITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandSubType;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ViewCommand} object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of a {@code ViewCommand}
     * and returns a {@code ViewCommand} object for execution.
     * @throws ParseException if the user input does not conform to the expected format,
     *                        or has missing compulsory arguments.
     */
    public ViewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CONTACT, PREFIX_ACTIVITY);

        if (!onePrefixPresent(argMultimap, PREFIX_CONTACT, PREFIX_ACTIVITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        CommandSubType subType;
        String indexString;

        if (argMultimap.getValue(PREFIX_ACTIVITY).isPresent()) {
            subType = CommandSubType.ACTIVITY;
            indexString = argMultimap.getValue(PREFIX_ACTIVITY).get();
        } else {
            assert argMultimap.getValue(PREFIX_CONTACT).isPresent();
            subType = CommandSubType.CONTACT;
            indexString = argMultimap.getValue(PREFIX_CONTACT).get();
        }

        try {
            Index index = ParserUtil.parseIndex(indexString);
            return new ViewCommand(subType, index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), pe);
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
