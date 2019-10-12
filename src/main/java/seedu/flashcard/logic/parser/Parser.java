package seedu.flashcard.logic.parser;

import seedu.flashcard.logic.commands.Command;

/**
 * interface for all the parsers.
 * @param <T>
 */
public interface Parser<T extends Command> {

    T parse(String userInput);
}

