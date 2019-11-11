package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.quiz.QuizBank;

/**
 * Represents an quiz create command (automatic).
 */
public class QuizCreateAutomaticallyCommand extends QuizCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a quiz automatically.\n"
            + "Parameters:\n"
            + "auto/\n"
            + "Example: auto/\n\n"
            + "quizID/ [QUIZ_ID]\n"
            + "Example: quizID/ CS2103T Finals\n\n"
            + "numQuestions/ [NUM_QUESTIONS]\n"
            + "Example: numQuestions/ 10 (The quiz will have 10 questions.)\n\n"
            + "type/ [TYPE: open, mcq, all]\n"
            + "Example: type/ open (Specifies the question type for the quiz)\n\n";

    private final String quizId;
    private final int numQuestions;
    private final String type;

    /**
     * Creates a QuizCreateAutomaticallyCommand instance with the appropriate attributes.
     * @param quizId The identifier of the quiz.
     * @param numQuestions The number of questions to add to the quiz.
     * @param type The type of questions to add to the quiz.
     */
    public QuizCreateAutomaticallyCommand(String quizId, int numQuestions, String type) {
        this.quizId = quizId;
        this.numQuestions = numQuestions;
        this.type = type;
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
        if (numQuestions == -1) {
            throw new CommandException(INVALID_NUM_QUESTIONS);
        }
        if (type.equals("")) {
            throw new CommandException(BLANK_TYPE);
        }
        if (!type.equals("mcq") && !type.equals("open") && !type.equals("all")) {
            throw new CommandException(INVALID_TYPE);
        }

        if (model.checkQuizExists(quizId)) {
            throw new CommandException(String.format(QUIZ_ALREADY_EXISTS, quizId));
        }

        boolean isSuccess = model.createQuizAutomatically(quizId, numQuestions, type);
        if (isSuccess) {
            QuizBank.setCurrentlyQueriedQuiz(quizId);
            return new CommandResult(generateSuccessMessage(), CommandResultType.SHOW_QUIZ_ALL);
        } else {
            throw new CommandException(NOT_ENOUGH_QUESTIONS_IN_STORAGE);
        }
    }

    /**
     * Generates a command execution success message.
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage() {
        if (numQuestions == 1) {
            return "Created quiz: " + quizId + " with " + numQuestions + " question.";
        } else {
            return "Created quiz: " + quizId + " with " + numQuestions + " questions.";
        }
    }

    /**
     * Generates a command execution failure message.
     * @return The String representation of a failure message.
     */
    private String generateFailureMessage() {
        return "You do not have enough questions in the storage! Add more questions and try again.";
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof QuizCreateAutomaticallyCommand)) {
            return false;
        }

        // state check
        QuizCreateAutomaticallyCommand e = (QuizCreateAutomaticallyCommand) other;
        return this.quizId.equals(e.quizId);
    }

}
