package seedu.address.logic.commands.quiz;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.util.HashMap;

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

    private final String quizID;
    private final int numQuestions;
    private final String type;

    /**
     * Creates a QuizCreateAutomaticallyCommand instance with the appropriate attributes.
     * @param quizID The identifier of the quiz.
     * @param numQuestions The number of questions to add to the quiz.
     * @param type The type of questions to add to the quiz.
     */
    public QuizCreateAutomaticallyCommand(String quizID, int numQuestions, String type) {
        this.quizID = quizID;
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
        model.createQuizAutomatically(quizID, numQuestions, type);
        return new CommandResult(generateSuccessMessage());
    }

    /**
     * Generates a command execution success message.
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage() {
        return "Created quiz: " + quizID + " with " + numQuestions + " questions.";
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
        return this.quizID.equals(e.quizID);
    }

}
