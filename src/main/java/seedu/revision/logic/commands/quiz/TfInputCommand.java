package seedu.revision.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.commands.main.CommandResultBuilder;
import seedu.revision.model.Model;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;

/** TfInputCommand class used to execute user inputs for True and False Answerables. **/
public class TfInputCommand extends Command {
    public static final String MESSAGE_USAGE = "Input can only be True(T) or False(F) (case insensitive)";
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
        case "true":
            //fallthrough
        case "t":
            selectedAnswer = new Answer("true");
            break;
        case "false":
            //fallthrough
        case "f":
            selectedAnswer = new Answer("false");
            break;
        case "n":
            return new CommandResultBuilder().withCorrect(false).build();
        default:
            selectedAnswer = null;
        }

        requireNonNull(selectedAnswer);
        boolean result = currentAnswerable.isAnswerCorrect(selectedAnswer);

        return new CommandResultBuilder().withCorrect(result).build();
    }


}
