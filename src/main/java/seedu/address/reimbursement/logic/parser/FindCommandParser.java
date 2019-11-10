package seedu.address.reimbursement.logic.parser;

import static seedu.address.util.CliSyntax.PREFIX_PERSON;

import java.util.stream.Stream;

import seedu.address.person.model.Model;
import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.logic.commands.FindCommand;
import seedu.address.reimbursement.logic.parser.exception.ParseException;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.reimbursement.ui.ReimbursementMessages;
import seedu.address.util.ArgumentMultimap;
import seedu.address.util.ArgumentTokenizer;
import seedu.address.util.Prefix;

/**
 * Parser for find command.
 */
public class FindCommandParser implements CommandParserWithPersonModel<FindCommand> {
    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

    /**
     * Finds a command by person.
     *
     * @param args        the arguments from the user.
     * @param personModel the person to search by.
     * @return a command representing the user's desired action.
     * @throws Exception if the command syntax is invalid.
     */
    public FindCommand parse(String args, Model personModel)
            throws ParseException, NoSuchPersonReimbursementException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERSON);

        if (!arePrefixesPresent(argMultimap, PREFIX_PERSON)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(ReimbursementMessages.MESSAGE_INVALID_FINDCOMMAND_FORMAT);
        }

        Person person = personModel.getPersonByName(argMultimap.getValue(PREFIX_PERSON).get());
        FindCommand deadlineCommand = new FindCommand(person);
        return deadlineCommand;
    }
}
