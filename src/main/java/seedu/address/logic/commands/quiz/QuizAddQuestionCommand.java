package seedu.address.logic.commands.quiz;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.util.HashMap;

/**
 * Represents an add question command, specific to a quiz.
 */
public class QuizAddQuestionCommand extends QuizCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an existing question to an existing quiz\n"
            + "Parameters:\n"
            + "quizID/ [QUIZ_ID]\n"
            + "Example: quizID/ CS2103T Finals\n\n"
            + "questionNumber/ [QUESTION_NUMBER]\n"
            + "Example: questionNumber/ 3 (Specifies the third question in the question bank)\n\n"
            + "quizQuestionNumber/ [QUIZ_QUESTION_NUMBER]\n"
            + "Example: quizQuestionNumber/ 2 (Specifies the question number in the quiz to add to)";

    private final String quizID;
    private final int questionNumber;
    private final int quizQuestionNumber;

    /**
     * Creates a QuizAddQuestionCommand instance with the appropriate attributes.
     * @param quizID The identifier of the quiz.
     * @param questionNumber The question number in the question bank to be added.
     * @param quizQuestionNumber The quiz question number to be added to.
     */
    public QuizAddQuestionCommand(String quizID, int questionNumber, int quizQuestionNumber) {
        this.quizID = quizID;
        this.questionNumber = questionNumber;
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
        model.addQuizQuestion(quizID, questionNumber, quizQuestionNumber);
        return new CommandResult(generateSuccessMessage());
    }

    /**
     * Generates a command execution success message.
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage() {
        return "Added question: " + questionNumber + " to quiz: " + quizQuestionNumber;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof QuizAddQuestionCommand)) {
            return false;
        }

        // state check
        QuizAddQuestionCommand e = (QuizAddQuestionCommand) other;
        return this.quizID.equals(e.quizID);
    }

}
