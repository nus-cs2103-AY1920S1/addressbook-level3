package seedu.address.reimbursement.logic.parser;

import seedu.address.reimbursement.commands.Command;

/**
 * Interface for all the Sort-related Parsers.
 */
public interface SortParser<T extends Command> {
    T parse(String userInput) throws Exception;
}
