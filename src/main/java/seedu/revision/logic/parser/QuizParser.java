package seedu.revision.logic.parser;

import seedu.revision.logic.commands.Command;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Answerable;

/**
 * Represents a QuizParserManager that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface QuizParser<T extends Command> {

    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    T parse(String userInput, Answerable currentAnswerable) throws ParseException;
}
