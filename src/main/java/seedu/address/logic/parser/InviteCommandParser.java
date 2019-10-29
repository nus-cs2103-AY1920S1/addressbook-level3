package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.InviteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new InviteCommand object
 */
public class InviteCommandParser implements Parser<InviteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the InviteCommand
     * and returns an InviteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public InviteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PARTICIPANT);

        if (!arePrefixesPresent(argMultimap, PREFIX_PARTICIPANT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, InviteCommand.MESSAGE_USAGE));
        }

        List<String> participants = argMultimap.getAllValues(PREFIX_PARTICIPANT);

        return new InviteCommand(participants);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
