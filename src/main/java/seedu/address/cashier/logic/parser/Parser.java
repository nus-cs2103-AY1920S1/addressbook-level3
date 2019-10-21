package seedu.address.cashier.logic.parser;

import seedu.address.cashier.logic.commands.Command;
import seedu.address.cashier.logic.parser.exception.ParseException;

/**
 * Represents a GeneralParser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface Parser<T extends Command> {

    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    T parse(String userInput);
}
