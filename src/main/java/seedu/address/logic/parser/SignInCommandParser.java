package seedu.address.logic.parser;

import seedu.address.logic.commands.SignInCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.OwnerAccount;
import seedu.address.model.person.Email;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACCOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

public class SignInCommandParser implements Parser<SignInCommand> {

    public SignInCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ACCOUNT, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_ACCOUNT, PREFIX_PASSWORD) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SignInCommand.MESSAGE_USAGE));
        }

        String account = argMultimap.getValue(PREFIX_ACCOUNT).get();
        String password = argMultimap.getValue(PREFIX_PASSWORD).get();

        OwnerAccount ownerAccount = new OwnerAccount(new Email(account), password);
        return new SignInCommand(ownerAccount);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
