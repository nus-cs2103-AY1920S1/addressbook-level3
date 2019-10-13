package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.stream.Stream;

import seedu.address.logic.commands.RegisterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.Email;
import seedu.address.model.borrower.Name;
import seedu.address.model.borrower.Phone;

/**
 * Parses input arguments and creates a new RegisterCommand object
 */
public class RegisterCommandParser implements Parser<RegisterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RegisterCommand
     * and returns an RegisterCommand object for execution.
     *
     * @return RegisterCommand object.
     * @throws seedu.address.logic.parser.exceptions.ParseException if the user input does not conform the
     * expected format
     */
    public RegisterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());;
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Borrower borrower = new Borrower(name, phone, email);
        return new RegisterCommand(borrower);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
