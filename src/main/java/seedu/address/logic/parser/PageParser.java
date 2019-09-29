package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a categorical Parser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface PageParser<T extends Command> {

    /**
     * Parses {@code command} into an exact, non-categorical {@code Parser}
     * which then parses the arguments into a {@code Command} and returns the command.
     * @throws ParseException if {@code command} does not conform any of the expected formats
     */
    T parse(String command, String arguments) throws ParseException;
}
