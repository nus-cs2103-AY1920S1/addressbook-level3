package seedu.address.diaryfeature.logic.parser;

import static seedu.address.diaryfeature.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.diaryfeature.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.diaryfeature.logic.commands.SetDetailsCommand;
import seedu.address.diaryfeature.logic.parser.exceptions.DetailParseException;
import seedu.address.diaryfeature.logic.parser.exceptions.EmptyArgumentException;
import seedu.address.diaryfeature.model.details.Details;
import seedu.address.diaryfeature.model.details.Password;
import seedu.address.diaryfeature.model.details.Username;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;

/**
 * Parses input arguments and creates a SetDetailsCommand object
 */
public class SetDetailsCommandParser {
    private final String SET_DETAILS_USAGE = "In particular, input your setDetails command like this: \n \n" +
            "setDetails user/USERNAME password/PASSWORD | EG: user/myname password/mypassword." +
            " \nNote: all details have to be at least 8 characters and only alphanumeric";

    /**
     * Parses the given {@code String} of arguments in the context of the SetDetails
     * and returns an SetDetails object for execution.
     */
    public Command parse(String args) throws EmptyArgumentException, DetailParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if ((!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)
                || (!argMultimap.getPreamble().isEmpty()))) {
            throw new EmptyArgumentException(SetDetailsCommand.COMMAND_WORD, SET_DETAILS_USAGE);
        }
        Details details;
        try {
            String user = ParserUtil.parseDetail(argMultimap.getValue(PREFIX_USERNAME).get(), SetDetailsCommand.COMMAND_WORD);
            String pass = ParserUtil.parseDetail(argMultimap.getValue(PREFIX_PASSWORD).get(), SetDetailsCommand.COMMAND_WORD);
            details = new Details(new Username(user), new Password(pass));

        } catch (EmptyArgumentException err) {
            throw new EmptyArgumentException(SetDetailsCommand.COMMAND_WORD, SET_DETAILS_USAGE);
        }
        return new SetDetailsCommand(details);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
