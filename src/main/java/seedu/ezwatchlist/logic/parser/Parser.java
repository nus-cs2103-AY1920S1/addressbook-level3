package seedu.ezwatchlist.logic.parser;

import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.logic.commands.Command;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface Parser<T extends Command> {

    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    T parse(String userInput, String currentPanel) throws ParseException, OnlineConnectionException;
}
