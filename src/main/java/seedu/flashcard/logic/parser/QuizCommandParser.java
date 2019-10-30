package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.flashcard.commons.core.index.Index;
import seedu.flashcard.logic.commands.QuizCommand;
import seedu.flashcard.logic.parser.exceptions.ParseException;

/**
 * Parses input argument to generate a {@code QuizCommand}
 */
public class QuizCommandParser implements Parser<QuizCommand> {

    /**
     * Parses the given context into a view command
     * @param args the input from the user
     * @return new view command
     */
    @Override
    public QuizCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new QuizCommand(index);
        } catch (ParseException e) {
            FlashcardListParser.setQuizMode(false);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT + QuizCommand.MESSAGE_USAGE), e);
        }
    }
}
