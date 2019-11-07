package unrealunity.visit.logic.commands;

import unrealunity.visit.model.Model;

/**
 * Displays appointments in a pop-up window.
 */
public class ShowCommand extends Command {

    /**
     * Word to call the Follow-Up Command.
     */
    public static final String COMMAND_WORD = "show";

    /**
     * Help message on usage.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays reminders and follow-ups for the day";

    /**
     * Success message when executed.
     */
    public static final String SHOWING_MOTD_MESSAGE = "Opened message of the day.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_MOTD_MESSAGE, false, false, false, false, false, false, true);
    }
}
