package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.question.Answer;

public class QuizCheckAnswer extends Command {
    public static final String ANSWER_CORRECT = "The answer is correct!";
    public static final String ANSWER_WRONG = "The answer is wrong!";

    private final int index;
    private final Answer answer;

    public QuizCheckAnswer(int index, Answer answer) {
        this.index= index;
        this.answer = answer;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (model.checkQuizAnswer(index, answer)) {
            return new CommandResult(ANSWER_CORRECT);
        } else {
            return new CommandResult(ANSWER_WRONG);
        }
    }
}
