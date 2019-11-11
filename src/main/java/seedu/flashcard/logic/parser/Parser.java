package seedu.flashcard.logic.parser;

import seedu.flashcard.logic.commands.Command;
import seedu.flashcard.logic.parser.exceptions.ParseException;

/**
 * interface for all the parsers.
 * @param <T>
 */
public interface Parser<T extends Command> {

    T parse(String userInput) throws ParseException;

}

