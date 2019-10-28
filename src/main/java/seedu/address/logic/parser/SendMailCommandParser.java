package seedu.address.logic.parser;

import seedu.address.logic.commands.SendMailCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECIPIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;

public class SendMailCommandParser implements Parser<SendMailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SendMailCommand
     * and returns a SendMailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SendMailCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_RECIPIENT, PREFIX_SUBJECT, PREFIX_MESSAGE);

        if (!arePrefixesPresent(argMultimap, PREFIX_RECIPIENT, PREFIX_SUBJECT, PREFIX_MESSAGE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SendMailCommand.MESSAGE_USAGE));
        }

        String recipient = argMultimap.getValue(PREFIX_RECIPIENT).get();
        String subject = argMultimap.getValue(PREFIX_SUBJECT).get();
        String message = argMultimap.getValue(PREFIX_MESSAGE).get();

        return new SendMailCommand(recipient, subject, message);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
