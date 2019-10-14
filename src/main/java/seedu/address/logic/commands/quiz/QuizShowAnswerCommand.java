package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.question.Answer;

public class QuizShowAnswerCommand extends Command {
    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_SUCCESS = "The answer is: %1$s";

    private final int index;

    public QuizShowAnswerCommand(int index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Answer answer = model.getQuizAnswer(index);

        return new CommandResult(String.format(MESSAGE_SUCCESS, answer.toString()));
    }
}
