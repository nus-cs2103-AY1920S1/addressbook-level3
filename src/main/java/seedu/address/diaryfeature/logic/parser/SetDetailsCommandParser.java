package seedu.address.diaryfeature.logic.parser;

import static seedu.address.diaryfeature.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.diaryfeature.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.diaryfeature.logic.commands.SetDetailsCommand;
import seedu.address.diaryfeature.logic.parser.exceptions.EmptyArgumentException;
import seedu.address.diaryfeature.model.Details;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;

public class SetDetailsCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) throws EmptyArgumentException  {
         final String SETDETAILS_USAGE = "In particular, input your setDetails command like this: \n" +
                "setDetails user/username password/pass Eg: user/myname password/mypassword." +
                " \n Note that the input has to be something, it can't be empty!";

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if ((!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)
                || (!argMultimap.getPreamble().isEmpty()))) {
            throw new EmptyArgumentException(SetDetailsCommand.COMMAND_WORD,SETDETAILS_USAGE);
        }

        Details details;

        try {
            String user = ParserUtil.parseStringArgs(argMultimap.getValue(PREFIX_USERNAME).get(),SetDetailsCommand.COMMAND_WORD);
            String pass = ParserUtil.parseStringArgs(argMultimap.getValue(PREFIX_PASSWORD).get(),SetDetailsCommand.COMMAND_WORD);
            details = new Details(user, pass);


        } catch (EmptyArgumentException err) {

            throw new EmptyArgumentException(SetDetailsCommand.COMMAND_WORD,SETDETAILS_USAGE);

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
