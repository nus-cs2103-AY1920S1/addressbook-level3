package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class HomeCommand extends Command {

    public static final String COMMAND_WORD = "home";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns to dashboard view.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HOME_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(SHOWING_HOME_MESSAGE, false, false);
    }
}
