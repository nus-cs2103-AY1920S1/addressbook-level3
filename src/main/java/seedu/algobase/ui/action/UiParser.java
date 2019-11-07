package seedu.algobase.ui.action;

import seedu.algobase.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser that is able to parse user input into a {@code UiAction} of type {@code T}.
 */
public interface UiParser<T extends UiAction> {

    /**
     * Parses {@code uiAction} into a command and returns it.
     * @throws ParseException if {@code uiAction} does not conform the expected format
     */
    T parse(UiActionDetails uiActionDetails) throws ParseException;
}
