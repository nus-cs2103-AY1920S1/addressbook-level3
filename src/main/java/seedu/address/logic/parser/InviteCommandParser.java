package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ActivityCommand;

import seedu.address.logic.commands.InviteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class InviteCommandParser implements Parser<InviteCommand> {

    public InviteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PARTICIPANT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, InviteCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_PARTICIPANT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, InviteCommand.MESSAGE_USAGE));
        }

        List<String> participants = argMultimap.getAllValues(PREFIX_PARTICIPANT);

        return new InviteCommand(index, participants);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
