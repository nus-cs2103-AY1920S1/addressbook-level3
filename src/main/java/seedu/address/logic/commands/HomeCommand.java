package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;

/**
 * Format full help instructions for every command for display.
 */
public class HomeCommand extends Command {

    public static final String COMMAND_WORD = "home";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows statistics under home page.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HOME_MESSAGE = "Showing homepage.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HOME_MESSAGE, false, false, CommandType.HM);
    }
}
