package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class QuitQuizModeCommand extends Command {
    public static final String COMMAND_WORD = "quit";
    public static final String MESSAGE_SUCCESS = "You have successfully exit from the quiz mode!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        LogicManager.isQuiz = false;
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
