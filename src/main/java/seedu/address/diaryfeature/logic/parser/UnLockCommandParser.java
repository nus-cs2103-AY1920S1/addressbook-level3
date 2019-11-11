package seedu.address.diaryfeature.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.diaryfeature.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.diaryfeature.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.diaryfeature.logic.commands.UnLockCommand;
import seedu.address.diaryfeature.logic.parser.exceptions.DetailParseException;
import seedu.address.diaryfeature.logic.parser.exceptions.EmptyArgumentException;
import seedu.address.diaryfeature.model.details.Details;
import seedu.address.diaryfeature.model.details.Password;
import seedu.address.diaryfeature.model.details.Username;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a UnLockCommand object
 */
public class UnLockCommandParser {
    public static final String UNLOCK_USAGE = "In particular, input your unlock command like this: \n" +
            "unlock TARGET user/USERNAME password/PASSWORD \nEG: unlock 1 user/myusername password/mypassword " +
            "\nNote that the input has to be a number and more than or equal to 1";

    /**
     * Parses the given {@code String} of arguments in the context of theUnLockCommand
     * and returns a UnLockCommand object for execution.    *
     *
     * @param args is the user input
     * @return an UnlockCommand to execute
     * @throws EmptyArgumentException if the user input does not conform the expected format
     * @throws DetailParseException   if the Detail is invalid
     */
    public Command parse(String args) throws EmptyArgumentException, DetailParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);
        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)) {
            throw new EmptyArgumentException(UnLockCommand.COMMAND_WORD, UNLOCK_USAGE);
        }
        Index index;
        Username username;
        Password password;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new EmptyArgumentException(UnLockCommand.COMMAND_WORD, UNLOCK_USAGE);
        }
        username = new Username(ParserUtil.parseDetail(argMultimap.getValue(PREFIX_USERNAME).get(),
                UnLockCommand.COMMAND_WORD));
        password = new Password(ParserUtil.parseDetail(argMultimap.getValue(PREFIX_PASSWORD).get(),
                UnLockCommand.COMMAND_WORD));

        return new UnLockCommand(index, new Details(username, password));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
