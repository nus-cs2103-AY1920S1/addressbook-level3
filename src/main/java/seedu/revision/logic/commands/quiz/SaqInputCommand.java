package seedu.revision.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import seedu.revision.logic.commands.Command;

import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.commands.main.CommandResultBuilder;
import seedu.revision.model.Model;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;

/**
 * User inputs that answer the SAQ questions in the quiz session.
 */
public class SaqInputCommand extends Command {
    public static final String MESSAGE_USAGE = "Input cannot be blank and must start with letter or number";
    private final String saqInput;
    private final Answerable currentAnswerable;

    public SaqInputCommand(String saqInput, Answerable currentAnswerable) {
        this.saqInput = saqInput;
        this.currentAnswerable = currentAnswerable;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Answer selectedAnswer = new Answer(saqInput);

        requireNonNull(selectedAnswer);
        boolean result = currentAnswerable.isAnswerCorrect(selectedAnswer);

        return new CommandResultBuilder().withCorrect(result).build();
    }
}
