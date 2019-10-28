package seedu.revision.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.model.Model;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.answer.Answer;
import seedu.revision.model.answerable.answer.TrueFalseAnswer;

public class TfInputCommand extends Command {
    public static final String MESSAGE_USAGE = "Input can only be T or F (case insensitive)";
    private final String tfInput;
    private final Answerable currentAnswerable;


    public TfInputCommand(String tfInput, Answerable currentAnswerable) {
        this.tfInput = tfInput;
        this.currentAnswerable = currentAnswerable;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Answer selectedAnswer;

        switch (tfInput.toLowerCase()) {
        case "t":
            selectedAnswer = new TrueFalseAnswer("TRUE");
            break;
        case "f":
            selectedAnswer = new TrueFalseAnswer("FALSE");
            break;
        default:
            selectedAnswer = null;
        }

        requireNonNull(selectedAnswer);
        String result  = currentAnswerable.isCorrect(selectedAnswer) ? "correct" : "wrong";

        return new CommandResult(result , false, false);
    }


}
