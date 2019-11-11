package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.isNumeric;

import java.util.ArrayList;
import java.util.HashMap;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.quiz.QuizBank;

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

    private final String quizId;
    private final ArrayList<Integer> questionNumbers;

    /**
     * Creates a QuizCreateManuallyCommand instance with the appropriate attributes.
     * @param fields The fields to be edited, including quizID and questions.
     */
    public QuizCreateManuallyCommand(HashMap<String, String> fields) {
        String quizId = fields.get("quizID");
        String questionNumbersKey = fields.get("questionNumbers");
        String[] splitQuestionNumbers = questionNumbersKey.split(" ");

        ArrayList<Integer> questionNumbers = new ArrayList<>();
        for (String s : splitQuestionNumbers) {
            if (isNumeric(s)) {
                int convertedInt = Integer.parseInt(s);
                if (!questionNumbers.contains(convertedInt)) {
                    questionNumbers.add(convertedInt);
                }
            }
        }

        this.quizId = quizId;
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
        requireNonNull(model);
        if (quizId.equals("") || quizId == null) {
            throw new CommandException(BLANK_QUIZ_ID);
        }
        if (questionNumbers.size() == 0) {
            throw new CommandException(INVALID_QUESTION_NUMBERS);
        }
        if (hasInvalidNumber(questionNumbers)) {
            throw new CommandException(INVALID_QUESTION_NUMBERS);
        }

        if (model.checkQuizExists(quizId)) {
            throw new CommandException(String.format(QUIZ_ALREADY_EXISTS, quizId));
        }

        boolean isSuccess = model.createQuizManually(quizId, questionNumbers);
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
        int numQuestions = questionNumbers.size();
        if (numQuestions == 1) {
            return "Created quiz: " + quizId + " with " + numQuestions + " question.";
        } else {
            return "Created quiz: " + quizId + " with " + questionNumbers.size() + " questions.";
        }
    }

    /**
     * Generates a command execution failure message.
     * @return The String representation of a failure message.
     */
    private String generateFailureMessage() {
        return "You have entered one or more invalid question indexes.";
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
        return this.quizId.equals(e.quizId);
    }

    /**
     * Checks whether there are any invalid numbers.
     * @param questionNumbers The question numbers to be checked.
     * @return True if there are invalid numbers, else false.
     */
    private boolean hasInvalidNumber(ArrayList<Integer> questionNumbers) {
        boolean hasInvalid = false;
        for (Integer i : questionNumbers) {
            if (i <= 0) {
                hasInvalid = true;
                break;
            }
        }
        return hasInvalid;
    }

}
