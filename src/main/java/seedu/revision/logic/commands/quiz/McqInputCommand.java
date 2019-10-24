package seedu.revision.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import seedu.revision.logic.commands.Command;

import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.model.Model;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;

public class McqInputCommand extends Command {

    public static final String MESSAGE_USAGE = "Input can only be A, B, C, or D (case insensitive)";
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
        default:
            selectedAnswer = null;
        }

        requireNonNull(selectedAnswer);
        String result  = currentAnswerable.isCorrect(selectedAnswer) ? "correct" : "wrong";

        return new CommandResult(result , false, false);
    }








}
