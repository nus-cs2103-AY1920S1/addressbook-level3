package seedu.address.reimbursement.logic.parser;

import seedu.address.person.model.Model;
import seedu.address.reimbursement.commands.Command;

/**
 * Parser for all other commands.
 */
public interface GeneralParser<T extends Command> {
    T parse(String userInput, Model personModel) throws Exception;
}
