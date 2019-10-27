package seedu.address.logic.commands.quiz;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.quiz.QuizBank;

/**
 * Represents a list answers command, specific to a quiz.
 */
public class QuizListAnswersCommand extends QuizCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets the answers for a quiz\n"
            + "Parameters:\n"
            + "showAnswers quizID/ [QUIZ_ID]\n"
            + "Example: quizID/ CS2103T Finals\n\n";

    private final String quizId;

    /**
     * Creates a QuizListAnswersCommand instance with the appropriate attributes.
     * @param quizId The identifier of the quiz.
     */
    public QuizListAnswersCommand(String quizId) {
        this.quizId = quizId;
    }

    /**
     * Executes the user command.
     * @param model {@code Model} which the command should operate on.
     * @return The result of the command.
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.checkQuizExists(quizId)) {
            return new CommandResult(String.format(QUIZ_DOES_NOT_EXIST, quizId));
        }
        QuizBank.setCurrentlyQueriedQuiz(quizId);
        return new CommandResult("Starting quiz view.", CommandResultType.SHOW_QUIZ_ANSWERS);
    }

    /**
     * Generates a command execution success message.
     * @param message The relevant message from the model.
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage(String message) {
        return "These are the questions & answers for "
                    + message;
    }

}
