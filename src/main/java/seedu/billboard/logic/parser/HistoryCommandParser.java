package seedu.billboard.logic.parser;

import seedu.billboard.logic.commands.HistoryCommand;
import seedu.billboard.logic.parser.exceptions.ParseException;

/**
 * Insure there is no input argument and creates a new HistoryCommand object.
 *
 */
public class HistoryCommandParser implements Parser<HistoryCommand> {
    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param userInput
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public HistoryCommand parse(String userInput) throws ParseException {
        if (!userInput.isBlank()) {
            throw new ParseException("history command does not take in parameter");
        }
        return new HistoryCommand();
    }
}
