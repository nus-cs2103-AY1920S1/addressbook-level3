package seedu.billboard.logic.parser;

import seedu.billboard.logic.commands.RedoCommand;
import seedu.billboard.logic.parser.exceptions.ParseException;

/**
 * Ensure there is no input argument and creates a new UndoCommand object
 */
public class RedoCommandParser implements Parser<RedoCommand> {
    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param userInput
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public RedoCommand parse(String userInput) throws ParseException {
        if (!userInput.isBlank()) {
            throw new ParseException("redo command does not take in parameter");
        }
        return new RedoCommand();
    }
}
