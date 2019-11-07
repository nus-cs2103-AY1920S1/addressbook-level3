package seedu.revision.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.commands.main.CommandResultBuilder;
import seedu.revision.model.Model;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;

/**
 * User inputs that answer the MCQ questions in the quiz session.
 */
public class McqInputCommand extends Command {

    public static final String MESSAGE_USAGE = "Input can only be A, B, C, or D (case insensitive)";
    private static final Logger logger = Logger.getLogger(McqInputCommand.class.getName());
    private final String mcqInput;
    private final Answerable currentAnswerable;


    public McqInputCommand(String mcqInput, Answerable currentAnswerable) {
        this.mcqInput = mcqInput;
        this.currentAnswerable = currentAnswerable;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Answer selectedAnswer;

        switch (mcqInput.toLowerCase()) {
        case "a":
            selectedAnswer = currentAnswerable.getCombinedAnswerList().get(0);
            break;
        case "b":
            selectedAnswer = currentAnswerable.getCombinedAnswerList().get(1);
            break;
        case "c":
            selectedAnswer = currentAnswerable.getCombinedAnswerList().get(2);
            break;
        case "d":
            selectedAnswer = currentAnswerable.getCombinedAnswerList().get(3);
            break;
        case "n":
            return new CommandResultBuilder().withCorrect(false).build();
        default:
            selectedAnswer = null;
        }

        requireNonNull(selectedAnswer);
        boolean result = currentAnswerable.isCorrect(selectedAnswer);

        return new CommandResultBuilder().withCorrect(result).build();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof McqInputCommand // instanceof handles nulls
                && mcqInput.equals(((McqInputCommand) other).mcqInput));
    }
}
