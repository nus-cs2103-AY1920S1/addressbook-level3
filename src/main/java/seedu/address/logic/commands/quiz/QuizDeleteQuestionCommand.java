package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.quiz.QuizBank;

/**
 * Represents a delete question command, specific to a quiz.
 */
public class QuizDeleteQuestionCommand extends QuizCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes an existing question from an existing quiz\n"
            + "Parameters:\n"
            + "delete quizID/ [QUIZ_ID]\n"
            + "Example: quizID/ CS2103T Finals\n\n"
            + "quizQuestionNumber/ [QUIZ_QUESTION_NUMBER]\n"
            + "Example: quizQuestionNumber/ 2 (Specifies the question number in the quiz to remove)";

    private final String quizId;
    private final int quizQuestionNumber;

    /**
     * Creates a QuizDeleteQuestionCommand instance with the appropriate attributes.
     * @param quizId The identifier of the quiz.
     * @param quizQuestionNumber The question number of the quiz to be removed.
     */
    public QuizDeleteQuestionCommand(String quizId, int quizQuestionNumber) {
        requireAllNonNull(quizId, quizQuestionNumber);
        this.quizId = quizId;
        this.quizQuestionNumber = quizQuestionNumber;
    }

    /**
     * Executes the user command.
     * @param model {@code Model} which the command should operate on.
     * @return The result of the command.
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (quizId.equals("") || quizId == null) {
            throw new CommandException(BLANK_QUIZ_ID);
        }
        if (quizQuestionNumber == -1) {
            throw new CommandException(INVALID_QUESTION_NUMBERS);
        }

        if (!model.checkQuizExists(quizId)) {
            throw new CommandException(String.format(QUIZ_DOES_NOT_EXIST, quizId));
        } else {
            boolean isSuccess = model.deleteQuizQuestion(quizId, quizQuestionNumber);
            if (isSuccess) {
                QuizBank.setCurrentlyQueriedQuiz(quizId);
                return new CommandResult(generateSuccessMessage(), CommandResultType.SHOW_QUIZ_ALL);
            } else {
                throw new CommandException(INVALID_QUESTION_INDEX);
            }
        }
    }

    /**
     * Generates a command execution success message.
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage() {
        return "Removed question: " + quizQuestionNumber + " from quiz: " + quizId;
    }

    /**
     * Generates a command execution failure message.
     * @return The String representation of a failure message.
     */
    private String generateFailureMessage() {
        return "You are deleting a question index which does not exist!";
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof QuizDeleteQuestionCommand)) {
            return false;
        }

        // state check
        QuizDeleteQuestionCommand e = (QuizDeleteQuestionCommand) other;
        return this.quizId.equals(e.quizId);
    }

}
