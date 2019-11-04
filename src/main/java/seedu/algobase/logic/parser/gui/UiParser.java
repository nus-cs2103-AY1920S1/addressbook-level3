package seedu.algobase.logic.parser.gui;

import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.gui.UiAction;

/**
 * Represents a Parser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface UiParser<T extends Command> {

    /**
     * Parses {@code uiAction} into a command and returns it.
     * @throws ParseException if {@code uiAction} does not conform the expected format
     */
    T parse(UiAction uiAction) throws ParseException;
}
