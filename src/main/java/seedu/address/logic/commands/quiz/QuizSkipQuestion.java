package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Skips current quiz question.
 */
public class QuizSkipQuestion extends Command {
    public static final String COMMAND_WORD = "skip";
    public static final String MESSAGE_SUCCESS = "Next question is displayed";

    public static final String EMPTY_QUESTION = "There is no more question!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.removeOneQuizQuestion();
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(EMPTY_QUESTION);
        }
        return new CommandResult(MESSAGE_SUCCESS, 4);
    }
}
