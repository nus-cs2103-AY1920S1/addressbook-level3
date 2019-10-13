package seedu.address.cashier.logic;

import seedu.address.cashier.commands.Command;
import seedu.address.cashier.logic.exception.ParseException;

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
