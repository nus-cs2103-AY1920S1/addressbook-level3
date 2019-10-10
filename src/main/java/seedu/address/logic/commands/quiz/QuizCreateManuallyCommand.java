package seedu.address.logic.commands.quiz;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents an quiz create command (manual).
 */
public class QuizCreateManuallyCommand extends QuizCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a quiz manually.\n"
            + "Parameters:\n"
            + "manual/\n"
            + "Example: manual/\n\n"
            + "quizID/ [QUIZ_ID]\n"
            + "Example: quizID/ CS2103T Finals\n\n"
            + "questionNumber/ [QUESTION_NUMBER]\n"
            + "Example: questionNumber/ 1 3 5 (Adds questions 1, 3 and 5 in the question bank to the quiz)\n\n";

    private final String quizID;
    private final ArrayList<Integer> questionNumbers;

    /**
     * Creates a QuizCreateManuallyCommand instance with the appropriate attributes.
     * @param fields The fields to be edited, including quizID and questions.
     */
    public QuizCreateManuallyCommand(HashMap<String, String> fields) {
        String quizID = fields.get("quizID");
        String questionNumbersKey = fields.get("questionNumbers");
        String[] splitQuestionNumbers = questionNumbersKey.split(" ");

        ArrayList<Integer> questionNumbers = new ArrayList<>();
        for(String s : splitQuestionNumbers) {
            questionNumbers.add(Integer.parseInt(s));
        }

        this.quizID = quizID;
        this.questionNumbers = questionNumbers;
    }

    /**
     * Executes the user command.
     * @param model {@code Model} which the command should operate on.
     * @return The result of the command.
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.createQuizManually(quizID, questionNumbers);
        return new CommandResult(generateSuccessMessage());
    }

    /**
     * Generates a command execution success message.
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage() {
        int numQuestions = questionNumbers.size();
        if(numQuestions == 1) {
            return "Created quiz: " + quizID + " with " + numQuestions + " question.";
        } else {
            return "Created quiz: " + quizID + " with " + questionNumbers.size() + " questions.";
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof QuizCreateManuallyCommand)) {
            return false;
        }

        // state check
        QuizCreateManuallyCommand e = (QuizCreateManuallyCommand) other;
        return this.quizID.equals(e.quizID);
    }

}