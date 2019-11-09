package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.flashcard.logic.CommandHistory;
import seedu.flashcard.model.Model;

/**
 * The command to end quiz mode.
 */
public class EndCommand extends Command {

    public static final String COMMAND_WORD = "end";

    public static final String MESSAGE_SUCCESS = "Ended quiz mode";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, true, "");
    }
}
