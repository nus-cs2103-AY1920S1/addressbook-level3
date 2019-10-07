package seedu.address.reimbursement.logic.parser;

import static seedu.address.reimbursement.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.reimbursement.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.stream.Stream;

import seedu.address.person.model.Model;
import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.commands.DeadlineCommand;
import seedu.address.reimbursement.logic.exception.ParseException;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.reimbursement.ui.ReimbursementMessages;

public class DeadlineCommandParser implements GeneralParser<DeadlineCommand> {
    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

    public DeadlineCommand parse(String args, Model personModel)
            throws Exception {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATETIME, PREFIX_PERSON);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATETIME, PREFIX_PERSON)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(ReimbursementMessages.MESSAGE_INVALID_DEADLINECOMMAND_FORMAT);
        }

        String datetime = argMultimap.getValue(PREFIX_DATETIME).get();
        try {

            Person person = personModel.getPersonByName(argMultimap.getValue(PREFIX_PERSON).get());
            DeadlineCommand deadlineCommand = new DeadlineCommand(person, datetime);
            return deadlineCommand;
        } catch (Exception e) {
            throw new NoSuchPersonReimbursementException();
        }
    }
}
