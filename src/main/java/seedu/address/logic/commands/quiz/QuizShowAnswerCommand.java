package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.question.Answer;

/**
 * Shows an answer for the question in quiz.
 */
public class QuizShowAnswerCommand extends Command {
    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_SUCCESS = "The answer is: %1$s";
    public static final String EMPTY_QUESTION = "There is no more question!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            Answer answer = model.showQuizAnswer();
            return new CommandResult(String.format(MESSAGE_SUCCESS, answer.toString()), 4);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(EMPTY_QUESTION);
        }
    }
}
