package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Displays reminders and follow-ups for the day
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays reminders and follow-ups for the day";

    public static final String SHOWING_MOTD_MESSAGE = "Opened message of the day.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_MOTD_MESSAGE, false, false, false, false, false, true);
    }
}
