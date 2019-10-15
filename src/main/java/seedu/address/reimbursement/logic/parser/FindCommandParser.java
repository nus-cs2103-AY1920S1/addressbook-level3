package seedu.address.reimbursement.logic.parser;

import static seedu.address.reimbursement.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.stream.Stream;

import seedu.address.person.model.Model;
import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.commands.FindCommand;
import seedu.address.reimbursement.logic.exception.ParseException;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.reimbursement.ui.ReimbursementMessages;

/**
 * Parser for find command.
 */
public class FindCommandParser implements GeneralParser<FindCommand> {
    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

    /**
     * Finds a command by person.
     * @param args the arguments from the user.
     * @param personModel the person to search by.
     * @return a command representing the user's desired action.
     * @throws Exception if the command syntax is invalid.
     */
    public FindCommand parse(String args, Model personModel)
            throws Exception {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERSON);

        if (!arePrefixesPresent(argMultimap, PREFIX_PERSON)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(ReimbursementMessages.MESSAGE_INVALID_FINDCOMMAND_FORMAT);
        }

        try {
            Person person = personModel.getPersonByName(argMultimap.getValue(PREFIX_PERSON).get());
            FindCommand deadlineCommand = new FindCommand(person);
            return deadlineCommand;
        } catch (Exception e) {
            throw new NoSuchPersonReimbursementException();
        }
    }
}
