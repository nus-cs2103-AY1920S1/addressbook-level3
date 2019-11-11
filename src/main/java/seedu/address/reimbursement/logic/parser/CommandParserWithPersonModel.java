package seedu.address.reimbursement.logic.parser;

import seedu.address.person.model.Model;
import seedu.address.reimbursement.logic.commands.Command;
import seedu.address.reimbursement.logic.parser.exception.ParseException;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;

/**
 * Parser for all other commands.
 */
public interface CommandParserWithPersonModel<T extends Command> {
    T parse(String userInput, Model personModel) throws ParseException, NoSuchPersonReimbursementException;
}
