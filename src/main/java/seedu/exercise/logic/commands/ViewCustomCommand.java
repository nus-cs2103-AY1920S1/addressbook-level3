package seedu.exercise.logic.commands;

import seedu.exercise.model.Model;

/**
 * Produces a window for user to view the table of custom properties they have defined.
 */
public class ViewCustomCommand extends Command {

    public static final String COMMAND_WORD = "viewcustom";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all defined custom properties.\n"
        + "Example: " + COMMAND_WORD;

    public static final String SHOWING_VIEW_CUSTOM_MESSAGE = "Opened custom properties window";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_VIEW_CUSTOM_MESSAGE, false, false, false, true);
    }

}

