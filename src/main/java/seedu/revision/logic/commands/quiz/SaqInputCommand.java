package seedu.revision.logic.commands.quiz;

import static java.util.Objects.requireNonNull;
import static seedu.revision.ui.bar.Timer.TIMER_UP_SKIP_QUESTION;

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
    public static final String MESSAGE_INVALID_INPUT_TIMER_UP = "The input cannot be " + TIMER_UP_SKIP_QUESTION;
    private final String saqInput;
    private final Answerable currentAnswerable;

    public SaqInputCommand(String saqInput, Answerable currentAnswerable) {
        this.saqInput = saqInput;
        this.currentAnswerable = currentAnswerable;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (saqInput.equalsIgnoreCase(TIMER_UP_SKIP_QUESTION)) {
            return new CommandResultBuilder().withCorrect(false).build();
        }

        Answer selectedAnswer = new Answer(saqInput);

        requireNonNull(selectedAnswer);
        boolean result = currentAnswerable.isAnswerCorrect(selectedAnswer);

        return new CommandResultBuilder().withCorrect(result).build();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SaqInputCommand // instanceof handles nulls
                && saqInput.equalsIgnoreCase(((SaqInputCommand) other).saqInput));
    }

}
