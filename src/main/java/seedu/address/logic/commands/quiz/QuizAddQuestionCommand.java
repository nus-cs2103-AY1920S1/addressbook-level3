package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.quiz.QuizBank;

/**
 * Represents an add question command, specific to a quiz.
 */
public class QuizAddQuestionCommand extends QuizCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an existing question to an existing quiz\n"
            + "Parameters:\n"
            + "add quizID/ [QUIZ_ID]\n"
            + "Example: quizID/ CS2103T Finals\n\n"
            + "questionNumber/ [QUESTION_NUMBER]\n"
            + "Example: questionNumber/ 3 (Specifies the third question in the question bank)\n\n"
            + "quizQuestionNumber/ [QUIZ_QUESTION_NUMBER]\n"
            + "Example: quizQuestionNumber/ 2 (Specifies the question number in the quiz to add to)";

    private final String quizId;
    private final int questionNumber;
    private final int quizQuestionNumber;

    /**
     * Creates a QuizAddQuestionCommand instance with the appropriate attributes.
     * @param quizId The identifier of the quiz.
     * @param questionNumber The question number in the question bank to be added.
     * @param quizQuestionNumber The quiz question number to be added to.
     */
    public QuizAddQuestionCommand(String quizId, int questionNumber, int quizQuestionNumber) {
        requireAllNonNull(quizId, questionNumber, quizQuestionNumber);
        this.quizId = quizId;
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
        requireNonNull(model);
        if (quizId.equals("") || quizId == null) {
            throw new CommandException(BLANK_QUIZ_ID);
        }
        if (questionNumber == -1) {
            throw new CommandException(INVALID_QUESTION_NUMBERS);
        }
        if (quizQuestionNumber == -1) {
            throw new CommandException(INVALID_QUESTION_NUMBERS);
        }

        if (!model.checkQuizExists(quizId)) {
            throw new CommandException(String.format(QUIZ_DOES_NOT_EXIST, quizId));
        } else {
            boolean isSuccess = model.addQuizQuestion(quizId, questionNumber, quizQuestionNumber);
            if (isSuccess) {
                QuizBank.setCurrentlyQueriedQuiz(quizId);
                return new CommandResult(generateSuccessMessage(), CommandResultType.SHOW_QUIZ_ALL);
            } else {
                throw new CommandException(REPEATED_QUESTION);
            }
        }
    }

    /**
     * Generates a command execution success message.
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage() {
        return "Added question: " + questionNumber + " to quiz: " + quizId + ".";
    }

    /**
     * Generates a command execution failure message.
     * @return The String representation of a failure message.
     */
    private String generateFailureMessage() {
        return "That is a repeated question!";
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
        return this.quizId.equals(e.quizId);
    }

}
