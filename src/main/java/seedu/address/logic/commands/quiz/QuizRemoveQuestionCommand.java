package seedu.address.logic.commands.quiz;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a remove question command, specific to a quiz.
 */
public class QuizRemoveQuestionCommand extends QuizCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes an existing question from an existing quiz\n"
            + "Parameters:\n"
            + "delete quizID/ [QUIZ_ID]\n"
            + "Example: quizID/ CS2103T Finals\n\n"
            + "quizQuestionNumber/ [QUIZ_QUESTION_NUMBER]\n"
            + "Example: quizQuestionNumber/ 2 (Specifies the question number in the quiz to remove)";

    private final String quizId;
    private final int quizQuestionNumber;

    /**
     * Creates a QuizRemoveQuestionCommand instance with the appropriate attributes.
     * @param quizId The identifier of the quiz.
     * @param quizQuestionNumber The question number of the quiz to be removed.
     */
    public QuizRemoveQuestionCommand(String quizId, int quizQuestionNumber) {
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
        if (!model.checkQuizExists(quizId)) {
            return new CommandResult(String.format(QUIZ_DOES_NOT_EXIST, quizId));
        } else {
            boolean isSuccess = model.removeQuizQuestion(quizId, quizQuestionNumber);
            if (isSuccess) {
                return new CommandResult(generateSuccessMessage());
            } else {
                return new CommandResult(generateFailureMessage());
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
        return "You are deleting a question which does not exist! Congratulations!";
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof QuizRemoveQuestionCommand)) {
            return false;
        }

        // state check
        QuizRemoveQuestionCommand e = (QuizRemoveQuestionCommand) other;
        return this.quizId.equals(e.quizId);
    }

}
