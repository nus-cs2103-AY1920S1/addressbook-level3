package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class HomeCommand extends AppCommand {

    public static final String COMMAND_WORD = "home";

    public static final String MESSAGE_HOME_ACKNOWLEDGEMENT = "Going home as requested";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_HOME_ACKNOWLEDGEMENT, false, false);
    }

}
