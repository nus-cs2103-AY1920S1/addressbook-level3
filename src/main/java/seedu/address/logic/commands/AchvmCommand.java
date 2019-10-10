package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Displays information on user's biography.
 */
public class AchvmCommand extends Command {

    public static final String COMMAND_WORD = "achvm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays information on user's achievements.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_ACHV_MESSAGE = "Here're your achievements so far. Keep up the good work!";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_ACHV_MESSAGE, false, false, true, false);
    }
}
