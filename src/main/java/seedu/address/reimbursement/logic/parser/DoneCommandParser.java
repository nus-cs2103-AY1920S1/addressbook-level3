package seedu.address.reimbursement.logic.parser;

import static seedu.address.reimbursement.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.stream.Stream;

import seedu.address.person.model.Model;
import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.commands.DoneCommand;
import seedu.address.reimbursement.logic.exception.ParseException;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.reimbursement.ui.ReimbursementMessages;

/**
 * Parser for done command.
 */
public class DoneCommandParser implements GeneralParser<DoneCommand> {
    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

    /**
     * Marks a reimbursement done.
     * @param args the arguments from the user.
     * @param personModel the person to search by.
     * @return a command representing the user's desired action.
     * @throws Exception if the command syntax is invalid.
     */
    public DoneCommand parse(String args, Model personModel)
            throws Exception {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERSON);

        if (!arePrefixesPresent(argMultimap, PREFIX_PERSON)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(ReimbursementMessages.MESSAGE_INVALID_DONECOMMAND_FORMAT);
        }

        try {
            Person person = personModel.getPersonByName(argMultimap.getValue(PREFIX_PERSON).get());
            DoneCommand doneCommand = new DoneCommand(person);
            return doneCommand;
        } catch (Exception e) {
            throw new NoSuchPersonReimbursementException();
        }
    }
}
