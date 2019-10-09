package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Displays information on user's biography.
 */
public class BioCommand extends Command {

    public static final String COMMAND_WORD = "bio";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays information on user's biography.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_BIO_MESSAGE = "Displayed bio information on window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_BIO_MESSAGE, false, true, false);
    }
}
