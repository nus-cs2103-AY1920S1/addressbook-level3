package seedu.revision.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import seedu.revision.logic.commands.QuizCommand;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.model.Model;
import seedu.revision.model.answerable.Answerable;

public class McqInputCommand extends QuizCommand {

    public static final String MESSAGE_USAGE = "Input can only be A, B, C, or D (case insensitive)";
    private final String mcqInput;

    public McqInputCommand(String mcqInput) {
        this.mcqInput = mcqInput;
    }

    @Override
    public CommandResult execute(Model model, Answerable currentAnswerable) {
        requireNonNull(model);

        currentAnswerable.isCorrect(mcqInput);

        // TODO: Khiang Leon can update storage.statistics here, pass in boolean and answerable
        return new CommandResult("" , false, false);
    }








}
