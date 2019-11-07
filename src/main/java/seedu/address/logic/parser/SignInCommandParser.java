package seedu.address.logic.parser;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.SignInCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.OwnerAccount;
import seedu.address.model.person.Email;

import java.util.logging.Logger;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

public class SignInCommandParser implements Parser<SignInCommand> {

    public SignInCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ACCOUNT, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_ACCOUNT, PREFIX_PASSWORD) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SignInCommand.MESSAGE_USAGE));
        }

        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_ACCOUNT).get());
        String emailName = email.value.split("@")[1];
        String emailProvider = emailName.split("\\.")[0];
        int len = emailName.split("\\.").length;

        final Logger logger = LogsCenter.getLogger(SignInCommandParser.class);

        if (!emailProvider.equals("gmail")) {
            throw new ParseException("Please use a gmail account to sign in. Our application only supports a gmail account for email-ing purposes");
        }
        if (len < 2) {
            throw new ParseException("Please use a valid gmail account to sign in. Our application only supports a gmail account for email-ing purposes");
        }

        String password = argMultimap.getValue(PREFIX_PASSWORD).get();

        OwnerAccount ownerAccount = new OwnerAccount(email, password);
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
