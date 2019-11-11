package seedu.address.reimbursement.logic.parser;

import seedu.address.reimbursement.logic.commands.Command;
import seedu.address.reimbursement.logic.parser.exception.ParseException;

/**
 * Interface for all the Sort-related Parsers.
 */
public interface IndependentCommandParser<T extends Command> {
    T parse(String userInput) throws ParseException;
}
