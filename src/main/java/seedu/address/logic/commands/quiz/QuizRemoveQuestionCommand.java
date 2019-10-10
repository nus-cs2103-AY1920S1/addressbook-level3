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
            + "quizID/ [QUIZ_ID]\n"
            + "Example: quizID/ CS2103T Finals\n\n"
            + "quizQuestionNumber/ [QUIZ_QUESTION_NUMBER]\n"
            + "Example: quizQuestionNumber/ 2 (Specifies the question number in the quiz to remove)";

    private final String quizID;
    private final int quizQuestionNumber;

    /**
     * Creates a QuizRemoveQuestionCommand instance with the appropriate attributes.
     * @param quizID The identifier of the quiz.
     * @param quizQuestionNumber The question number of the quiz to be removed.
     */
    public QuizRemoveQuestionCommand(String quizID, int quizQuestionNumber) {
        this.quizID = quizID;
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
        model.removeQuizQuestion(quizID, quizQuestionNumber);
        return new CommandResult(generateSuccessMessage());
    }

    /**
     * Generates a command execution success message.
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage() {
        return "Removed question: " + quizQuestionNumber + " from quiz: " + quizID;
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
        return this.quizID.equals(e.quizID);
    }

}
