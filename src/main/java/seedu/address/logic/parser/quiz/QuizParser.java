package seedu.address.logic.parser.quiz;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.quiz.QuitQuizModeCommand;
import seedu.address.logic.commands.quiz.QuizShowAnswerCommand;
import seedu.address.logic.commands.quiz.QuizSkipQuestion;

/**
 * Parses user input in the quiz mode.
 */
public class QuizParser {
    /**
     * Parses user input into command for execution or answer of questions to check correctness.
     *
     * @param userInput full user input string
     * @return the command or answer based on the user input
     */
    public Command parseCommand(String userInput) {
        final String commandWord = userInput.trim();
        switch (commandWord) {

        case QuitQuizModeCommand.COMMAND_WORD:
            return new QuitQuizModeCommand();

        case QuizShowAnswerCommand.COMMAND_WORD:
            return new QuizShowAnswerCommand();

        case QuizSkipQuestion.COMMAND_WORD:
            return new QuizSkipQuestion();

        default:
            return new QuizAnswerParser().parse(userInput);
        }
    }
}
