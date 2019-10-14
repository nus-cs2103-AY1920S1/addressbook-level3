package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangeAppCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.events.Timing;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;

/**
 * Parses input arguments and creates a new ChangeAppCommand object
 */
public class ChangeAppCommandParser {
    public ChangeAppCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_START, PREFIX_END);

        if (!arePrefixesPresent(argMultimap, PREFIX_START, PREFIX_END) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeAppCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            String startString = argMultimap.getValue(PREFIX_START).get();
            String endString = argMultimap.getValue(PREFIX_END).get();
            Timing timing = ParserUtil.parseTiming(startString, endString);
            return new ChangeAppCommand(index, timing);
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeAppCommand.MESSAGE_USAGE), e);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }
}
