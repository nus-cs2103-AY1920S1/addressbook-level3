package seedu.address.logic.parser;

import seedu.address.logic.commands.BroadcastMailCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;

public class BroadcastMailCommandParser implements Parser<BroadcastMailCommand> {

    @Override
    public BroadcastMailCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT, PREFIX_MESSAGE);

        if (!arePrefixesPresent(argMultimap, PREFIX_SUBJECT, PREFIX_MESSAGE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BroadcastMailCommand.MESSAGE_USAGE));
        }

        String subject = argMultimap.getValue(PREFIX_SUBJECT).get();
        String message = argMultimap.getValue(PREFIX_MESSAGE).get();

        return new BroadcastMailCommand(subject, message);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
