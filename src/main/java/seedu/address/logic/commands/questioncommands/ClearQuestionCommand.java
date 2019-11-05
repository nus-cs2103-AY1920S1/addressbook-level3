package seedu.address.logic.commands.questioncommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Command to clear all questions.
 */
public class ClearQuestionCommand extends Command {
    public static final String COMMAND_WORD = "clearq";
    public static final String MESSAGE_SUCCESS = "Questions have been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.clearQuestions();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
