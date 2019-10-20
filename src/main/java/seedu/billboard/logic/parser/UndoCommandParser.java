package seedu.billboard.logic.parser;

import seedu.billboard.logic.commands.UndoCommand;
import seedu.billboard.logic.parser.exceptions.ParseException;

/**
 * Undo previous edit command.
 */
public class UndoCommandParser implements Parser<UndoCommand> {
    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param userInput
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public UndoCommand parse(String userInput) throws ParseException {
        if (!userInput.isBlank()) {
            throw new ParseException("undo command does not take in parameter");
        }
        return new UndoCommand();
    }
}
