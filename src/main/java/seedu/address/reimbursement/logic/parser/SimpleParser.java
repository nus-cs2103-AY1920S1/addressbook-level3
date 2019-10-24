package seedu.address.reimbursement.logic.parser;

import seedu.address.reimbursement.logic.commands.Command;

/**
 * Interface for all the Sort-related Parsers.
 */
public interface SimpleParser<T extends Command> {
    T parse(String userInput) throws Exception;
}
