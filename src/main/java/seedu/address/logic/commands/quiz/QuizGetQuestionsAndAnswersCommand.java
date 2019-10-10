package seedu.address.logic.commands.quiz;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents an add question command, specific to a quiz.
 */
public class QuizGetQuestionsAndAnswersCommand extends QuizCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets the questions & answers for a quiz\n"
            + "Parameters:\n"
            + "quizID/ [QUIZ_ID]\n"
            + "Example: quizID/ CS2103T Finals\n\n";

    private final String quizID;

    /**
     * Creates a QuizGetQuestionsAndAnswersCommand instance with the appropriate attributes.
     * @param quizID The identifier of the quiz.
     */
    public QuizGetQuestionsAndAnswersCommand(String quizID) {
        this.quizID = quizID;
    }

    /**
     * Executes the user command.
     * @param model {@code Model} which the command should operate on.
     * @return The result of the command.
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(generateSuccessMessage(model.getQuestionsAndAnswers(quizID)));
    }

    /**
     * Generates a command execution success message.
     * @param message The relevant message from the model.
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage(String message) {
        System.out.println(message);
        return "These are the questions & answers for " + quizID + " "
                    + message;
    }

}