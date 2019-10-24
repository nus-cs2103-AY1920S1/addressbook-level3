package seedu.address.cashier.logic.parser;

import seedu.address.cashier.logic.commands.Command;
import seedu.address.cashier.logic.parser.exception.ParseException;
import seedu.address.cashier.model.Model;

/**
 * Represents a Parser that is able to parse user input into a {@code Command}.
 */
public interface Parser {

    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    Command parse(String args, Model modelManager, Model personModel) throws Exception;
}
