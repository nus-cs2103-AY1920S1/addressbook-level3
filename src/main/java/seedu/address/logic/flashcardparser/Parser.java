package seedu.address.logic.flashcardparser;

import seedu.address.logic.flashcardcommands.Command;

/**
 * interface for all the parsers.
 * @param <T>
 */
public interface Parser<T extends Command> {

    T parse(String userInput);
}

