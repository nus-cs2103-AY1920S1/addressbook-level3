package seedu.weme.logic.commands.memecommand;

import static java.util.Objects.requireNonNull;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.model.Model;

/**
 * Clears all the memes in Weme.
 */
public class MemeClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Weme has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.clearMemes();
        CommandResult result = new CommandResult(MESSAGE_SUCCESS);
        model.commitWeme(result.getFeedbackToUser());

        return result;
    }
}
