package seedu.address.logic.parser.mark;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.mark.AddMarkCommand;
import seedu.address.logic.commands.mark.MarkCommand;
import seedu.address.logic.commands.mark.RemoveMarkCommand;
import seedu.address.logic.parser.*;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;


/**
 * Represents a parser for mark commands.
 */
public class MarkCommandParser implements Parser<MarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddMarkCommand/RemoveMarkCommand.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, PREFIX_INDEX, PREFIX_UNMARK);

        if (argMultimap.getValue(PREFIX_UNMARK).isPresent()) {
            return unmarkCommand(argMultimap);
        } else {
            return markCommand(argMultimap);
        }
    }

    private AddMarkCommand markCommand(ArgumentMultimap argMultimap) throws ParseException {
        Index index;
        try {
            String indexStr = argMultimap.getSingleValue(CliSyntax.PREFIX_INDEX)
                    .orElseThrow(() -> new ParseException(""));
            index = ParserUtil.parseIndex(indexStr);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMarkCommand.MESSAGE_USAGE, pe.getMessage()),
                    pe);
        }
        return new AddMarkCommand(index);
    }

    private RemoveMarkCommand unmarkCommand(ArgumentMultimap argMultimap) throws ParseException {
        Index index;
        try {
            String indexStr = argMultimap.getSingleValue(CliSyntax.PREFIX_INDEX)
                    .orElseThrow(() -> new ParseException(""));
            index = ParserUtil.parseIndex(indexStr);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveMarkCommand.MESSAGE_USAGE, pe.getMessage()),
                    pe);
        }
        return new RemoveMarkCommand(index);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
                                              Prefix... prefixes) {
        return Stream.of(prefixes)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
