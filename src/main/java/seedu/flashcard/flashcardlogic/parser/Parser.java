package seedu.flashcard.flashcardlogic.parser;

import seedu.flashcard.flashcardlogic.commands.Command;

/**
 * interface for all the parsers.
 * @param <T>
 */
public interface Parser<T extends Command> {

    T parse(String userInput);
}

